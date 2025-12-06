export interface Program {
    id: number;
name: string;
description: string;
startDate: string; // ISO string από LocalDate
endDate: string;
state: string;     // π.χ. "DRAFT", "APPROVED"
}
