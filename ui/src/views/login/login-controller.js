export default {
  methods: {
    FormController() {
      let self = this
      return {
        submit() {
          self.LoginBind().viewloading()
          self.AuthenticationDao().login.excute()
        }
      }
    },
    AuthCheckController() {
      let self = this
      return {
        authCheck() {
          self.CookiesBind().initVo()
          if ( self.cookiesVo.userId != null && self.cookiesVo.token != null ) {
            self.AuthenticationDao().authCheck.excute().then( res => {
                  console.info('check auth : ', self.$store.state.loginInfo)
                  if ( self.$store.state.loginInfo.userId != '' && self.$store.state.loginInfo.authToken != '' ) {
                  self.$router.push({path: '/search'})
                }
              }
            )
          }
        }
      }
    }
  }
}  