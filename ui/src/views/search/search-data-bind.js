export default {
  methods: {
    PlaceBind() {
      let self = this
      return {
        init(placeInfos) {
          self.tableData = placeInfos.content
          self.searchPage.total = placeInfos.totalElements
          self.searchPage.pageSize = placeInfos.size
          self.searchPage.currentPage = placeInfos.number
        }
      }
    }
  }
}