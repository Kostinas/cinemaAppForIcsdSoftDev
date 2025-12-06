import React from "react";
import { createBrowserRouter } from "react-router-dom";

import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import DashboardPage from "./pages/DashboardPage";

import ProgramListPage from "./pages/programs/ProgramListPage";
import ProgramFormPage from "./pages/programs/ProgramFormPage";

import ScreeningListPage from "./pages/screenings/ScreeningListPage";
import ScreeningFormPage from "./pages/screenings/ScreeningFormPage";

import UserListPage from "./pages/users/UserListPage";

import Layout from "./components/Layout/Layout";
import { ProtectedRoute } from "./components/common/ProtectedRoute";

export const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "/register",
    element: <RegisterPage />
  },
  {
    path: "/",
    element: (
      <ProtectedRoute>
        <Layout />
      </ProtectedRoute>
    ),
    children: [
      {
        index: true,
        element: <DashboardPage />
      },
      {
        path: "programs",
        element: <ProgramListPage />
      },
      {
        path: "programs/new",
        element: <ProgramFormPage />
      },
      {
        path: "programs/:id",
        element: <ProgramFormPage />
      },
      {
        path: "screenings",
        element: <ScreeningListPage />
      },
      {
        path: "screenings/new",
        element: <ScreeningFormPage />
      },
      {
        path: "screenings/:id",
        element: <ScreeningFormPage />
      },
      {
        path: "users",
        element: <UserListPage />
      }
    ]
  }
]);
