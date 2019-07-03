export default {
  methods: {
    PollingController() {
      let self = this
      return {
        topKeywords() {  
          setInterval(function(){
            if ( self.$route.path.startsWith( '/search' )) {
              self.KeywordDao().top.excute()
            }
          }, 3000);
        }
      }
    }
  }
}
