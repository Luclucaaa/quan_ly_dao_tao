import { useEffect, useState } from 'react';
import axios from 'axios';
import './KeHoachDayHocPage.css';

interface KeHoachDayHocDTO {
  id?: number;
  ctdtId: number;
  hocPhanId: number;
  hocKy: number;
  namHoc: number;
}

interface HocPhan {
  id: number;
  tenHp: string;
}

interface ThongTinChung {
  id: number;
  tenCtdt: string;
}

export default function KeHoachDayHocPage() {
  const [list, setList] = useState<KeHoachDayHocDTO[]>([]);
  const [hocPhanList, setHocPhanList] = useState<HocPhan[]>([]);
  const [ctdtList, setCtdtList] = useState<ThongTinChung[]>([]);
  const [form, setForm] = useState<KeHoachDayHocDTO>({ ctdtId: 0, hocPhanId: 0, hocKy: 1, namHoc: new Date().getFullYear() });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [search, setSearch] = useState('');
  const [showModal, setShowModal] = useState(false);

  const loadData = async () => {
    const [khdhRes, hpRes, ctdtRes] = await Promise.all([
      axios.get('/api/ke-hoach-day-hoc'),
      axios.get('/api/hoc-phan'),
      axios.get('/api/thong-tin-chung'),
    ]);
    setList(khdhRes.data);
    setHocPhanList(hpRes.data);
    setCtdtList(ctdtRes.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/ke-hoach-day-hoc/${editingId}`, form);
    } else {
      await axios.post('/api/ke-hoach-day-hoc', form);
    }
    setEditingId(null);
    setShowModal(false);
    setForm({ ctdtId: 0, hocPhanId: 0, hocKy: 1, namHoc: new Date().getFullYear() });
    loadData();
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc muốn xóa?')) {
      await axios.delete(`/api/ke-hoach-day-hoc/${id}`);
      loadData();
    }
  };

  const getHpName = (id: number) => hocPhanList.find(hp => hp.id === id)?.tenHp || id;
  const getCtdtName = (id: number) => ctdtList.find(c => c.id === id)?.tenCtdt || id;

  const filteredList = list.filter(item =>
    item.namHoc.toString().includes(search) || item.hocKy.toString().includes(search)
  );

  return (
    <div className="khdh-container">
      <h2>Kế hoạch dạy học</h2>

      <div className="khdh-toolbar">
        <button className="add-button" onClick={() => { setShowModal(true); setEditingId(null); }}>+ Thêm mới</button>
        <input
          className="search-input"
          placeholder="Tìm theo học kỳ, năm học..."
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
      </div>

      <table className="khdh-table">
        <thead>
          <tr>
            <th>CTĐT</th>
            <th>Học phần</th>
            <th>Học kỳ</th>
            <th>Năm học</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {filteredList.map(item => (
            <tr key={item.id}>
              <td>{getCtdtName(item.ctdtId)}</td>
              <td>{getHpName(item.hocPhanId)}</td>
              <td>{item.hocKy}</td>
              <td>{item.namHoc}</td>
              <td>
                <button className="edit-btn" onClick={() => { setForm(item); setEditingId(item.id!); setShowModal(true); }}>Sửa</button>
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
            <form onSubmit={handleSubmit}>
              <select value={form.ctdtId} onChange={e => setForm({ ...form, ctdtId: +e.target.value })}>
                <option value="">Chọn CTĐT</option>
                {ctdtList.map(c => <option key={c.id} value={c.id}>{c.tenCtdt}</option>)}
              </select>
              <select value={form.hocPhanId} onChange={e => setForm({ ...form, hocPhanId: +e.target.value })}>
                <option value="">Chọn học phần</option>
                {hocPhanList.map(hp => <option key={hp.id} value={hp.id}>{hp.tenHp}</option>)}
              </select>
              <input type="number" placeholder="Học kỳ" value={form.hocKy} onChange={e => setForm({ ...form, hocKy: +e.target.value })} />
              <input type="number" placeholder="Năm học" value={form.namHoc} onChange={e => setForm({ ...form, namHoc: +e.target.value })} />
              <button type="submit" className="submit-btn">Lưu</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}