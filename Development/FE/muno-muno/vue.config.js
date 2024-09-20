// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })


const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost',  // 호스트를 0.0.0.0 대신 localhost로 설정
    port: 8082,          // 포트를 8081에서 8082로 변경
    // 필요 시 자동으로 열리게 설정
    open: true
  }
})
