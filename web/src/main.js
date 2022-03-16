import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/reset.scss'
import axios from 'axios'
// import store from './store'
import qs from 'qs'
import * as echarts from 'echarts';

Vue.prototype.$echarts = echarts

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
  baseURL: "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate?access_token=24.f0ad9ec14d12993bd2d8c789bed12f36.2592000.1649421012.282335-25575534", //在此处修改后端的地址和端口
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})

new Vue({
  router,
  // store,
  render: h => h(App)
}).$mount('#app')
