import http from "./http";
import type { Screening } from "../types/screening";

export interface ScreeningPayload {
programId: number;
title: string;
genre: string;
description: string;
room: string;
scheduledTime: string;
}

export const screeningsApi = {
list: async (): Promise<Screening[]> => {
    const res = await http.get<Screening[]>("/screenings");
    return res.data;
  },

  get: async (id: number): Promise<Screening> => {
    const res = await http.get<Screening>(`/screenings/${id}`);
    return res.data;
  },

  create: async (data: ScreeningPayload): Promise<Screening> => {
    const res = await http.post<Screening>("/screenings", data);
    return res.data;
  },

  update: async (
    id: number,
    data: ScreeningPayload
  ): Promise<Screening> => {
    const res = await http.put<Screening>(
      `/screenings/${id}`,
      data
    );
    return res.data;
  },

  delete: async (id: number): Promise<void> => {
    await http.delete(`/screenings/${id}`);
  }
};
