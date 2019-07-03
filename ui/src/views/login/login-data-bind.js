export default {
  methods: {
    LoginBind() {
      let self = this
      return {
        viewloading() {
          self.fullscreenLoading = true
        },
        hideloading() {
            self.fullscreenLoading = false
        }
      }
    },
    UserInfoBind() {
      let self = this
      return {
      }
    },
    CookiesBind() {
      let self = this
      return {
        initVo() {
          self.cookiesVo.userId = self.$cookie.get('user-id')
          self.cookiesVo.token = self.$cookie.get('token')
        }
      }
    }
  }
}