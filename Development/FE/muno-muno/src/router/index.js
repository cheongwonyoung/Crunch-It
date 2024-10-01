import { createRouter, createWebHistory } from 'vue-router';
import HomeP from '@/pages/Home.vue';
import ProductP from '@/pages/Product.vue';
import PropertyP from '@/pages/Property.vue';
import CommunityP from '@/pages/CommunityPage.vue';
import PostDetailP from "@/pages/PostDetailPage.vue";
import WritePostP from "@/pages/WritePostPage.vue"
import EditPostP from "@/pages/EditPostPage.vue";

const routes = [
  { path: '/', name: 'Home', component: HomeP },
  { path: '/product', name: 'Product', component: ProductP },
  { path: '/property', name: 'Property', component: PropertyP },
  { path: '/community', name: 'Community', component: CommunityP },
  { path: '/community/:id', name: 'PostDetail', component: PostDetailP, props:true },
  { path: '/community/create', name: 'WritePost', component: WritePostP, props:true },
  { path: '/community//modify/:id', name: 'EditPost', component: EditPostP, props:true }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
