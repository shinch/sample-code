<template>
  <div margin>
    <el-pagination background layout="prev, next" :total="searchPage.total" :page-size	="searchPage.pageSize" :current-page.sync="searchPage.currentPage" @current-change="HistoryController().changePage()"></el-pagination>
    <el-timeline :reverse="reverse">
      <el-timeline-item v-for="(histories, index) in histories" :key="index" :timestamp="histories.searchAt">
        {{histories.keyword}}
      </el-timeline-item>
    </el-timeline>
  </div>
</template>
<script>
  import Base from './history-base.js'
  import Computed from './history-computed.js'
  import Controller from './history-controller.js'
  import Dao from './history-dao.js'
  import DataBind from './history-data-bind.js'
  import useData from './history-data.js'

  export default {
    name: 'searchIndex',
    components: { },
    mixins: [Base, Computed, Controller, Dao, DataBind],
    data () {
      return useData
    }
  };
</script>
<style>
.group {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>