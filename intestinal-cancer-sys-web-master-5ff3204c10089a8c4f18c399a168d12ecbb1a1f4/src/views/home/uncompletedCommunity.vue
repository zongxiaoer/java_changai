<template>
  <div slot="right" class="content-page" v-if="uncompletedCommunity_page">
    <router-link to="/home/areaHome">
      <el-button size="mini" class="return-home">返 回</el-button>
    </router-link>
    <el-table
      :data="queryResult.tableData"
      border
      :default-sort = "{prop: 'commityName', order: 'descending'}"
      style="width: 100%;margin-top: 20px;">
      <el-table-column
        type="index"
        align="center"
        sortable
        label="序号"
        width="50">
      </el-table-column>
      <el-table-column
        prop="commityName"
        label="所属区"
        width="80"
       >
      </el-table-column>
      <el-table-column
        sortable
        label="未完成危险因素调查表"
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未完成危险因素调查表', communityDeptId:scope.row.communityDeptId,type:'c1'}}" v-if="scope.row.notRiskFactors>0">
          <span style="color: blue;">{{scope.row.notRiskFactors}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        label="未录入FIT编号"
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未录入FIT编号', communityDeptId:scope.row.communityDeptId,type:'c2'}}" v-if="scope.row.notFitcodes>0">
          <span style="color: blue;">{{scope.row.notFitcodes}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        label="未录入FIT结果"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未录入FIT结果', communityDeptId:scope.row.communityDeptId,type:'c3'}}" v-if="scope.row.notFitResult>0">
          <span style="color: blue;">{{scope.row.notFitResult}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        label="未录入粪便DNA编号"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未录入粪便DNA编号', communityDeptId:scope.row.communityDeptId,type:'c4'}}" v-if="scope.row.notStollDNA>0">
          <span style="color: blue;">{{scope.row.notStollDNA}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        label="未预约结肠镜检查"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未预约结肠镜检查', communityDeptId:scope.row.communityDeptId,type:'c5'}}" v-if="scope.row.notEntryallocations>0">
          <span style="color: blue;">{{scope.row.notEntryallocations}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        label="未完成结肠镜检查"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未完成结肠镜检查', communityDeptId:scope.row.communityDeptId,type:'c6'}}" v-if="scope.row.notFinishes>0">
          <span style="color: blue;">{{scope.row.notFinishes}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="notNotices"
        label="未通知筛查结果告知书"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未通知筛查结果告知书', communityDeptId:scope.row.communityDeptId,type:'c7'}}" v-if="scope.row.notNotices>0">
          <span style="color: blue;">{{scope.row.notNotices}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column
        label="未发放粪便DNA结果"
        sortable
      >
        <template slot-scope="scope"><router-link :to="{path:'/home/agency',query:{title:'未发放粪便DNA结果', communityDeptId:scope.row.communityDeptId,type:'c8'}}" v-if="scope.row.notDNAResults>0">
          <span style="color: blue;">{{scope.row.notDNAResults}}</span>
        </router-link>
          <span v-else>0</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: 'Right',
    data () {
      return {
        //权限判定
        uncompletedCommunity_page:false,
        btnAuth:{
        },
        //查询结果
        "queryResult":{
          "pageNo":1,//当前页
          "pageSize":10,//每页的条数
          "totalPageCount":0,
          "tableData":[]
        },
        //分页
        "queryResultSource":{
          "pageNoSource":1,//当前页
          "pageSizeSource":15,//每页的条数
          "totalPageCount":0
        },
      }
    },
    mounted(){
      this.$store.commit('LOGOUT_USER');

      let obj = this.checkPageAuth(this,"uncompletedCommunity_page",this.btnAuth);
      this.query();
    },
    methods:{
      //查询
      query(){
        $axios_http({
          url:"/base/hospital/person/notentry/riskfactors/notRiskfactorsByAreaId",
          data:{
          },
          vueObj:this
        }).then((res)=>{
          this.queryResult.tableData=res.data.data;
        })
      },
    }}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
