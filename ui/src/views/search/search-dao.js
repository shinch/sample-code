export default {
  methods: {
    KeywordSearchDao () {
      let self = this
      return {
        search: {
          excute () {
            self.axios.get('/api/place/search/' + self.searchInfo.keyword + '/' + self.searchInfo.nowPage, {headers: {'Accept': 'application/vnd.shinch.api.report-V1+json', 'X-Api-User': self.$store.state.loginInfo.userId, 'X-Api-Token': self.$store.state.loginInfo.authToken }})
              .then(response => { self.KeywordSearchDao().search.complate(response.data) })
              .catch(error => { self.KeywordSearchDao().search.error(error) })
          },
          error (error) {
            if ( error.response ) {
              if ( error.response.status == 401 ) {
                self.$router.push({path: '/login'})
              }
            }
          },
          complate (responseData) {
            self.PlaceBind().init(responseData)
          }
        }
      }
    }
  }
}