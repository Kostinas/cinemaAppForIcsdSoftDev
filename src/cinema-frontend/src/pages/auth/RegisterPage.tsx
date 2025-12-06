import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import TextField from "../../components/common/TextField";
import { authApi } from "../../api/authApi";

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [fullName, setFullName] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    try {
      await authApi.register({ username, password, fullName });
      setSuccess("Εγγραφή επιτυχής! Μπορείς να κάνεις login.");
      setTimeout(() => navigate("/login"), 1000);
    } catch (err) {
      console.error(err);
      setError("Αποτυχία εγγραφής");
    }
  };

  return (
    <div className="auth-page">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h1>Register</h1>
        {error && <div className="error-box">{error}</div>}
        {success && <div className="success-box">{success}</div>}

        <TextField
          label="Full name"
          name="fullName"
          value={fullName}
          onChange={e => setFullName(e.target.value)}
          required
        />
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

        <button type="submit">Register</button>

        <p>
          Έχεις ήδη λογαριασμό; <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
};

export default RegisterPage;
