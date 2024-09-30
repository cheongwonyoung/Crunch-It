import { createRouter, createWebHistory } from "vue-router";
import HomeP from "@/pages/Home.vue";
import ProductP from "@/pages/Product.vue";
import PropertyP from "@/pages/Property.vue";
import CommunityP from "@/pages/Community.vue";
import LoginP from "@/pages/LoginPage.vue";
import apiClient from "@/axios";

const routes = [
    { path: "/", name: "Home", component: HomeP },
    { path: "/product", name: "Product", component: ProductP },
    { path: "/property", name: "Property", component: PropertyP },
    { path: "/community", name: "Community", component: CommunityP },
    { path: "/login", name: "Login", component: LoginP },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('JwtToken'); // 토큰을 localStorage에서 가져옴
  
  if (to.name !== 'Login') {
    if(!token) next({name:'Login'});
    apiClient.get("/auth/authenticate").then(res=>{
      if(res.status == 200) next();
      else{
        next({name: 'Login'})
      } 
    }).catch(err=>{
      if(err.status==401) alert("로그인이 만료되었습니다. 다시 로그인 해주세요.");
      console.log(err);
      next({name: 'Login'});
    })
  } else {
    next();
  }
});

export default router;
