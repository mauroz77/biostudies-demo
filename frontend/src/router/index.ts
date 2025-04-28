import { createRouter, createWebHistory } from 'vue-router'

import Browser from '../views/Browser.vue'
import StudyDetail from '../views/StudyDetail.vue'
import About from '../views/About.vue'

const routes = [
  { path: '/', redirect: '/studies' }, // redirect / to /studies
  { path: '/studies', name: 'Browser', component: Browser },
  { path: '/studies/:accno', name: 'study-detail', component: StudyDetail },
  { path: '/about', name: 'About', component: About },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
