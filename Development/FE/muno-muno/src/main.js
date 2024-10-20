import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const app = createApp(App);




app.use(router);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); // 플러그인 사용
app.use(pinia);
app.mount("#app");
