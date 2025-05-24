import { useEffect, useState } from 'react';
import axios from 'axios';
import './KhungChuongTrinhPage.css';

interface KhungChuongTrinhDTO {
  id?: number;
  ctdtId: number;
  maNhom: string;
  tenNhom: string;
  soTinChiToiThieu: string;
}

interface ThongTinChungDTO {
  id: number;
  tenCtdt: string;
}

export default function KhungChuongTrinhPage() {
  const [list, setList] = useState<KhungChuongTrinhDTO[]>([]);
  const [ctdtList, setCtdtList] = useState<ThongTinChungDTO[]>([]);
  const [form, setForm] = useState<KhungChuongTrinhDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [showStatsModal, setShowStatsModal] = useState(false);
  const [groupStats, setGroupStats] = useState<Map<string, number>>(new Map());

  function emptyForm(): KhungChuongTrinhDTO {
    return { ctdtId: 0, maNhom: '', tenNhom: '', soTinChiToiThieu: '' };
  }

  const loadData = async () => {
    const res = await axios.get('/api/khung-chuong-trinh');
    setList(res.data);
    calculateStats(res.data);
  };

  const loadCtdt = async () => {
    const res = await axios.get('/api/thong-tin-chung');
    setCtdtList(res.data);
  };

  const calculateStats = (data: KhungChuongTrinhDTO[]) => {
  const stats = new Map<number, number>();
  data.forEach((item) => {
    stats.set(item.ctdtId, (stats.get(item.ctdtId) || 0) + +item.soTinChiToiThieu);
  });
  setGroupStats(stats);
};



  useEffect(() => {
    loadData();
    loadCtdt();
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/khung-chuong-trinh/${editingId}`, form);
    } else {
      await axios.post('/api/khung-chuong-trinh', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleEdit = (item: KhungChuongTrinhDTO) => {
    setForm(item);
    setEditingId(item.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Xóa khung chương trình này?')) {
      await axios.delete(`/api/khung-chuong-trinh/${id}`);
      loadData();
    }
  };

  return (
    <div className="container">
      <h2>Khung chương trình đào tạo</h2>

      <div className="action-bar">
        <button className="add-button" onClick={() => { setForm(emptyForm()); setShowModal(true); }}>
          + Thêm mới
        </button>
        <button className="stat-button" onClick={() => setShowStatsModal(true)}>
          Thống kê
        </button>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th><th>CTĐT</th><th>Mã nhóm</th><th>Tên nhóm</th><th>Số TC tối thiểu</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {list.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{ctdtList.find(c => c.id === item.ctdtId)?.tenCtdt || '...'}</td>
              <td>{item.maNhom}</td>
              <td>{item.tenNhom}</td>
              <td>{item.soTinChiToiThieu}</td>
              <td>
                <button className="edit-btn" onClick={() => handleEdit(item)}>Sửa</button>
                <button className="delete-btn" onClick={() => handleDelete(item.id!)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>


      {showStatsModal && (
  <div className="modal">
    <div className="modal-content">
      <button className="close-btn" onClick={() => setShowStatsModal(false)}>×</button>
      <h3>Thống kê số tín chỉ theo chương trình đào tạo</h3>
      <table className="table">
        <thead>
          <tr><th>Chương trình đào tạo</th><th>Tổng số tín chỉ</th></tr>
        </thead>
        <tbody>
          {Array.from(groupStats.entries()).map(([ctdtId, tinchi]) => {
            const ctdtName = ctdtList.find(c => c.id === ctdtId)?.tenCtdt || 'Chưa xác định';
            return (
              <tr key={ctdtId}>
                <td>{ctdtName}</td>
                <td>{tinchi}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  </div>
)}


      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            <h3>{editingId ? 'Cập nhật nhóm CTĐT' : 'Thêm nhóm CTĐT mới'}</h3>
            <form onSubmit={handleSubmit}>
              <select value={form.ctdtId} onChange={e => setForm({ ...form, ctdtId: +e.target.value })}>
                <option value={0}>-- Chọn CTĐT --</option>
                {ctdtList.map(ctdt => (
                  <option key={ctdt.id} value={ctdt.id}>{ctdt.tenCtdt}</option>
                ))}
              </select>
              <input placeholder="Mã nhóm" value={form.maNhom} onChange={e => setForm({ ...form, maNhom: e.target.value })} />
              <input placeholder="Tên nhóm" value={form.tenNhom} onChange={e => setForm({ ...form, tenNhom: e.target.value })} />
              <input type="number" placeholder="Số tín chỉ tối thiểu" value={form.soTinChiToiThieu}
                     onChange={e => setForm({ ...form, soTinChiToiThieu: e.target.value })} />
              <button type="submit" className="submit-btn">Lưu</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}