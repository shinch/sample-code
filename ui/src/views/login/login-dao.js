export default {
  methods: {
    AuthenticationDao () {
      let self = this
      return {
        login: {
          excute () {
            self.axios.post('/api/authentication/login/' + self.loginVo.id, '', {headers: {'Accept': 'application/vnd.shinch.api.report-V1+json', 'X-Api-User': self.loginVo.id, 'X-Api-Password': self.loginVo.password }})
              .then(response => { self.AuthenticationDao().login.complate(response.data) })
              .catch(error => { self.AuthenticationDao().login.error(error) })
          },
          error (error) {
            let errorMessage = "로그인에 실패 하였습니다."
            if ( error.response ) {
              if ( error.response.status == 401 ) {
                errorMessage = "로그인 정보가 올바르지 않습니다."
              }
            }
            self.LoginBind().hideloading()
            self.$message.error(errorMessage);
          },
          complate (responseData) {
            self.LoginBind().hideloading()
            self.AuthCheckController().authCheck()
          }
        },
        authCheck: {
          async excute () {
            try {
              let response = await self.axios.get('/api/authentication/check/' + self.cookiesVo.userId, {headers: {'Accept': 'application/vnd.shinch.api.report-V1+json', 'X-Api-User': self.cookiesVo.userId, 'X-Api-Token': self.cookiesVo.token }})
              self.AuthenticationDao().authCheck.complate(response.data)
              return true;
            } catch (error){
              self.AuthenticationDao().authCheck.error(error)
            }
          },
          error (error) {
          },
          complate (responseData) {
            self.$store.commit('loginInfo', self.cookiesVo)
          }
        }
      }
    }
  }
}