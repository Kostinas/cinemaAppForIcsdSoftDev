import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { programsApi } from "../../api/programsApi";
import type { Program } from "../../types/program";

const ProgramListPage: React.FC = () => {
  const [programs, setPrograms] = useState<Program[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
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

  const handleDelete = async (id: number) => {
    if (!confirm("Σίγουρα διαγραφή;")) return;
    try {
      await programsApi.delete(id);
      await load();
    } catch (err) {
      console.error(err);
      alert("Αποτυχία διαγραφής");
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Programs</h1>
        <button onClick={() => navigate("/programs/new")}>+ New Program</button>
      </div>

      {loading && <div>Loading...</div>}
      {error && <div className="error-box">{error}</div>}

      {!loading && !error && (
        <table className="data-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>State</th>
              <th>Start</th>
              <th>End</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {programs.map(p => (
              <tr key={p.id}>
                <td>
                  <Link to={`/programs/${p.id}`}>{p.name}</Link>
                </td>
                <td>{p.state}</td>
                <td>{p.startDate}</td>
                <td>{p.endDate}</td>
                <td>
                  <button onClick={() => navigate(`/programs/${p.id}`)}>Edit</button>
                  <button onClick={() => handleDelete(p.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {programs.length === 0 && (
              <tr>
                <td colSpan={5}>No programs</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ProgramListPage;
