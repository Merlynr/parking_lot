import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      name: "张三",
      power: "研发部经理",
      role_id: "2",
    }
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
