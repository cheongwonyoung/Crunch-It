const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000, // 원하는 포트 번호로 설정합니다.
    proxy: {
      '/ws': {
        target: 'http://localhost:8080',  // 백엔드 서버 주소
        ws: true,  // WebSocket 요청을 프록시하기 위한 설정
        changeOrigin: true,  // 원본 헤더를 백엔드의 호스트로 변경
      },
    }
  },
});
