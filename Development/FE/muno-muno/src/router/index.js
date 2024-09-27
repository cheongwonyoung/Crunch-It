import { createRouter, createWebHistory } from 'vue-router';
import HomeP from '@/pages/Home.vue';
import ProductP from '@/pages/Product.vue';
import PropertyP from '@/pages/Property.vue';
import CommunityP from '@/pages/Community.vue';

const routes = [
  { path: '/', name: 'Home', component: HomeP },
  { path: '/product', name: 'Product', component: ProductP },
  { path: '/property', name: 'Property', component: PropertyP },
  { path: '/community', name: 'Community', component: CommunityP },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
