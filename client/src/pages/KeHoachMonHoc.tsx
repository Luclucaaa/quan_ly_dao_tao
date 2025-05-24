import { useEffect, useState } from 'react';
import axios from 'axios';
import './KeHoachMonHoc.css';

interface KeHoachMonHocDTO {
  id?: number;
  maNhom: string;
  hocPhanId: number;
  namHoc: string;
  hocKy: number;
  soLuongSv: number;
  thoiGianBatDau: string;
  thoiGianKetThuc: string;
  trangThai: string;
}

interface PhanCongGiangDayDTO {
  id?: number;
  nhomId: number;
  giangVienId: number;
  vaiTro: string;
  soTiet: number;
}

interface GiangVien {
  id: number;
  hoTen: string;
}

export default function KeHoachMonHocPage() {
  const [list, setList] = useState<KeHoachMonHocDTO[]>([]);
  const [form, setForm] = useState<KeHoachMonHocDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [search, setSearch] = useState('');
  const [phanCongPopup, setPhanCongPopup] = useState<number | null>(null);
  const [phanCongList, setPhanCongList] = useState<PhanCongGiangDayDTO[]>([]);
  const [giangVienList, setGiangVienList] = useState<GiangVien[]>([]);
  const [phanCongForm, setPhanCongForm] = useState<PhanCongGiangDayDTO>({ nhomId: 0, giangVienId: 0, vaiTro: '', soTiet: 0 });

  function emptyForm(): KeHoachMonHocDTO {
    return {
      maNhom: '',
      hocPhanId: 1,
      namHoc: '',
      hocKy: 1,
      soLuongSv: 0,
      thoiGianBatDau: '',
      thoiGianKetThuc: '',
      trangThai: 'Chưa mở',
    };
  }

  const loadData = async () => {
    const res = await axios.get('/api/ke-hoach-mon-hoc');
    setList(res.data);
  };

  const loadGiangVien = async () => {
    const res = await axios.get('/api/giang-vien');
    setGiangVienList(res.data);
  };

  const loadPhanCong = async (nhomId: number) => {
    const res = await axios.get('/api/phan-cong-giang-day');
    setPhanCongList(res.data.filter((p: any) => p.nhomId === nhomId));
    setPhanCongPopup(nhomId);
  };

  useEffect(() => {
    loadData();
    loadGiangVien();
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/ke-hoach-mon-hoc/${editingId}`, form);
    } else {
      await axios.post('/api/ke-hoach-mon-hoc', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleDelete = async (id: number) => {
    if (confirm('Xóa kế hoạch này?')) {
      await axios.delete(`/api/ke-hoach-mon-hoc/${id}`);
      loadData();
    }
  };

  const handlePhanCongSubmit = async () => {
    if (phanCongForm.id) {
      await axios.put(`/api/phan-cong-giang-day/${phanCongForm.id}`, phanCongForm);
    } else {
      await axios.post('/api/phan-cong-giang-day', phanCongForm);
    }
    loadPhanCong(phanCongForm.nhomId);
    setPhanCongForm({ nhomId: 0, giangVienId: 0, vaiTro: '', soTiet: 0 });
  };

  const handlePhanCongDelete = async (id: number) => {
    if (confirm('Xóa phân công này?')) {
      await axios.delete(`/api/phan-cong-giang-day/${id}`);
      loadPhanCong(phanCongPopup!);
    }
  };

  return (
    <div className="khmh-container">
      <h2>Kế hoạch mở nhóm & Phân công giảng dạy</h2>
      <div className="toolbar">
        <button class="add-button" onClick={() => { setForm(emptyForm()); setEditingId(null); setShowModal(true); }}>+ Thêm mới</button>
        <input class="search-input" placeholder="Tìm mã nhóm..." value={search} onChange={e => setSearch(e.target.value)} />
      </div>

      <table className="khmh-table">
        <thead>
          <tr>
            <th>Mã nhóm</th><th>Năm học</th><th>Học kỳ</th><th>SL SV</th><th>Thời gian</th><th>Trạng thái</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {list.filter(x => x.maNhom.includes(search)).map(item => (
            <tr key={item.id}>
              <td>{item.maNhom}</td>
              <td>{item.namHoc}</td>
              <td>{item.hocKy}</td>
              <td>{item.soLuongSv}</td>
              <td>{item.thoiGianBatDau} → {item.thoiGianKetThuc}</td>
              <td>{item.trangThai}</td>
              <td>
                <button onClick={() => { setForm(item); setEditingId(item.id!); setShowModal(true); }}>Sửa</button>
                <button onClick={() => handleDelete(item.id!)}>Xóa</button>
                <button onClick={() => loadPhanCong(item.id!)}>Phân công</button>
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
            <h3>{editingId ? 'Cập nhật kế hoạch' : 'Thêm kế hoạch mới'}</h3>
              <input placeholder="Mã nhóm" value={form.maNhom} onChange={e => setForm({ ...form, maNhom: e.target.value })} />
              <input placeholder="Năm học" value={form.namHoc} onChange={e => setForm({ ...form, namHoc: e.target.value })} />
              <input type="number" placeholder="Học kỳ" value={form.hocKy} onChange={e => setForm({ ...form, hocKy: +e.target.value })} />
              <input type="number" placeholder="Số lượng sinh viên" value={form.soLuongSv} onChange={e => setForm({ ...form, soLuongSv: +e.target.value })} />
              <input type="date" placeholder="Thời gian bắt đầu" value={form.thoiGianBatDau} onChange={e => setForm({ ...form, thoiGianBatDau: e.target.value })} />
              <input type="date" placeholder="Thời gian kết thúc" value={form.thoiGianKetThuc} onChange={e => setForm({ ...form, thoiGianKetThuc: e.target.value })} />
              <select value={form.trangThai} onChange={e => setForm({ ...form, trangThai: e.target.value })}>
                <option>Đã kết thúc</option>
                <option>Đang diễn ra</option>
                <option>Chưa mở</option>
              </select>
              <button class="submit-btn" type="submit">Lưu</button>
            </form>
          </div>
        </div>
      )}

      {phanCongPopup && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setPhanCongPopup(null)}>×</button>
            <h3>Phân công giảng viên</h3>
            <table className="gv-table">
              <thead>
                <tr><th>Giảng viên</th><th>Vai trò</th><th>Số tiết</th><th></th></tr>
              </thead>
              <tbody>
                {phanCongList.map(p => {
                  const gv = giangVienList.find(g => g.id === p.giangVienId);
                  return (
                    <tr key={p.id}>
                      <td>{gv?.hoTen}</td><td>{p.vaiTro}</td><td>{p.soTiet}</td>
                      <td>
                        <button onClick={() => setPhanCongForm(p)}>Sửa</button>
                        <button onClick={() => handlePhanCongDelete(p.id!)}>Xóa</button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
            <div className="form-inline2">
              <select class="pcselect" value={phanCongForm.giangVienId} onChange={e => setPhanCongForm({ ...phanCongForm, nhomId: phanCongPopup, giangVienId: +e.target.value })}>
                <option value="">Chọn giảng viên</option>
                {giangVienList.map(g => <option key={g.id} value={g.id}>{g.hoTen}</option>)}
              </select>
              <input placeholder="Vai trò" value={phanCongForm.vaiTro} onChange={e => setPhanCongForm({ ...phanCongForm, vaiTro: e.target.value })} />
              <input type="number" placeholder="Số tiết" value={phanCongForm.soTiet} onChange={e => setPhanCongForm({ ...phanCongForm, soTiet: +e.target.value })} />
              <button onClick={handlePhanCongSubmit}>Lưu</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
