import { useEffect, useState } from 'react';
import axios from 'axios';
import './HocPhanPage.css';

interface HocPhanDTO {
  id?: number;
  maHp: string;
  tenHp: string;
  soTinChi: number;
  soTietLyThuyet: number;
  soTietThucHanh: number;
  nhomId: number;
  loaiHp: string;
  hocPhanTienQuyet: string;
}

export default function HocPhanPage() {
  const [list, setList] = useState<HocPhanDTO[]>([]);
  const [form, setForm] = useState<HocPhanDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  function emptyForm(): HocPhanDTO {
    return {
      maHp: '', tenHp: '', soTinChi: 0,
      soTietLyThuyet: 0, soTietThucHanh: 0,
      nhomId: 0, loaiHp: '', hocPhanTienQuyet: ''
    };
  }

  const loadData = async () => {
    const res = await axios.get('/api/hoc-phan', {
      params: searchTerm ? { search: searchTerm } : {}
    });
    setList(res.data);
  };

  useEffect(() => {
    loadData();
  }, [searchTerm]);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/hoc-phan/${editingId}`, form);
    } else {
      await axios.post('/api/hoc-phan', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleEdit = (item: HocPhanDTO) => {
    setForm(item);
    setEditingId(item.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Xóa học phần này?')) {
      await axios.delete(`/api/hoc-phan/${id}`);
      loadData();
    }
  };

  return (
    <div className="hocphan-container">
      <h2>Danh mục học phần</h2>

      <div className="hocphan-bar">
        <input
          className="search-input"
          placeholder="Tìm theo mã hoặc tên học phần"
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <button className="add-button" onClick={() => { setForm(emptyForm()); setShowModal(true); }}>
          + Thêm mới
        </button>
      </div>

      <table className="hocphan-table">
        <thead>
          <tr>
            <th>Mã</th><th>Tên</th><th>Số TC</th><th>Lý thuyết</th><th>Thực hành</th><th>Loại</th><th>Tiên quyết</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {list.map(item => (
            <tr key={item.id}>
              <td>{item.maHp}</td>
              <td>{item.tenHp}</td>
              <td>{item.soTinChi}</td>
              <td>{item.soTietLyThuyet}</td>
              <td>{item.soTietThucHanh}</td>
              <td>{item.loaiHp}</td>
              <td>{item.hocPhanTienQuyet}</td>
              <td>
                <button className="edit-btn" onClick={() => handleEdit(item)}>Sửa</button>
                <button className="delete-btn" onClick={() => handleDelete(item.id!)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            <h3>{editingId ? 'Cập nhật học phần' : 'Thêm học phần mới'}</h3>
            <form onSubmit={handleSubmit}>
              <input placeholder="Mã học phần" value={form.maHp} onChange={e => setForm({ ...form, maHp: e.target.value })} />
              <input placeholder="Tên học phần" value={form.tenHp} onChange={e => setForm({ ...form, tenHp: e.target.value })} />
              <input type="number" placeholder="Số tín chỉ" value={form.soTinChi} onChange={e => setForm({ ...form, soTinChi: +e.target.value })} />
              <input type="number" placeholder="Số tiết lý thuyết" value={form.soTietLyThuyet} onChange={e => setForm({ ...form, soTietLyThuyet: +e.target.value })} />
              <input type="number" placeholder="Số tiết thực hành" value={form.soTietThucHanh} onChange={e => setForm({ ...form, soTietThucHanh: +e.target.value })} />
              <input type="number" placeholder="Nhóm ID" value={form.nhomId} onChange={e => setForm({ ...form, nhomId: +e.target.value })} />
              <input placeholder="Loại học phần" value={form.loaiHp} onChange={e => setForm({ ...form, loaiHp: e.target.value })} />
              <input placeholder="Học phần tiên quyết" value={form.hocPhanTienQuyet} onChange={e => setForm({ ...form, hocPhanTienQuyet: e.target.value })} />
              <button type="submit" className="submit-btn">Lưu</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}