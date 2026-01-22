// API Configuration
export const API_CONFIG = {
  BASE_URL: 'http://172.16.31.149:8080/api/v1', // IP của máy chạy backend
  TIMEOUT: 10000,
};

export const API_ENDPOINTS = {
  // Auth endpoints
  REGISTER: '/auth/register',
  LOGIN: '/auth/login',
  LOGOUT: '/auth/logout',
  
  // User endpoints
  GET_PROFILE: '/user/profile',
  UPDATE_PROFILE: '/user/profile',
};
