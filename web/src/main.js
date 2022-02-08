import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/reset.scss'
import axios from 'axios'
// import store from './store'
import qs from 'qs'

Vue.config.productionTip = false
Vue.prototype.qs = qs
Vue.prototype.$http = axios.create({
  baseURL: "http://localhost:8848", //在此处修改后端的地址和端口
  headers: {
    'Content-Type': 'application/json',
    'token': localStorage.getItem("token")
  }
})
new Vue({
  router,
  // store,
  render: h => h(App)
}).$mount('#app')
