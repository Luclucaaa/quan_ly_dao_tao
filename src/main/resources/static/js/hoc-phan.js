function getTongTinChi(nhomId) {
    return fetch(`/api/hoc-phan/tong-tin-chi?nhomId=${nhomId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tính tổng tín chỉ');
            }
            return response.json();
        })
        .then(data => data)
        .catch(error => {
            console.error(error);
            return 0;
        });
}

function viewHocPhan(nhomId) {
    fetch(`/api/hoc-phan?nhomId=${nhomId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách học phần');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('hocPhanTable');
            tableBody.innerHTML = '';
            data.forEach(hp => {
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
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
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
            Promise.all(data.map(kct => 
                getTongTinChi(kct.id).then(tongTinChi => ({ ...kct, tongTinChi }))
            )).then(khungChuongTrinhWithTongTinChi => {
                khungChuongTrinhWithTongTinChi.forEach(kct => {
                    tableBody.innerHTML += `
                        <tr>
                            <td>${kct.maNhom}</td>
                            <td>${kct.tenNhom}</td>
                            <td>${getTenCtdt(kct.ctdtId)}</td>
                            <td>${kct.soTinChiToiThieu}</td>
                            <td>${kct.tongTinChi}</td>
                            <td>
                                <button class="btn btn-sm btn-info" onclick="viewHocPhan(${kct.id})"><i class="fas fa-eye"></i> Học Phần</button>
                                <button class="btn btn-sm btn-warning" onclick="openEditKhungChuongTrinhModal(${kct.id})"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-sm btn-danger" onclick="deleteKhungChuongTrinh(${kct.id})"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `;
                });
            });
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}


function getTenKhung(nhomId) {
    const khung = khungChuongTrinhList.find(k => k.id === parseInt(nhomId));
    return khung ? khung.tenKhung : 'N/A';
}

function loadHocPhan(query = '') {
    const url = query ? `/api/hoc-phan?search=${encodeURIComponent(query)}` : '/api/hoc-phan';
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách học phần');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('hocPhanTable');
            tableBody.innerHTML = '';
            data.forEach(hp => {
                tableBody.innerHTML += `
                    <tr>
                        <td>${hp.maHp}</td>
                        <td>${hp.tenHp}</td>
                        <td>${hp.soTinChi}</td>
                        <td>${hp.loaiHp}</td>
                        <td>${getTenKhung(hp.nhomId)}</td>
                        <td>${hp.hocPhanTienQuyet || 'Không'}</td>
                        <td>
                            <button class="btn btn-sm btn-warning" onclick="openEditHocPhanModal(${hp.id})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-danger" onclick="deleteHocPhan(${hp.id})"><i class="fas fa-trash"></i></button>
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

function openEditHocPhanModal(id) {
    fetch(`/api/hoc-phan/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin học phần');
            }
            return response.json();
        })
        .then(data => {
            const form = document.getElementById('hocPhanForm');
            form.querySelector('[name="id"]').value = data.id || '';
            form.querySelector('[name="maHp"]').value = data.maHp || '';
            form.querySelector('[name="tenHp"]').value = data.tenHp || '';
            form.querySelector('[name="soTinChi"]').value = data.soTinChi || '';
            form.querySelector('[name="loaiHp"]').value = data.loaiHp || '';
            form.querySelector('[name="soTietLyThuyet"]').value = data.soTietLyThuyet || '';
            form.querySelector('[name="soTietThucHanh"]').value = data.soTietThucHanh || '';
            form.querySelector('[name="nhomId"]').value = data.nhomId || '';
            form.querySelector('[name="hocPhanTienQuyet"]').value = data.hocPhanTienQuyet || '';
            document.getElementById('addHocPhanModalLabel').textContent = 'Sửa Học Phần';
            new bootstrap.Modal(document.getElementById('addHocPhanModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function deleteHocPhan(id) {
    if (confirm('Bạn có chắc muốn xóa học phần này?')) {
        fetch(`/api/hoc-phan/${id}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể xóa học phần');
                }
                loadHocPhan();
            })
            .catch(error => {
                alert(error.message);
                console.error(error);
            });
    }
}

function saveHocPhan(event) {
    event.preventDefault();
    const form = document.getElementById('hocPhanForm');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value === '' ? null : value;
    });

    const method = data.id ? 'PUT' : 'POST';
    const url = data.id ? `/api/hoc-phan/${data.id}` : '/api/hoc-phan';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lưu học phần');
            }
            return response.json();
        })
        .then(() => {
            alert('Lưu học phần thành công!');
            bootstrap.Modal.getInstance(document.getElementById('addHocPhanModal')).hide();
            loadHocPhan();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function searchHocPhan(event) {
    event.preventDefault();
    const query = document.getElementById('searchHocPhan').value;
    loadHocPhan(query);
}

function resetHocPhanForm() {
    const form = document.getElementById('hocPhanForm');
    form.reset();
    form.querySelector('[name="id"]').value = '';
    document.getElementById('addHocPhanModalLabel').textContent = 'Thêm Học Phần';
}

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', () => {
    loadKhungChuongTrinh().then(() => loadHocPhan());
});

// Gắn sự kiện submit cho form
document.getElementById('hocPhanForm').addEventListener('submit', saveHocPhan);

// Gắn sự kiện mở modal thêm mới
document.querySelector('[data-bs-target="#addHocPhanModal"]').addEventListener('click', resetHocPhanForm);