// src/pages/programs/ProgramListPage.tsx
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { programsApi } from "../../api/programsApi";
import type { Program } from "../../types/program";
import { useAuth } from "../../context/AuthContext";
import { canCreatePrograms } from "../../security/permissions";

const ProgramListPage: React.FC = () => {
  const [programs, setPrograms] = useState<Program[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const { user } = useAuth();
  const navigate = useNavigate();

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await programsApi.list();
      setPrograms(data);
    } catch (err) {
      console.error(err);
      setError("Αποτυχία φόρτωσης προγραμμάτων");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void load();
  }, []);

  return (
    <div>
      <div className="page-header">
        <h1>Programs</h1>

        {canCreatePrograms(user?.role) && (
          <button onClick={() => navigate("/programs/new")}>
            + New Program
          </button>
        )}
      </div>

      {loading && <div>Loading...</div>}
      {error && <div className="error-box">{error}</div>}

      {!loading && !error && (
        <table className="data-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Year</th>
              <th>State</th>
            </tr>
          </thead>
          <tbody>
            {programs.map(p => (
              <tr key={p.id} onClick={() => navigate(`/programs/${p.id}`)}>
                <td>{p.title}</td>
                <td>{p.year}</td>
                <td>{p.state}</td>
              </tr>
            ))}
            {programs.length === 0 && (
              <tr>
                <td colSpan={3}>No programs</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ProgramListPage;
