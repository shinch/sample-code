<template>
  <div>
    <el-container>
      <el-header height="90"><topSearch ref="topSearch"/></el-header>
      <el-container>
        <el-aside width="150px"><history ref="history"/></el-aside>
        <el-main>
          <el-row>
            <el-col :span="12">
              <el-input placeholder="검색어 입력" v-model="searchInfo.keyword" clearable>
                 <el-button slot="append" icon="el-icon-search" @click="SearchController().submit()"></el-button>
              </el-input>
              <el-table :data="tableData">
                <el-table-column fixed prop="placeName" label="장소명" width="200"></el-table-column>
                <el-table-column prop="address" label="지번주소" width="200"></el-table-column>
                <el-table-column prop="roadAddress" label="도로명주소" width="200"></el-table-column>
                <el-table-column prop="phoneNumebr" label="전화번호" width="150"></el-table-column>
                <el-table-column fixed="right" label="Operations" width="110">
                  <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-place" @click="MapController().changeMap(scope.$index, scope.row)" circle></el-button>
                    <a :href="scope.row.placeLink" target="_blank"><el-button type="warning" icon="el-icon-location-information" circle></el-button></a>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination background layout="prev, pager, next" :total="searchPage.total" :page-size	="searchPage.pageSize" :current-page.sync="searchPage.currentPage" @current-change="SearchController().changePage()"></el-pagination>
            </el-col>
            <el-col :span="12"><mainMap ref="mainMap"/></el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
  import mainMap from '../../components/map/index'
  import history from '../../components/history/index'
  import topSearch from '../../components/top/index'

  import Base from './search-base.js'
  import Computed from './search-computed.js'
  import Controller from './search-controller.js'
  import Dao from './search-dao.js'
  import DataBind from './search-data-bind.js'
  import useData from './search-data.js'

  export default {
    name: 'searchIndex',
    components: { mainMap, history, topSearch },
    mixins: [Base, Computed, Controller, Dao, DataBind],
    data () {
      return useData
    }
  }
</script>
