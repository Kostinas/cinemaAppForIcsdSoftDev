// src/components/common/RoleRoute.tsx
import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import type { Role } from "../../types/user";

interface RoleRouteProps {
  roles: Role[];
  children: React.ReactElement;
}

const RoleRoute: React.FC<RoleRouteProps> = ({ roles, children }) => {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (!roles.includes(user.role)) {
    return <div>Δεν έχεις δικαίωμα πρόσβασης σε αυτή τη σελίδα.</div>;
  }

  return children;
};

export default RoleRoute;
