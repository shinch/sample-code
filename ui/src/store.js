import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginInfo: {
      userId: '',
      authToken: ''
    }
  },
  mutations: {
    loginInfo (state, payload) {
      state.loginInfo.userId = payload.userId
      state.loginInfo.authToken = payload.token
    },
    cookiesInfo (state, userInfo) {
      state.loginInfo.userId = userInfo.userId
      state.loginInfo.authToken = userInfo.token
    }
  },
  actions: {

  }
})
