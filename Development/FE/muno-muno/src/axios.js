// src/axios.js
import axios from "axios";

// Spring 서버의 기본 URL 설정 (예: localhost:8080)
const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // 스프링 서버 URL로 설정
  headers: {
    'Content-Type': 'application/json',
  },
});
apiClient.interceptors.request.use(
    (config) => {
        // localStorage에서 JWT 토큰 가져오기
        const token = localStorage.getItem("JwtToken");

        if (token) {
            // 토큰이 있으면 Authorization 헤더에 추가
            config.headers["Authorization"] = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        // 요청 오류 처리
        return Promise.reject(error);
    }
);

export default apiClient;
