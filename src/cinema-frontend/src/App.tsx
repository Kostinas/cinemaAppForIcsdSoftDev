// src/App.tsx
import React from "react";
import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "./context/AuthContext";
import {
  canViewPrograms,
  canViewScreenings,
  isStaff,
  isAdmin
} from "./security/permissions";

const App: React.FC = () => {
  const { user, logout } = useAuth();
  const role = user?.role;

  return (
    <div className="app-layout">
      <aside className="sidebar">
        <div className="sidebar-header">
          <h2>Cinema Manager</h2>
          {user && (
            <div className="sidebar-user">
              {user.username} ({user.role})
            </div>
          )}
        </div>

        <nav className="sidebar-nav">
          <NavLink to="/" end>
            Dashboard
          </NavLink>

          {canViewPrograms(role) && (
            <NavLink to="/programs">Programs</NavLink>
          )}

          {canViewScreenings(role) && (
            <NavLink to="/screenings">Screenings</NavLink>
          )}

          {isStaff(role) && (
            <>
              <NavLink to="/tickets">Tickets</NavLink>
              <NavLink to="/reservations">Reservations</NavLink>
            </>
          )}

          {isAdmin(role) && <NavLink to="/users">Users</NavLink>}
        </nav>

        <div className="sidebar-footer">
          <button onClick={logout}>Logout</button>
        </div>
      </aside>

      <main className="main-content">
        <Outlet />
      </main>
    </div>
  );
};

export default App;
