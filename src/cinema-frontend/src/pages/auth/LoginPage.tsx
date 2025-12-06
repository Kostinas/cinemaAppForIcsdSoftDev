import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import TextField from "../../components/common/TextField";

const LoginPage: React.FC = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      await login({ username, password });
      navigate("/");
    } catch (err) {
      console.error(err);
      setError("Λάθος στοιχεία σύνδεσης");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h1>Login</h1>
        {error && <div className="error-box">{error}</div>}

        <TextField
          label="Username"
          name="username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          required
        />
        <TextField
          label="Password"
          name="password"
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        <p>
          Δεν έχεις λογαριασμό; <Link to="/register">Register</Link>
        </p>
      </form>
    </div>
  );
};

export default LoginPage;
