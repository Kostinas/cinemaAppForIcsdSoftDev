import http from "./http";
import type { Program } from "../types/program";

export interface ProgramPayload {
name: string;
description: string;
startDate: string;
endDate: string;
state?: string;
}

export const programsApi = {
list: async (): Promise<Program[]> => {
    const res = await http.get<Program[]>("/programs");
    return res.data;
  },

  get: async (id: number): Promise<Program> => {
    const res = await http.get<Program>(`/programs/${id}`);
    return res.data;
  },

  create: async (data: ProgramPayload): Promise<Program> => {
    const res = await http.post<Program>("/programs", data);
    return res.data;
  },

  update: async (id: number, data: ProgramPayload): Promise<Program> => {
    const res = await http.put<Program>(`/programs/${id}`, data);
    return res.data;
  },

  delete: async (id: number): Promise<void> => {
    await http.delete(`/programs/${id}`);
  },

  changeState: async (id: number, newState: string, actorUserId: number): Promise<void> => {
    await http.put(`/programs/${id}/state`, null, {
      params: { newState, actorUserId }
    });
  }
};
