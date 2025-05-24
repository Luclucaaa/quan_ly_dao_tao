import { BrowserRouter as Router, Routes, Route, NavLink, Navigate } from 'react-router-dom';
import ThongTinChungPage from "./pages/ThongTinChungPage";
import './pages/QuanLyDaoTaoPage.css';
import KhungChuongTrinhPage from './pages/KhungChuongTrinhPage';
import './pages/KhungChuongTrinhPage.css'
import HocPhanPage from './pages/HocPhanPage';
import CotDiemPage from './pages/DeCuongChiTietPage';
import KeHoachDayHocPage from './pages/KeHoachDayHocPage';
import GiangVienPage from './pages/GiangVienPage';
import './pages/GiangVienPage.css'
import KeHoachPhanCongPage from './pages/KeHoachMonHoc';
import KeHoachMonHocPage from './pages/KeHoachMonHoc';
import LoginPage from './pages/LoginPage';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/quan-li-dao-tao/*" element={<QuanLyDaoTaoPage />} />
        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

function QuanLyDaoTaoPage() {
  return (
    <div className="layout-container">
      <aside className="sidebar">
        <h2>Quản lý đào tạo</h2>
        <ul>
          <li><NavLink to="/quan-li-dao-tao/thong-tin-chung">Thông tin chung</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/khung-chuong-trinh">Khung Chương Trình</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/hoc-phan">Học phần</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/de-cuong-chi-tiet">Đề Cương Chi Tiết</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/ke-hoach-day-hoc">Kế Hoạch Dạy Học</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/giang-vien">Giảng Viên</NavLink></li>
          <li><NavLink to="/quan-li-dao-tao/ke-hoach-mon-hoc">Kế hoạch mở nhóm & Phân công giảng dạy​</NavLink></li>
          {/* <li><NavLink to="/quan-li-dao-tao/ke-hoach-mon-hoc">Thống kê​</NavLink></li> */}
          <li><NavLink to="/login">Đăng xuất</NavLink></li>
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
          <Route path="*" element={<div><h2>Chọn một mục ở sidebar</h2></div>} />
        </Routes>
      </main>
    </div>
  );
}
