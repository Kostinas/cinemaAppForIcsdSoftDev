import React from "react";
import { NavLink } from "react-router-dom";

const Sidebar: React.FC = () => {
  return (
    <aside className="sidebar">
      <ul>
        <li>
          <NavLink to="/" end>
            Dashboard
          </NavLink>
        </li>
        <li>
          <NavLink to="/programs">Programs</NavLink>
        </li>
        <li>
          <NavLink to="/screenings">Screenings</NavLink>
        </li>
        <li>
          <NavLink to="/users">Users</NavLink>
        </li>
      </ul>
    </aside>
  );
};

export default Sidebar;
