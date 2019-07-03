export default {
  methods: {
    HistoryController() {
      let self = this
      return {
        init() {
          self.HistoryDao().search.excute()
        },
        changePage() {
          self.nowPage = self.searchPage.currentPage
          self.HistoryDao().search.excute()
        }
      }
    }
  }
}
