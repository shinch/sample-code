export default {
    methods: {
      TopKeywordsBind() {
        let self = this
        return {
          init(keywordInfos) {
            self.topKeywordVos = keywordInfos
          }
        }
      }
    }
  }