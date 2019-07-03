export default {
  mounted: function () {
    this.$store.commit('cookiesInfo', { 'userId': this.$cookie.get('user-id'), 'token': this.$cookie.get('token')})
    this.KeywordDao().top.excute()
    this.PollingController().topKeywords()
  }
}  