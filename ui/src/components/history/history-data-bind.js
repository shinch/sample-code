export default {
  methods: {
    HistoryBind() {
      let self = this
      return {
        init(historyInfos) {
          self.histories = historyInfos.content
          self.searchPage.total = historyInfos.totalElements
          self.searchPage.pageSize = historyInfos.size
          self.searchPage.currentPage = historyInfos.number
        }
      }
    }
  }
}