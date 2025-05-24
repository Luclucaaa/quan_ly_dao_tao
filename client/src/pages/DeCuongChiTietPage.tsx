import { useEffect, useState } from 'react';
import axios from 'axios';
import './DeCuongChiTietPage.css';

interface DeCuongChiTietDTO {
  id?: number;
  hocPhanId: number;
  mucTieu: string;
  noiDung: string;
  phuongPhapGiangDay: string;
  phuongPhapDanhGia: string;
  taiLieuThamKhao: string;
  trangThai: string;
}

interface CotDiemDTO {
  id?: number;
  decuongId: number;
  tenCotDiem: string;
  tyLePhanTram: number;
  hinhThuc: string;
}

export default function DeCuongChiTietPage() {
  const [list, setList] = useState<DeCuongChiTietDTO[]>([]);
  const [form, setForm] = useState<DeCuongChiTietDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);

  const [cotDiemList, setCotDiemList] = useState<CotDiemDTO[]>([]);
  const [cotDiemForm, setCotDiemForm] = useState<CotDiemDTO>(emptyCotDiem());
  const [selectedDecuongId, setSelectedDecuongId] = useState<number | null>(null);
  const [showCotDiemModal, setShowCotDiemModal] = useState(false);

  const [hocPhanList, setHocPhanList] = useState<{id: number, tenHp: string}[]>([]);

  function emptyForm(): DeCuongChiTietDTO {
    return {
      hocPhanId: 0, mucTieu: '', noiDung: '', phuongPhapGiangDay: '',
      phuongPhapDanhGia: '', taiLieuThamKhao: '', trangThai: ''
    };
  }

  function emptyCotDiem(): CotDiemDTO {
    return { decuongId: 0, tenCotDiem: '', tyLePhanTram: 0, hinhThuc: '' };
  }

  const loadData = async () => {
    const res = await axios.get('/api/de-cuong-chi-tiet');
    setList(res.data);
  };

  const loadCotDiem = async (decuongId: number) => {
    const res = await axios.get('/api/cot-diem');
    setCotDiemList(res.data.filter((d: CotDiemDTO) => d.decuongId === decuongId));
  };

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    axios.get('/api/hoc-phan').then(res => {
      setHocPhanList(res.data.map((hp: any) => ({ id: hp.id, tenHp: hp.tenHp })));
    });
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/de-cuong-chi-tiet/${editingId}`, form);
    } else {
      await axios.post('/api/de-cuong-chi-tiet', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleCotDiemSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (cotDiemForm.id) {
      await axios.put(`/api/cot-diem/${cotDiemForm.id}`, cotDiemForm);
    } else {
      await axios.post(`/api/cot-diem`, cotDiemForm);
    }
    const decuongId = cotDiemForm.decuongId;
    setCotDiemForm({ ...emptyCotDiem(), decuongId });
    loadCotDiem(decuongId);
  };

  const handleCotDiemEdit = (item: CotDiemDTO) => {
    setCotDiemForm(item);
  };

  const handleCotDiemDelete = async (id: number) => {
    if (confirm('Xóa cột điểm?')) {
      await axios.delete(`/api/cot-diem/${id}`);
      if (selectedDecuongId) loadCotDiem(selectedDecuongId);
    }
  };
  const handleDelete = async (id: number) => {
    if (window.confirm('Bạn có chắc chắn muốn xóa đề cương này?')) {
      await axios.delete(`/api/de-cuong-chi-tiet/${id}`);
      loadData();
    }
  };

  const handleViewCotDiem = async (decuongId: number) => {
    setSelectedDecuongId(decuongId);
    setCotDiemForm({ ...emptyCotDiem(), decuongId });
    await loadCotDiem(decuongId);
    setShowCotDiemModal(true);
  };

  const tongTyLe = cotDiemList.reduce((sum, item) => sum + Number(item.tyLePhanTram), 0);

  return (
    <div className="decuong-container">
      <h2>Đề cương chi tiết</h2>

      <button className="add-button" onClick={() => { setForm(emptyForm()); setShowModal(true); }}>
        + Thêm mới
      </button>

      <table className="decuong-table">
        <thead>
          <tr>
            <th>Mục tiêu</th><th>Nội dung</th><th>Giảng dạy</th><th>Đánh giá</th><th>Tài liệu</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {list.map(item => (
            <tr key={item.id}>
              <td>{item.mucTieu}</td>
              <td>{item.noiDung}</td>
              <td>{item.phuongPhapGiangDay}</td>
              <td>{item.phuongPhapDanhGia}</td>
              <td>{item.taiLieuThamKhao}</td>
              <td>
                <button className="edit-btn" onClick={() => { setForm(item); setEditingId(item.id!); setShowModal(true); }}>Sửa</button>
                <button className="delete-btn" onClick={() => handleDelete(item.id!)}>Xóa</button>
                <button className="view-btn" onClick={() => handleViewCotDiem(item.id!)}>Xem cột điểm</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            <h3>{editingId ? 'Cập nhật đề cương' : 'Thêm đề cương mới'}</h3>
            <form onSubmit={handleSubmit}>
              <select value={form.hocPhanId} onChange={e => setForm({ ...form, hocPhanId: +e.target.value })}>
                <option value={0}>-- Chọn học phần --</option>
                {hocPhanList.map(hp => (
                  <option key={hp.id} value={hp.id}>{hp.tenHp}</option>
                ))}
              </select>
              <textarea placeholder="Mục tiêu" value={form.mucTieu} onChange={e => setForm({ ...form, mucTieu: e.target.value })} />
              <textarea placeholder="Nội dung" value={form.noiDung} onChange={e => setForm({ ...form, noiDung: e.target.value })} />
              <textarea placeholder="Phương pháp giảng dạy" value={form.phuongPhapGiangDay} onChange={e => setForm({ ...form, phuongPhapGiangDay: e.target.value })} />
              <textarea placeholder="Phương pháp đánh giá" value={form.phuongPhapDanhGia} onChange={e => setForm({ ...form, phuongPhapDanhGia: e.target.value })} />
              <textarea placeholder="Tài liệu tham khảo" value={form.taiLieuThamKhao} onChange={e => setForm({ ...form, taiLieuThamKhao: e.target.value })} />
              <input placeholder="Trạng thái" value={form.trangThai} onChange={e => setForm({ ...form, trangThai: e.target.value })} />
              <button type="submit" className="submit-btn">Lưu</button>
            </form>
          </div>
        </div>
      )}

      {showCotDiemModal && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowCotDiemModal(false)}>×</button>
            <h3>Quản lý các cột điểm</h3>
            <form onSubmit={handleCotDiemSubmit}>
              {tongTyLe < 100 ? (
                <>
                  <input placeholder="Tên cột điểm" value={cotDiemForm.tenCotDiem} onChange={e => setCotDiemForm({ ...cotDiemForm, tenCotDiem: e.target.value })} />
                  <input type="number" placeholder="Tỷ lệ %" value={cotDiemForm.tyLePhanTram} onChange={e => setCotDiemForm({ ...cotDiemForm, tyLePhanTram: +e.target.value })} />
                  <input placeholder="Hình thức" value={cotDiemForm.hinhThuc} onChange={e => setCotDiemForm({ ...cotDiemForm, hinhThuc: e.target.value })} />
                  <button type="submit" className="submit-btn" disabled={tongTyLe >= 100}>Lưu cột điểm</button>
                </>
              ) : (
                <div style={{color: 'red', marginBottom: 10}}>Tổng tỷ lệ đã đủ 100%. Không thể thêm cột điểm mới.</div>
              )}
            </form>
            <table className="cotdiem-table">
              <thead><tr><th>Tên</th><th>Tỷ lệ</th><th>Hình thức</th><th>Hành động</th></tr></thead>
              <tbody>
                {cotDiemList.map(item => (
                  <tr key={item.id}>
                    <td>{item.tenCotDiem}</td>
                    <td>{item.tyLePhanTram}</td>
                    <td>{item.hinhThuc}</td>
                    <td>
                      <button className="edit-btn" onClick={() => handleCotDiemEdit(item)}>Sửa</button>
                      <button className="delete-btn" onClick={() => handleCotDiemDelete(item.id!)}>Xóa</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}