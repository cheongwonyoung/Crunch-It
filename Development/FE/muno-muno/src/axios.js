// src/axios.js
import axios from 'axios';

// Spring 서버의 기본 URL 설정 (예: localhost:8080)
const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // 스프링 서버 URL로 설정
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;
