function loadThongTinChung() {
    fetch('/api/thong-tin-chung')
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải danh sách chương trình đào tạo');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('thongTinChungTable');
            tableBody.innerHTML = '';
            data.forEach(ctdt => {
                tableBody.innerHTML += `
                    <tr>
                        <td>${ctdt.maCtdt}</td>
                        <td>${ctdt.tenCtdt}</td>
                        <td>${ctdt.nganh}</td>
                        <td>${ctdt.khoaQuanLy}</td>
                        <td>${ctdt.trangThai}</td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="viewThongTinChung(${ctdt.id})"><i class="fas fa-eye"></i></button>
                            <button class="btn btn-sm btn-warning" onclick="openEditThongTinChungModal(${ctdt.id})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-danger" onclick="deleteThongTinChung(${ctdt.id})"><i class="fas fa-trash"></i></button>
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

function viewThongTinChung(id) {
    fetch(`/api/thong-tin-chung/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin chương trình đào tạo');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('viewMaCtdt').textContent = data.maCtdt || 'N/A';
            document.getElementById('viewTenCtdt').textContent = data.tenCtdt || 'N/A';
            document.getElementById('viewNganh').textContent = data.nganh || 'N/A';
            document.getElementById('viewMaNganh').textContent = data.maNganh || 'N/A';
            document.getElementById('viewKhoaQuanLy').textContent = data.khoaQuanLy || 'N/A';
            document.getElementById('viewHeDaoTao').textContent = data.heDaoTao || 'N/A';
            document.getElementById('viewTrinhDo').textContent = data.trinhDo || 'N/A';
            document.getElementById('viewTongTinChi').textContent = data.tongTinChi || 'N/A';
            document.getElementById('viewThoiGianDaoTao').textContent = data.thoiGianDaoTao || 'N/A';
            document.getElementById('viewNamBanHanh').textContent = data.namBanHanh || 'N/A';
            document.getElementById('viewTrangThai').textContent = data.trangThai || 'N/A';
            new bootstrap.Modal(document.getElementById('viewThongTinChungModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function openEditThongTinChungModal(id) {
    fetch(`/api/thong-tin-chung/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể tải thông tin chương trình đào tạo');
            }
            return response.json();
        })
        .then(data => {
            const form = document.getElementById('thongTinChungForm');
            form.querySelector('[name="id"]').value = data.id || '';
            form.querySelector('[name="maCtdt"]').value = data.maCtdt || '';
            form.querySelector('[name="tenCtdt"]').value = data.tenCtdt || '';
            form.querySelector('[name="nganh"]').value = data.nganh || '';
            form.querySelector('[name="maNganh"]').value = data.maNganh || '';
            form.querySelector('[name="khoaQuanLy"]').value = data.khoaQuanLy || '';
            form.querySelector('[name="heDaoTao"]').value = data.heDaoTao || '';
            form.querySelector('[name="trinhDo"]').value = data.trinhDo || '';
            form.querySelector('[name="tongTinChi"]').value = data.tongTinChi || '';
            form.querySelector('[name="thoiGianDaoTao"]').value = data.thoiGianDaoTao || '';
            form.querySelector('[name="namBanHanh"]').value = data.namBanHanh || '';
            form.querySelector('[name="trangThai"]').value = data.trangThai || '';
            document.getElementById('addThongTinChungModalLabel').textContent = 'Sửa Chương trình Đào tạo';
            new bootstrap.Modal(document.getElementById('addThongTinChungModal')).show();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function deleteThongTinChung(id) {
    if (confirm('Bạn có chắc muốn xóa chương trình đào tạo này?')) {
        fetch(`/api/thong-tin-chung/${id}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể xóa chương trình đào tạo');
                }
                loadThongTinChung();
            })
            .catch(error => {
                alert(error.message);
                console.error(error);
            });
    }
}

function saveThongTinChung(event) {
    event.preventDefault();
    const form = document.getElementById('thongTinChungForm');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value === '' ? null : value;
    });

    const method = data.id ? 'PUT' : 'POST';
    const url = data.id ? `/api/thong-tin-chung/${data.id}` : '/api/thong-tin-chung';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lưu chương trình đào tạo');
            }
            return response.json();
        })
        .then(() => {
            alert('Lưu chương trình đào tạo thành công!');
            bootstrap.Modal.getInstance(document.getElementById('addThongTinChungModal')).hide();
            loadThongTinChung();
        })
        .catch(error => {
            alert(error.message);
            console.error(error);
        });
}

function resetThongTinChungForm() {
    const form = document.getElementById('thongTinChungForm');
    form.reset();
    form.querySelector('[name="id"]').value = '';
    document.getElementById('addThongTinChungModalLabel').textContent = 'Thêm Chương trình Đào tạo';
}

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', () => loadThongTinChung());

// Gắn sự kiện submit cho form
document.getElementById('thongTinChungForm').addEventListener('submit', saveThongTinChung);

// Gắn sự kiện mở modal thêm mới
document.querySelector('[data-bs-target="#addThongTinChungModal"]').addEventListener('click', resetThongTinChungForm);