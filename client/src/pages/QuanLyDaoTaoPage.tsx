import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import ThongTinChungPage from "./ThongTinChungPage";
import KhungChuongTrinhPage from './KhungChuongTrinhPage';
import HocPhanPage from './HocPhanPage';
import CotDiemPage from './DeCuongChiTietPage';
import KeHoachDayHocPage from './KeHoachDayHocPage';
import GiangVienPage from './GiangVienPage';
import KeHoachMonHocPage from './KeHoachMonHoc';
import LoginPage from './LoginPage';

export default function QuanLyDaoTaoPage() {
  return (
    <Router>
      <div className="layout-container">
        <aside className="sidebar">
          <h2>Quản lý đào tạo</h2>
          <ul>
            <li><NavLink to="/thong-tin-chung">Thông tin chung</NavLink></li>
            <li><NavLink to="/khung-chuong-trinh">Khung Chương Trình</NavLink></li>
            <li><NavLink to="/hoc-phan">Học phần</NavLink></li>
            <li><NavLink to="/de-cuong-chi-tiet">Đề Cương Chi Tiết</NavLink></li>
            <li><NavLink to="/ke-hoach-day-hoc">Kế Hoạch Dạy Học</NavLink></li>
            <li><NavLink to="/giang-vien">Giảng Viên</NavLink></li>
            <li><NavLink to="/ke-hoach-mon-hoc">Kế hoạch mở nhóm & Phân công giảng dạy​</NavLink></li>
            <li><NavLink to="/ke-hoach-mon-hoc">Thống kê</NavLink></li>
            <li><NavLink to="/ke-hoach-mon-hoc">Đăng xuất</NavLink></li>


          </ul>
        </aside>
        <main className="main-content">
          <Routes>
            <Route path="/thong-tin-chung" element={<ThongTinChungPage />} />
            <Route path="/khung-chuong-trinh" element={<KhungChuongTrinhPage/>} />
            <Route path="/hoc-phan" element={<HocPhanPage/>} />
            <Route path="/de-cuong-chi-tiet" element={<CotDiemPage/>} />
            <Route path="/ke-hoach-day-hoc" element={<KeHoachDayHocPage/>} />
            <Route path="/giang-vien" element={<GiangVienPage/>} />
            <Route path="/ke-hoach-mon-hoc" element={<KeHoachMonHocPage/>} />
            <Route path="/dang-nhap" element={<LoginPage />} />

            <Route path="*" element={<div><h2>Chọn một mục ở sidebar</h2></div>} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}
