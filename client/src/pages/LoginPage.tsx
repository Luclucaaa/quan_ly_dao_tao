import { useState } from 'react';
import axios from 'axios';
import './LoginPage.css';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [showRegister, setShowRegister] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const res = await axios.post('/api/nguoi-dung/login', { username, password });
      localStorage.setItem('user', JSON.stringify(res.data));
      navigate('/quan-li-dao-tao');
    } catch {
      setError('Tài khoản hoặc mật khẩu không đúng.');
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin} style={{ display: showRegister ? 'none' : 'flex' }}>
        <h2>Đăng nhập</h2>
        <input
          type="text"
          placeholder="Tên đăng nhập"
          value={username}
          onChange={e => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Mật khẩu"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        {error && <div className="error">{error}</div>}
        <button type="submit">Đăng nhập</button>
        <div className="toggle-form">
          <span style={{ color: 'black' }}>Bạn chưa có tài khoản? </span>
          <a href="#" onClick={e => { e.preventDefault(); setShowRegister(true); }}>Đăng ký</a>
        </div>
      </form>
      {showRegister && <RegisterPage onBack={() => setShowRegister(false)} />}
    </div>
  );
}

function RegisterPage({ onBack }: { onBack: () => void }) {
  const [form, setForm] = useState({
    hoTen: '',
    email: '',
    soDienThoai: '',
    namSinh: '',
    username: '',
    password: '',
    vaiTro: 'giangvien',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    if (!form.hoTen || !form.email || !form.soDienThoai || !form.namSinh || !form.username || !form.password) {
      setError('Vui lòng nhập đầy đủ thông tin.');
      return;
    }
    try {
      await axios.post('/api/nguoi-dung', {
        hoTen: form.hoTen,
        email: form.email,
        soDienThoai: form.soDienThoai,
        namSinh: Number(form.namSinh),
        username: form.username,
        password: form.password,
        vaiTro: form.vaiTro,
      });
      setSuccess('Đăng ký thành công!');
      setTimeout(() => onBack(), 1000);
    } catch {
      setError('Đăng ký thất bại.');
    }
  };

  return (
    <form className="login-form" onSubmit={handleSubmit}>
      <h2>Đăng ký</h2>
      <input
        type="text"
        name="hoTen"
        placeholder="Họ tên"
        value={form.hoTen}
        onChange={handleChange}
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={form.email}
        onChange={handleChange}
      />
      <div style={{ display: 'flex', gap: 10 }}>
        <input
          type="text"
          name="soDienThoai"
          placeholder="Số điện thoại"
          value={form.soDienThoai}
          onChange={handleChange}
          style={{ flex: 1 }}
        />
        <input
          type="number"
          name="namSinh"
          placeholder="Năm sinh"
          value={form.namSinh}
          onChange={handleChange}
          style={{ flex: 1 }}
        />
      </div>
      <input
        type="text"
        name="username"
        placeholder="Tên đăng nhập"
        value={form.username}
        onChange={handleChange}
      />
      <input
        type="password"
        name="password"
        placeholder="Mật khẩu"
        value={form.password}
        onChange={handleChange}
      />
      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}
      <button type="submit">Đăng ký</button>
      <div className="toggle-form">
        <span style={{ color: 'black' }}>Đã có tài khoản? </span>
        <a href="#" onClick={e => { e.preventDefault(); onBack(); }}>Đăng nhập</a>
      </div>
    </form>
  );
}
