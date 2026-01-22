// Auth Types
export interface RegisterRequest {
  email: string;
  password: string;
  fullName: string;
  avatarUrl?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
  deviceName?: string;
}

export interface AuthResponse {
  status: string;
  data: {
    accessToken: string;
    user: {
      id: number;
      fullName: string;
      avatarUrl: string;
    };
  };
}

export interface ApiResponse<T = any> {
  status: 'success' | 'error';
  message?: string;
  data?: T;
}
