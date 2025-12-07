// src/router.tsx
import React from "react";
import { createBrowserRouter } from "react-router-dom";

import App from "./App";
import ProtectedRoute from "./components/common/ProtectedRoute";
import RoleRoute from "./components/common/RoleRoute";

import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";

import DashboardPage from "./pages/dashboard/DashboardPage";

import ProgramListPage from "./pages/programs/ProgramListPage";
import ProgramFormPage from "./pages/programs/ProgramFormPage";

import ScreeningListPage from "./pages/screenings/ScreeningListPage";
import ScreeningFormPage from "./pages/screenings/ScreeningFormPage";

import UserListPage from "./pages/users/UserListPage";

import StaffTicketsPage from "./pages/staff/StaffTicketsPage";
import StaffReservationsPage from "./pages/staff/StaffReservationsPage";

export const router = createBrowserRouter([
  // 🔐 Public auth routes
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "/register",
    element: <RegisterPage />
  },

  // 🔐 Protected app
  {
    path: "/",
    element: (
      <ProtectedRoute>
        <App />
      </ProtectedRoute>
    ),
    children: [
      {
        index: true,
        element: <DashboardPage />
      },

      // ---------------------------------
      // PROGRAMS
      // ---------------------------------

      // View/search programs: όλοι εκτός ADMIN
      {
        path: "programs",
        element: (
          <RoleRoute
            roles={[
              "VISITOR",
              "USER",
              "PROGRAMMER",
              "STAFF",
              "SUBMITTER"
            ]}
          >
            <ProgramListPage />
          </RoleRoute>
        )
      },

      // Create/update programs:
      // USER, PROGRAMMER, STAFF, SUBMITTER
      {
        path: "programs/new",
        element: (
          <RoleRoute
            roles={["USER", "PROGRAMMER", "STAFF", "SUBMITTER"]}
          >
            <ProgramFormPage />
          </RoleRoute>
        )
      },
      {
        path: "programs/:id",
        element: (
          <RoleRoute
            roles={["USER", "PROGRAMMER", "STAFF", "SUBMITTER"]}
          >
            <ProgramFormPage />
          </RoleRoute>
        )
      },

      // ---------------------------------
      // SCREENINGS
      // ---------------------------------

      // View/search screenings: όλοι εκτός ADMIN
      {
        path: "screenings",
        element: (
          <RoleRoute
            roles={[
              "VISITOR",
              "USER",
              "PROGRAMMER",
              "STAFF",
              "SUBMITTER"
            ]}
          >
            <ScreeningListPage />
          </RoleRoute>
        )
      },

      // Create/update screenings:
      // USER, PROGRAMMER, STAFF, SUBMITTER
      {
        path: "screenings/new",
        element: (
          <RoleRoute
            roles={["USER", "PROGRAMMER", "STAFF", "SUBMITTER"]}
          >
            <ScreeningFormPage />
          </RoleRoute>
        )
      },
      {
        path: "screenings/:id",
        element: (
          <RoleRoute
            roles={["USER", "PROGRAMMER", "STAFF", "SUBMITTER"]}
          >
            <ScreeningFormPage />
          </RoleRoute>
        )
      },

      // ---------------------------------
      // STAFF pages (review assigned screenings, tickets κτλ.)
      // ---------------------------------
      {
        path: "tickets",
        element: (
          <RoleRoute roles={["STAFF"]}>
            <StaffTicketsPage />
          </RoleRoute>
        )
      },
      {
        path: "reservations",
        element: (
          <RoleRoute roles={["STAFF"]}>
            <StaffReservationsPage />
          </RoleRoute>
        )
      },

      // ---------------------------------
      // ADMIN – user management ONLY
      // ---------------------------------
      {
        path: "users",
        element: (
          <RoleRoute roles={["ADMIN"]}>
            <UserListPage />
          </RoleRoute>
        )
      }
    ]
  }
]);
