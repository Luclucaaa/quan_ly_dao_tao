let thongTinChungList = [];
let hocPhanList = [];

function loadThongTinChung() {
    return fetch('/api/thong-tin-chung')
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách chương trình đào tạo');
            }
            return response.json();
        })
        .then(data => {
            thongTinChungList = data;
        })
        .catch(error => {
            console.error(error);
            thongTinChungList = [];
        });
}

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

function getTenCtdt(ctdtId) {
    const ctdt = thongTinChungList.find(c => c.id === parseInt(ctdtId));
    return ctdt ? ctdt.tenCtdt : 'N/A';
}

function getTongTinChi(nhomId) {
    return hocPhanList
        .filter(hp => hp.nhomId === parseInt(nhomId))
        .reduce((sum, hp) => sum + (hp.soTinChi || 0), 0);
}

function loadKhungChuongTrinh() {
    fetch('/api/khung-chuong-trinh')
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách khung chương trình');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('khungChuongTrinhTable');
            tableBody.innerHTML = '';
            data.forEach(kct => {
                tableBody.innerHTML += `
                    <tr>
                        <td>${kct.maNhom}</td>
                        <td>${kct.tenNhom}</td>
                        <td>${getTenCtdt(kct.ctdtId)}</td>
                        <td>${kct.soTinChiToiThieu}</td>
                        <td>${getTongTinChi(kct.id)}</td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="viewHocPhan(${kct.id})"><i class="fas fa-eye"></i> Học Phần</button>
                            <button class="btn btn-sm btn-warning" onclick="openEditKhungChuongTrinhModal(${kct.id})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-danger" onclick="deleteKhungChuongTrinh(${kct.id})"><i class="fas fa-trash"></i></button>
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

function openEditKhungChuongTrinhModal(id) {
    fetch(`/api/khung-chuong-trinh/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin khung chương trình');
            }
            return response.json();
        })
        .then(data => {
            const form = document.getElementById('khungChuongTrinhForm');
            form.querySelector('[name="id"]').value = data.id || '';
            form.querySelector('[name="maNhom"]').value = data.maNhom || '';
            form.querySelector('[name="tenNhom"]').value = data.tenNhom || '';
            form.querySelector('[name="ctdtId"]').value = data.ctdtId || '';
            form.querySelector('[name="soTinChiToiThieu"]').value = data.soTinChiToiThieu || '';
            document.getElementById('addKhungChuongTrinhModalLabel').textContent = 'Sửa Khung Chương Trình';
            new bootstrap.Modal(document.getElementById('addKhungChuongTrinhModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function deleteKhungChuongTrinh(id) {
    if (confirm('Bạn có chắc muốn xóa khung chương trình này?')) {
        fetch(`/api/khung-chuong-trinh/${id}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể xóa khung chương trình');
                }
                loadKhungChuongTrinh();
            })
            .catch(error => {
                alert(error.message);
                console.error(error);
            });
    }
}

function saveKhungChuongTrinh(event) {
    event.preventDefault();
    const form = document.getElementById('khungChuongTrinhForm');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value === '' ? null : value;
    });

    const method = data.id ? 'PUT' : 'POST';
    const url = data.id ? `/api/khung-chuong-trinh/${data.id}` : '/api/khung-chuong-trinh';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lưu khung chương trình');
            }
            return response.json();
        })
        .then(() => {
            alert('Lưu khung chương trình thành công!');
            bootstrap.Modal.getInstance(document.getElementById('addKhungChuongTrinhModal')).hide();
            loadKhungChuongTrinh();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function viewHocPhan(nhomId) {
    const filteredHocPhan = hocPhanList.filter(hp => hp.nhomId === parseInt(nhomId));
    const tableBody = document.getElementById('hocPhanTable');
    tableBody.innerHTML = '';
    filteredHocPhan.forEach(hp => {
        tableBody.innerHTML += `
            <tr>
                <td>${hp.maHp}</td>
                <td>${hp.tenHp}</td>
                <td>${hp.soTinChi}</td>
                <td>${hp.loaiHp}</td>
            </tr>
        `;
    });
    document.getElementById('viewHocPhanModalLabel').textContent = `Danh sách Học Phần (Nhóm ID: ${nhomId})`;
    new bootstrap.Modal(document.getElementById('viewHocPhanModal')).show();
}

function resetKhungChuongTrinhForm() {
    const form = document.getElementById('khungChuongTrinhForm');
    form.reset();
    form.querySelector('[name="id"]').value = '';
    document.getElementById('addKhungChuongTrinhModalLabel').textContent = 'Thêm Khung Chương Trình';
}

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', () => {
    Promise.all([loadThongTinChung(), loadHocPhan()])
        .then(() => loadKhungChuongTrinh())
        .catch(error => {
            alert('Lỗi khi tải dữ liệu: ' + error.message);
            console.error(error);
        });
});

// Gắn sự kiện submit cho form
document.getElementById('khungChuongTrinhForm').addEventListener('submit', saveKhungChuongTrinh);

// Gắn sự kiện mở modal thêm mới
document.querySelector('[data-bs-target="#addKhungChuongTrinhModal"]').addEventListener('click', resetKhungChuongTrinhForm);