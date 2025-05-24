import { useEffect, useState } from 'react';
import axios from 'axios';
import './GiangVienPage.css';
import Papa from 'papaparse';

interface GiangVienDTO {
  id?: number;
  userId?: number;
  maGv: string;
  hoTen: string;
  boMon: string;
  khoa: string;
  trinhDo: string;
  chuyenMon: string;
  trangThai: string;
}

export default function GiangVienPage() {
  const [list, setList] = useState<GiangVienDTO[]>([]);
  const [form, setForm] = useState<GiangVienDTO>(emptyForm());
  const [editingId, setEditingId] = useState<number | null>(null);
  const [search, setSearch] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showStats, setShowStats] = useState(false);

  const fieldPlaceholders: Record<string, string> = {
    maGv: 'Mã GV',
    hoTen: 'Họ tên',
    boMon: 'Bộ môn',
    khoa: 'Khoa',
    trinhDo: 'Trình độ',
    chuyenMon: 'Chuyên môn',
    trangThai: 'Trạng thái',
  };

  function emptyForm(): GiangVienDTO {
    return {
      maGv: '', hoTen: '', boMon: '', khoa: '', trinhDo: '',
      chuyenMon: '', trangThai: ''
    };
  }

  const loadData = async () => {
    const res = await axios.get('/api/giang-vien');
    setList(res.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    if (editingId) {
      await axios.put(`/api/giang-vien/${editingId}`, form);
    } else {
      await axios.post('/api/giang-vien', form);
    }
    setForm(emptyForm());
    setEditingId(null);
    setShowModal(false);
    loadData();
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa?')) {
      await axios.delete(`/api/giang-vien/${id}`);
      loadData();
    }
  };

  const escapeCSV = (value: string) => {
    if (value == null) return '';
    // Nếu có dấu phẩy hoặc dấu ngoặc kép, bọc bằng dấu ngoặc kép và escape dấu ngoặc kép
    if (value.includes(',') || value.includes('"')) {
      return `"${value.replace(/"/g, '""')}"`;
    }
    return value;
  };
  
  const exportCSV = () => {
    const csv = ['Mã GV,Họ tên,Bộ môn,Khoa,Trình độ,Chuyên môn,Trạng thái'];
    list.forEach(gv => {
      csv.push([
        escapeCSV(gv.maGv),
        escapeCSV(gv.hoTen),
        escapeCSV(gv.boMon),
        escapeCSV(gv.khoa),
        escapeCSV(gv.trinhDo),
        escapeCSV(gv.chuyenMon),
        escapeCSV(gv.trangThai)
      ].join(','));
    });
    const blob = new Blob(['\uFEFF' + csv.join('\n')], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'giang_vien.csv';
    a.click();
  };

  const importCSV = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = async () => {
      Papa.parse(reader.result as string, {
        header: true,
        skipEmptyLines: true,
        complete: async (results) => {
          for (const row of results.data as any[]) {
            // row sẽ có các trường đúng tên cột
            const { 'Mã GV': maGv, 'Họ tên': hoTen, 'Bộ môn': boMon, 'Khoa': khoa, 'Trình độ': trinhDo, 'Chuyên môn': chuyenMon, 'Trạng thái': trangThai } = row;
            await axios.post('/api/giang-vien', { maGv, hoTen, boMon, khoa, trinhDo, chuyenMon, trangThai });
          }
          loadData();
        }
      });
    };
    reader.readAsText(file);
  };

  const filteredList = list.filter(gv =>
    gv.maGv.toLowerCase().includes(search.toLowerCase()) || gv.hoTen.toLowerCase().includes(search.toLowerCase())
  );

  const thongKeTheoKhoa = list.reduce((acc: Record<string, number>, curr) => {
    acc[curr.khoa] = (acc[curr.khoa] || 0) + 1;
    return acc;
  }, {});

  return (
    <div className="gv-container">
      <h2>Danh mục giảng viên</h2>
      <div className="gv-toolbar">
        <button className="add-button" onClick={() => { setForm(emptyForm()); setEditingId(null); setShowModal(true); }}>+ Thêm</button>
        <button className="black-button" onClick={() => setShowStats(true)}>📊 Thống kê</button>
        <button className="black-button" onClick={exportCSV}>⬆ Xuất file</button>
        <label className="import-label black-button">⬇ Nhập file
          <input type="file" accept=".csv" onChange={importCSV} />
        </label>
        <input className="search" placeholder="Tìm mã/tên giảng viên..." value={search} onChange={e => setSearch(e.target.value)} />
      </div>

      <table className="gv-table">
        <thead>
          <tr>
            <th>Mã GV</th><th>Họ tên</th><th>Khoa</th><th>Bộ môn</th><th>Trình độ</th><th>Chuyên môn</th><th>Trạng thái</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {filteredList.map(gv => (
            <tr key={gv.id}>
              <td>{gv.maGv}</td>
              <td>{gv.hoTen}</td>
              <td>{gv.khoa}</td>
              <td>{gv.boMon}</td>
              <td>{gv.trinhDo}</td>
              <td>{gv.chuyenMon}</td>
              <td>{gv.trangThai}</td>
              <td>
                <button className="edit-btn" onClick={() => { setForm(gv); setEditingId(gv.id!); setShowModal(true); }}>Sửa</button>
                <button className="delete-btn" onClick={() => handleDelete(gv.id!)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            <h3>{editingId ? 'Cập nhật giảng viên' : 'Thêm giảng viên mới'}</h3>
            <form onSubmit={handleSubmit}>
              {Object.entries(form).map(([k, v]) => (
                k !== 'id' && <input key={k} value={v} placeholder={fieldPlaceholders[k] || k} onChange={e => setForm({ ...form, [k]: e.target.value })} />
              ))}
              <button className="submit-btn" type="submit">Lưu</button>
            </form>
          </div>
        </div>
      )}

      {showStats && (
        <div className="modal">
          <div className="modal-content">
            <button className="close-btn" onClick={() => setShowStats(false)}>×</button>
            <h3>Thống kê số lượng theo khoa</h3>
            <ul>
              {Object.entries(thongKeTheoKhoa).map(([khoa, count]) => (
                <li key={khoa}><strong>{khoa}</strong>: {count} giảng viên</li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
}
