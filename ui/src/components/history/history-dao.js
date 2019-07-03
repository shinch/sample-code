export default {
  methods: {
    HistoryDao () {
      let self = this
      return {
        search: {
          excute () {
            self.axios.get('/api/keyword/my/' + self.nowPage, {headers: {'Accept': 'application/vnd.shinch.api.report-V1+json', 'X-Api-User': self.$store.state.loginInfo.userId, 'X-Api-Token': self.$store.state.loginInfo.authToken }})
              .then(response => { self.HistoryDao().search.complate(response.data) })
              .catch(error => { self.HistoryDao().search.error(error) })
          },
          error (error) {
            if ( error.response ) {
              if ( error.response.status == 401 ) {
                self.$router.push({path: '/login'})
              }
            }
          },
          complate (responseData) {
            self.HistoryBind().init(responseData)
          }
        }
      }
    }
  }
}