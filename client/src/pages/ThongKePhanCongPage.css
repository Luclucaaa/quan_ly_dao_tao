import { useEffect, useState } from 'react';
import axios from 'axios';
import './ThongKePhanCongPage.css';

interface ThongKeItem {
  maGiangVien: string;
  hoTen: string;
  hocPhan: string;
  maHocPhan: string;
  soTinChi: number;
  soTiet: number;
  soLop: number;
  tietHocKy1: number;
  tietHocKy2: number;
  tietHocKy3: number;
  tongTiet: number;
  congTacKhac: string;
  tongCong: number;
}

export default function ThongKePhanCongPage() {
  const [data, setData] = useState<ThongKeItem[]>([]);

  useEffect(() => {
    axios.get('/api/thong-ke-phan-cong') // API backend bạn đã triển khai
      .then(res => setData(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="thongke-container">
      <h2>BẢNG PHÂN CÔNG CÔNG TÁC GIẢNG VIÊN CƠ HỮU</h2>
      <table className="thongke-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Mã GV</th>
            <th>Họ và tên</th>
            <th>Tên học phần</th>
            <th>Mã HP</th>
            <th>Số TC</th>
            <th>Số tiết</th>
            <th>Lớp</th>
            <th>HK1</th>
            <th>HK2</th>
            <th>HK3</th>
            <th>Tổng tiết</th>
            <th>Công tác khác</th>
            <th>Tổng cộng</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, i) => (
            <tr key={i}>
              <td>{i + 1}</td>
              <td>{item.maGiangVien}</td>
              <td>{item.hoTen}</td>
              <td>{item.hocPhan}</td>
              <td>{item.maHocPhan}</td>
              <td>{item.soTinChi}</td>
              <td>{item.soTiet}</td>
              <td>{item.soLop}</td>
              <td>{item.tietHocKy1}</td>
              <td>{item.tietHocKy2}</td>
              <td>{item.tietHocKy3}</td>
              <td>{item.tongTiet}</td>
              <td>{item.congTacKhac}</td>
              <td>{item.tongCong}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
