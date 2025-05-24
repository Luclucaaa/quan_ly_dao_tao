import { useEffect, useState } from 'react';
import axios from 'axios';
import './ThongTinChungPage.css';

interface ThongTinChungDTO {
  id?: number;
  maCtdt: string;
  tenCtdt: string;
  nganh: string;
  maNganh: string;
  khoaQuanLy: string;
  heDaoTao: string;
  trinhDo: string;
  tongTinChi: string;
  thoiGianDaoTao: string;
  namBanHanh: string;
  trangThai: string;
}

const fieldLabels: Record<keyof ThongTinChungDTO, string> = {
  id: 'ID',
  maCtdt: 'Mã CTĐT',
  tenCtdt: 'Tên CTĐT',
  nganh: 'Ngành',
  maNganh: 'Mã ngành',
  khoaQuanLy: 'Khoa quản lý',
  heDaoTao: 'Hệ đào tạo',
  trinhDo: 'Trình độ',
  tongTinChi: 'Tổng tín chỉ',
  thoiGianDaoTao: 'Thời gian đào tạo',
  namBanHanh: 'Năm ban hành',
  trangThai: 'Trạng thái',
};

export default function ThongTinChungPage() {
  const [list, setList] = useState<ThongTinChungDTO[]>([]);
  const [form, setForm] = useState<ThongTinChungDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [detail, setDetail] = useState<ThongTinChungDTO | null>(null);

  function emptyForm(): ThongTinChungDTO {
    return {
      maCtdt: '', tenCtdt: '', nganh: '', maNganh: '', khoaQuanLy: '',
      heDaoTao: '', trinhDo: '', tongTinChi: '', thoiGianDaoTao: '',
      namBanHanh: '', trangThai: ''
    };
  }

  const loadData = async () => {
    const res = await axios.get('/api/thong-tin-chung');
    setList(res.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/thong-tin-chung/${editingId}`, form);
    } else {
      await axios.post('/api/thong-tin-chung', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleEdit = (item: ThongTinChungDTO) => {
    setForm(item);
    setEditingId(item.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Xóa chương trình này?')) {
      await axios.delete(`/api/thong-tin-chung/${id}`);
      loadData();
    }
  };

  return (
    <div className="container" style={{ width: '100%', padding: '20px', boxSizing: 'border-box' }}>
      <h2>Thông tin chung CTĐT</h2>

      <button className="add-button" onClick={() => { setShowModal(true); setEditingId(null); setForm(emptyForm()); }}>
        + Thêm mới
      </button>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th><th>Mã</th><th>Tên</th><th>Ngành</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {list.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.maCtdt}</td>
              <td>{item.tenCtdt}</td>
              <td>{item.nganh}</td>
              <td>
                <button className="detail-btn" onClick={() => setDetail(item)}>Chi tiết</button>
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
            <h3>{editingId ? 'Cập nhật CTĐT' : 'Thêm CTĐT mới'}</h3>
            <form onSubmit={handleSubmit}>
              {Object.entries(form).map(([key, value]) => key !== 'id' && (
                <input
                  key={key}
                  placeholder={fieldLabels[key as keyof ThongTinChungDTO] || key}
                  value={value}
                  onChange={e =>
                    setForm({
                      ...form,
                      [key]: key === 'tongTinChi' || key === 'namBanHanh' ? +e.target.value : e.target.value
                    })
                  }
                />
              ))}
              <div style={{ display: 'flex', gap: '10px', marginTop: '10px' }}>
                <button type="submit" className="submit-btn">Lưu</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {detail && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setDetail(null)}>×</button>
            <h3>Chi tiết chương trình đào tạo</h3>
            <ul>
              {Object.entries(detail).map(([key, val]) => (
                <li key={key}>
                  <strong>{fieldLabels[key as keyof ThongTinChungDTO] || key}:</strong> {val}
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
}
