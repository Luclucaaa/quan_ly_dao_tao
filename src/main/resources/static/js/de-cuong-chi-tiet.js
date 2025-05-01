let hocPhanList = [];

function loadHocPhan() {
    return fetch('/api/hoc-phan')
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách học phần');
            }
            return response.json();
        })
        .then(data => {
            hocPhanList = data;
        })
        .catch(error => {
            console.error(error);
            hocPhanList = [];
        });
}

function getTenHocPhan(hocPhanId) {
    const hocPhan = hocPhanList.find(hp => hp.id === parseInt(hocPhanId));
    return hocPhan ? hocPhan.tenHp : 'N/A';
}

function loadDeCuongChiTiet() {
    fetch('/api/de-cuong-chi-tiet')
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách đề cương chi tiết');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('deCuongChiTietTable');
            tableBody.innerHTML = '';
            data.forEach(dc => {
                tableBody.innerHTML += `
                    <tr>
                        <td>${getTenHocPhan(dc.hocPhanId)}</td>
                        <td>${dc.mucTieu ? dc.mucTieu.substring(0, 50) + (dc.mucTieu.length > 50 ? '...' : '') : 'N/A'}</td>
                        <td>${dc.trangThai}</td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="viewDeCuongChiTiet(${dc.id})"><i class="fas fa-eye"></i></button>
                            <button class="btn btn-sm btn-warning" onclick="openEditDeCuongChiTietModal(${dc.id})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-danger" onclick="deleteDeCuongChiTiet(${dc.id})"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function viewDeCuongChiTiet(id) {
    fetch(`/api/de-cuong-chi-tiet/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin đề cương chi tiết');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('viewHocPhan').textContent = getTenHocPhan(data.hocPhanId);
            document.getElementById('viewMucTieu').textContent = data.mucTieu || 'N/A';
            document.getElementById('viewNoiDung').textContent = data.noiDung || 'N/A';
            document.getElementById('viewPhuongPhapGiangDay').textContent = data.phuongPhapGiangDay || 'N/A';
            document.getElementById('viewPhuongPhapDanhGia').textContent = data.phuongPhapDanhGia || 'N/A';
            document.getElementById('viewTaiLieuThamKhao').textContent = data.taiLieuThamKhao || 'N/A';
            document.getElementById('viewTrangThai').textContent = data.trangThai || 'N/A';
            new bootstrap.Modal(document.getElementById('viewDeCuongChiTietModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function openEditDeCuongChiTietModal(id) {
    fetch(`/api/de-cuong-chi-tiet/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin đề cương chi tiết');
            }
            return response.json();
        })
        .then(data => {
            const form = document.getElementById('deCuongChiTietForm');
            form.querySelector('[name="id"]').value = data.id || '';
            form.querySelector('[name="hocPhanId"]').value = data.hocPhanId || '';
            form.querySelector('[name="mucTieu"]').value = data.mucTieu || '';
            form.querySelector('[name="noiDung"]').value = data.noiDung || '';
            form.querySelector('[name="phuongPhapGiangDay"]').value = data.phuongPhapGiangDay || '';
            form.querySelector('[name="phuongPhapDanhGia"]').value = data.phuongPhapDanhGia || '';
            form.querySelector('[name="taiLieuThamKhao"]').value = data.taiLieuThamKhao || '';
            form.querySelector('[name="trangThai"]').value = data.trangThai || '';
            document.getElementById('addDeCuongChiTietModalLabel').textContent = 'Sửa Đề cương Chi tiết';
            new bootstrap.Modal(document.getElementById('addDeCuongChiTietModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function deleteDeCuongChiTiet(id) {
    if (confirm('Bạn có chắc muốn xóa đề cương chi tiết này?')) {
        fetch(`/api/de-cuong-chi-tiet/${id}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể xóa đề cương chi tiết');
                }
                loadDeCuongChiTiet();
            })
            .catch(error => {
                alert(error.message);
                console.error(error);
            });
    }
}

function saveDeCuongChiTiet(event) {
    event.preventDefault();
    const form = document.getElementById('deCuongChiTietForm');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value === '' ? null : value;
    });

    const method = data.id ? 'PUT' : 'POST';
    const url = data.id ? `/api/de-cuong-chi-tiet/${data.id}` : '/api/de-cuong-chi-tiet';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lưu đề cương chi tiết');
            }
            return response.json();
        })
        .then(() => {
            alert('Lưu đề cương chi tiết thành công!');
            bootstrap.Modal.getInstance(document.getElementById('addDeCuongChiTietModal')).hide();
            loadDeCuongChiTiet();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function resetDeCuongChiTietForm() {
    const form = document.getElementById('deCuongChiTietForm');
    form.reset();
    form.querySelector('[name="id"]').value = '';
    document.getElementById('addDeCuongChiTietModalLabel').textContent = 'Thêm Đề cương Chi tiết';
}

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', () => {
    loadHocPhan().then(() => loadDeCuongChiTiet());
});

// Gắn sự kiện submit cho form
document.getElementById('deCuongChiTietForm').addEventListener('submit', saveDeCuongChiTiet);

// Gắn sự kiện mở modal thêm mới
document.querySelector('[data-bs-target="#addDeCuongChiTietModal"]').addEventListener('click', resetDeCuongChiTietForm);