import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../components/HomePage.vue';
import ProductPage from '../components/ProductPage.vue';
import PropertyPage from '../components/PropertyPage.vue';
import CommunityPage from '../components/CommunityPage.vue';

const routes = [
  { path: '/', name: 'Home', component: HomePage },
  { path: '/product', name: 'Product', component: ProductPage },
  { path: '/property', name: 'Property', component: PropertyPage },
  { path: '/community', name: 'Community', component: CommunityPage },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
