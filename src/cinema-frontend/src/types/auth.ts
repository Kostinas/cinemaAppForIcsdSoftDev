export interface User {
    id: number;
username: string;
fullName: string;
// πρόσθεσε ό,τι άλλο πεδίο έχει το UserResponse
}

export interface AuthResponse {
token: string;
user: User;
}
