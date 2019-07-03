<template>
  <div>
    <span v-for="topKeyword in topKeywordVos" :key="topKeyword.keyword">
      <el-badge :value="topKeyword.cnt" class="item">
        <el-button size="small">{{topKeyword.keyword}}</el-button>
      </el-badge>
    </span>
  </div>
</template>
<script>
  import Base from './top-base.js'
  import Computed from './top-computed.js'
  import Controller from './top-controller.js'
  import Dao from './top-dao.js'
  import DataBind from './top-data-bind.js'
  import useData from './top-data.js'

  export default {
    name: 'topIndex',
    components: { },
    mixins: [Base, Computed, Controller, Dao, DataBind],
    data () {
      return useData
    }
  }
</script>
<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>