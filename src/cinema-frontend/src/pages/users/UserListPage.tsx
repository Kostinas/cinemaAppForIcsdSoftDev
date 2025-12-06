import React, { useEffect, useState } from "react";
import { usersApi } from "../../api/usersApi";
import type { UserRow } from "../../types/user";

const UserListPage: React.FC = () => {
  const [users, setUsers] = useState<UserRow[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await usersApi.list();
      setUsers(data);
    } catch (err) {
      console.error(err);
      setError("Αποτυχία φόρτωσης χρηστών");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void load();
  }, []);

  const handleDeactivate = async (id: number) => {
    if (!confirm("Απενεργοποίηση χρήστη;")) return;
    try {
      await usersApi.deactivate(id);
      await load();
    } catch (err) {
      console.error(err);
      alert("Αποτυχία απενεργοποίησης");
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Οριστική διαγραφή χρήστη;")) return;
    try {
      await usersApi.delete(id);
      await load();
    } catch (err) {
      console.error(err);
      alert("Αποτυχία διαγραφής");
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Users</h1>
      </div>

      {loading && <div>Loading...</div>}
      {error && <div className="error-box">{error}</div>}

      {!loading && !error && (
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Full name</th>
              <th>Active</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map(u => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.username}</td>
                <td>{u.fullName}</td>
                <td>{u.active ? "Yes" : "No"}</td>
                <td>
                  <button onClick={() => handleDeactivate(u.id)}>Deactivate</button>
                  <button onClick={() => handleDelete(u.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {users.length === 0 && (
              <tr>
                <td colSpan={5}>No users</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UserListPage;
