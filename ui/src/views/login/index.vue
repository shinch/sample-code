<template>
  <div> 
    <el-row :gutter="20">
      <el-col :span="8">&nbsp;</el-col>
      <el-col :span="8">
        <el-form ref="form" :model="loginVo" label-width="120px">
          <el-form-item label="ID">
            <el-input placeholder="ID를 입력 하세요" clearable v-model="loginVo.id"></el-input>
          </el-form-item>
          <el-form-item label="Password">
            <el-input placeholder="PASSWORD를 입력 하세요" clearable v-model="loginVo.password" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="FormController().submit()" v-loading.fullscreen.lock="loadingVo.fullScreen">Login</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="8">&nbsp;</el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8">&nbsp;</el-col>
      <el-col :span="8">
        <el-table :data="userInfoVo" border>
          <el-table-column prop="id" label="ID"></el-table-column>
          <el-table-column prop="pw" label="Password"></el-table-column>
        </el-table>
      </el-col>
      <el-col :span="8">&nbsp;</el-col>
    </el-row>
  </div>
</template>
<script>
  import useData from './login-data.js'
  import Base from './login-base.js'
  import Controller from './login-controller.js'
  import Dao from './login-dao.js'
  import DataBind from './login-data-bind.js'
  import { Loading } from 'element-ui';

  export default {
    name: 'loginIndex',
    components: { },
    mixins: [Base, Controller, Dao, DataBind],
    data () {
      return useData
    }
  }
</script>