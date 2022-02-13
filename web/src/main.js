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
Vue.prototype.$httpX = axios.create({
  baseURL: "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate?access_token=24.f611b2d9b75bf7b2f8cdb026c1a86670.2592000.1646725923.282335-25575534", //在此处修改后端的地址和端口
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})

new Vue({
  router,
  // store,
  render: h => h(App)
}).$mount('#app')
