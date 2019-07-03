export default {
  methods: {
    SearchController() {
      let self = this
      return {
        submit() {
          self.KeywordSearchDao().search.excute()
        },
        changePage() {
          self.searchInfo.nowPage = self.searchPage.currentPage
          self.KeywordSearchDao().search.excute()
        }
      }
    },
    MapController() {
      let self = this
      return {
        changeMap(index, row) {
          self.$refs.mainMap.viewMap(row.xposition, row.yposition, row.placeName)
        }
      }
    }
  }
}
