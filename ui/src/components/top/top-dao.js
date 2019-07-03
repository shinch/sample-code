export default {
    methods: {
      KeywordDao () {
        let self = this
        return {
          top: {
            excute () {
              self.axios.get('/api/keyword/top', {headers: {'Accept': 'application/vnd.shinch.api.report-V1+json', 'X-Api-User': self.$store.state.loginInfo.userId, 'X-Api-Token': self.$store.state.loginInfo.authToken }})
                .then(response => { self.KeywordDao().top.complate(response.data) })
                .catch(error => { self.KeywordDao().top.error(error) })
            },
            error (error) {
              if ( error.response ) {
                if ( error.response.status == 401 ) {
                  self.$router.push({path: '/login'})
                }
              }
            },
            complate (responseData) {
              self.TopKeywordsBind().init(responseData)
            }
          }
        }
      }
    }
  }