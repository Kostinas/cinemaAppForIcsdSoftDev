import http from "./http";
import type { UserRow } from "../types/user";

export const usersApi = {
list: async (): Promise<UserRow[]> => {
    const res = await http.get<UserRow[]>("/users");
    return res.data;
  },

  deactivate: async (id: number): Promise<void> => {
    await http.put(`/users/${id}/deactivate`);
  },

  delete: async (id: number): Promise<void> => {
    await http.delete(`/users/${id}`);
  }
};
