export interface Screening {
    id: number;
programId: number;
submitterId: number;
title: string;
genre: string;
description: string;
room: string;
scheduledTime: string; // ISO
state: string;
staffMemberId: number;
submittedTime: string;
reviewedTime: string;
}
