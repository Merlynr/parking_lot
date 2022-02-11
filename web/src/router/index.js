import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Main from '../views/Main.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      { path: '/', component: Main },
      {
        path: '/yuezhu',
        name: 'yuezhu',
        component: () => import("../views/manage/yuezhu.vue")
      },{
        path: '/chewei',
        component: () => import("../views/manage/chewei.vue")
      }
      // {
      //   path: '/performance',
      //   name: 'performance',
      //   component: () => import("../views/performance.vue")
      // },
      // {
      //   path: '/user',
      //   name: 'user',
      //   component: () => import("../views/user.vue")
      // },
      // {
      //   path: '/depart',
      //   name: 'depart',
      //   component: () => import("../views/depart.vue")
      // },
      // {
      //   path: "/mine",
      //   name: "mine",
      //   component: () => import("../views/mine.vue")
      // },
      // {
      //   path: "/information",
      //   name: "infomation",
      //   component: () => import("../views/information.vue")
      // },
      // {
      //   path: "/level",
      //   name: "level",
      //   component: () => import("../views/level.vue")
      // }
    ]
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/login.vue")
  }
]

const router = new VueRouter({
  routes
})

export default router
