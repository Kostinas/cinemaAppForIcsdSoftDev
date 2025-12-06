import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { screeningsApi } from "../../api/screeningsApi";
import type { Screening } from "../../types/screening";

const ScreeningListPage: React.FC = () => {
  const [screenings, setScreenings] = useState<Screening[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await screeningsApi.list();
      setScreenings(data);
    } catch (err) {
      console.error(err);
      setError("Αποτυχία φόρτωσης προβολών");
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
      await screeningsApi.delete(id);
      await load();
    } catch (err) {
      console.error(err);
      alert("Αποτυχία διαγραφής");
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Screenings</h1>
        <button onClick={() => navigate("/screenings/new")}>+ New Screening</button>
      </div>

      {loading && <div>Loading...</div>}
      {error && <div className="error-box">{error}</div>}

      {!loading && !error && (
        <table className="data-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Program</th>
              <th>Room</th>
              <th>State</th>
              <th>Time</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {screenings.map(s => (
              <tr key={s.id}>
                <td>{s.title}</td>
                <td>{s.programId}</td>
                <td>{s.room}</td>
                <td>{s.state}</td>
                <td>{s.scheduledTime}</td>
                <td>
                  <button onClick={() => navigate(`/screenings/${s.id}`)}>Edit</button>
                  <button onClick={() => handleDelete(s.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {screenings.length === 0 && (
              <tr>
                <td colSpan={6}>No screenings</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ScreeningListPage;
