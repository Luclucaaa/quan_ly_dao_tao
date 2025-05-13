import { useState } from 'react';
import axios from 'axios';
import './LoginPage.css';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await axios.get('/api/nguoi-dung');
      const user = res.data.find((u: any) => u.username === username);

      if (!user) {
        setError('Tài khoản không tồn tại.');
        return;
      }
      localStorage.setItem('user', JSON.stringify(user));
      navigate('/quan-li-dao-tao');

    } catch (err) {
      setError('Đã xảy ra lỗi khi đăng nhập.');
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
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
      </form>
    </div>
  );
}
