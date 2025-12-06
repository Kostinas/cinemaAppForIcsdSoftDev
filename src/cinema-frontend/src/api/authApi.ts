import http from "./http";
import type { AuthResponse } from "../types/auth";

export interface LoginPayload {
username: string;
password: string;
}

export interface RegisterPayload {
username: string;
password: string;
fullName: string;
}

export const authApi = {
login: async (data: LoginPayload): Promise<AuthResponse> => {
    const res = await http.post<AuthResponse>("/auth/login", data);
    return res.data;
  },

  register: async (data: RegisterPayload): Promise<void> => {
    await http.post("/auth/register", data);
  },

  validateToken: async (): Promise<void> => {
    await http.get("/auth/validate");
  },

  logout: async (): Promise<void> => {
    await http.post("/auth/logout");
  }
};
