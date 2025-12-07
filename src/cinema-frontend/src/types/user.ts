// src/types/user.ts
export type Role =
| "VISITOR"
| "USER"
| "PROGRAMMER"
| "STAFF"
| "SUBMITTER"
| "ADMIN";

export interface AuthUser {
id: number;
username: string;
role: Role;
}
