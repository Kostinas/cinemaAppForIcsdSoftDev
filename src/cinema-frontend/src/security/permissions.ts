// src/security/permissions.ts
import type { Role } from "../types/user";

// --- Βοηθητικά για τα προγράμματα ---

// Όλοι εκτός από ADMIN βλέπουν προγράμματα
export const canViewPrograms = (role?: Role) =>
!!role && role !== "ADMIN";

// USER, PROGRAMMER, STAFF, SUBMITTER μπορούν να δημιουργήσουν πρόγραμμα
export const canCreatePrograms = (role?: Role) =>
role === "USER" ||
role === "PROGRAMMER" ||
role === "STAFF" ||
role === "SUBMITTER";

// --- Βοηθητικά για προβολές ---

export const canViewScreenings = (role?: Role) =>
!!role && role !== "ADMIN";

export const canCreateScreenings = (role?: Role) =>
role === "USER" ||
role === "PROGRAMMER" ||
role === "STAFF" ||
role === "SUBMITTER";

// --- Ειδικά για Programmer / Staff / Submitter / Admin ---

export const isProgrammer = (role?: Role) => role === "PROGRAMMER";
export const isStaff = (role?: Role) => role === "STAFF";
export const isSubmitter = (role?: Role) => role === "SUBMITTER";
export const isAdmin = (role?: Role) => role === "ADMIN";
