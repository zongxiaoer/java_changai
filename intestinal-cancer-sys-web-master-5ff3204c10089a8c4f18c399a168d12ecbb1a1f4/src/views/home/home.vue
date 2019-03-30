<template>
    <div slot="right" class="content-page" v-if="home_page">
      <el-row :gutter="30">
        <el-col  :md="18" :lg="16" :xl="18">
          <div class="grid-content bg-purple">
            <div class="home-message-title">消息中心</div>
            <div class="home-message">
              <!-- <img src="../../assets/img/info.png" />
              <h3>欢迎参加中国结直肠癌筛选项目</h3>
              <p>您的一份奉献，将会使更多家庭从结直肠癌的早诊早治中受益</p> -->
              <homeNews></homeNews>  
            </div>
          </div>

        </el-col>
        <el-col   :md="6" :lg="8" :xl="6">
          <div class="grid-content bg-purple">
            <div class="home-message-title">放号通知</div>
            <div class="home-message">
              <div class="borderStyle">
                <el-carousel :interval="5000" arrow="always" height="250px" indicator-position="none">
                  <el-carousel-item v-for="(item,index) in this.allocationListData" :key="index">
                    <ul>
                      <li>
                        <span class="home-hospital-name">{{item.deptName}}</span>
                      </li>
                      <li>
                        <span class="home-hospital-time">{{item.period}} 可预约</span>
                      </li>
                      <li>
                        <span class="home-hospital-count">{{item.reserveable}}人</span>
                      </li>
                      <li v-if="item.reserveable>0">
                        <router-link :to="{path:'addBooking',query:{id:item.id}}">
                          <el-button type="primary" >立即预约</el-button>
                        </router-link>
                      </li>
                    </ul>
                  </el-carousel-item>
                </el-carousel>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row  class="home-message-list" >
        <el-col :span="4">
          <div class="home-message-title" >项目进度</div>
          <div class="home-message-item1">
            <router-link :to="{path:'/home/subjectInsert',query:{flag:2}}">
              <dl>
                  <dt>
                    <img src="../../assets/img/add-user.png" alt="" style="margin-top: -20px;">
                  </dt>
                <dd class="home-message-itemTitle">
                  新增受试者
                </dd>
              </dl>
            </router-link>
          </div>
          <div class="home-message-item1 ">
            <router-link to="/home/uncompletedResearch">
              <dl>
                  <dt>
                    <img src="../../assets/img/list-item.png" alt="">
                  </dt>
                <dd class="home-message-itemTitle">
                  退出研究查看
                </dd>
              </dl>
            </router-link>
          </div>
          <!--<div class="grid-content bg-purple">-->
            <!--<div class="add-user">-->
                <!--<router-link to="subjectInsert">-->
                  <!--<dl>-->
                    <!--<dt>-->
                      <!--<img src="../../assets/img/add-user.png" alt="">-->
                    <!--</dt>-->
                    <!--<dd class="add-user-title">-->
                      <!--新增受试者-->
                    <!--</dd>-->
                  <!--</dl>-->
                <!--</router-link>-->
            <!--</div>-->
          <!--</div>-->
        </el-col>
        <el-col :span="20">
          <div class="home-message-title" style="padding-left: 34px;">待办事项</div>
          <div class="home-message-item ">
            <router-link to="/home/uncompletedRiskFactors">
            <dl>
              <el-badge :value="backlog.riskFactors" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未完成危险因素调查表
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item ">
            <router-link to="/home/uncompletedFITNumber">
            <dl>
              <el-badge :value="backlog.notEntryFITCode" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未录入FIT编号
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item ">
            <router-link to="/home/uncompletedFITResult">
            <dl>
              <el-badge :value="backlog.notEntryFITResult" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未录入FIT结果
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item ">
            <router-link to="/home/uncompletedDNA">
            <dl>
              <el-badge :value="backlog.notStoolDnaCode" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未录入粪便DNA装置编号
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item"><!--/home/unreservedEnteroscopy-->
            <router-link :to="{path:'/home/unreservedEnteroscopy'}">
            <dl>
              <el-badge :value="backlog.notReserve" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未预约结肠镜检查
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item">
            <router-link to="/home/uncompletedEnteroscopy">
            <dl>
              <el-badge :value="backlog.notFinishExamination" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未完成结肠镜检查
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item">
            <router-link to="/home/noScreeningResults">
            <dl>
              <el-badge :value="backlog.notIssueNotification" class="item">
                <dt>
                  <img src="../../assets/img/list-item.png" alt="">
                </dt>
              </el-badge>
              <dd class="home-message-itemTitle">
                未发放筛查结果告知书
              </dd>
            </dl>
            </router-link>
          </div>
          <div class="home-message-item">
            <router-link to="/home/uncompletedDNAResults">
              <dl>
                <el-badge :value="backlog.notIssueDna" class="item">
                  <dt>
                    <img src="../../assets/img/list-item.png" alt="">
                  </dt>
                </el-badge>
                <dd class="home-message-itemTitle">
                  未通知粪便DNA结果
                </dd>
              </dl>
            </router-link>
          </div>
        </el-col>

      </el-row>

    </div>

</template>

<script>
import homeNews from '../components/homeNews'   //消息中心
  export default {
    name: 'Right',
    components:{
      homeNews
    },
    data () {
      return {
        allocationListData:[{
          name:'XXX社区医院',
          period:'2018-04-17',
          reserveable:'10'
        }],
        "backlog":{
          "riskFactors":'',//待收集危险因素
          "notEntryFITCode":'' ,//待录入FIT编号
          "notEntryFITResult":'' ,//待录入FIT结果
          "notStoolDnaCode":'',//未录入粪便DNA装置编号
          "notReserve":'',//未预约结肠镜检查
          "notFinishExamination":'',//未完成结肠镜检查
          "notIssueNotification":'',//未发放筛查结果告知书
          "notIssueDna":''
        },
        //权限判定
        home_page: false,
        btnAuth: {
          buttonRoleAdd: false,
          buttonRoleEditor: false,
          buttonRoleDel: false,
          buttonRoleQuery: false,
          buttonUserRoleDis: false
        },
        //查询条件
        "qc": {
          "name": "",
          "desc": ""
        },
        //查询结果
        "queryResult": {
          "pageNo": 1,//当前页
          "pageSize": 10,//每页的条数
          "totalPageCount": 0,
          "tableData": []
        },
        //分页
        "queryResultSource": {
          "pageNoSource": 1,//当前页
          "pageSizeSource": 15,//每页的条数
          "totalPageCount": 0
        },
        allocateResourcesData: [],
        formLabelWidth: '80px',
        rules: {
          name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {min: 1, max: 16, message: '长度在1到16个字符', trigger: 'blur'}
          ],
          desc: [
            {required: true, message: '请输入角色描述信息', trigger: 'blur'},
            {min: 1, max: 32, message: '长度在1到32个字符', trigger: 'blur'}
          ]
        }
      }
    },
    mounted(){
      this.$store.commit('LOGOUT_USER');
      let obj = this.checkPageAuth(this, "home_page", this.btnAuth);
      this.queryTelephoneNumber()
    },
    methods: {
      queryTelephoneNumber(){
        $axios_http({
          url: "/base/hospital/community/allocation/query",
          data: {},
          vueObj: this
        }).then((res) => {
          this.allocationListData=res.data.data;
          if(this.allocationListData.length==0){
            this.allocationListData=[
              {
                reserveable:'暂无预约'
              }
            ]
          }
        })
        this.queryTodoQuery();
      },
      queryTodoQuery(){
        $axios_http({
          url: "/base/hospital/community/todo/query",
          data: {},
          vueObj: this
        }).then((res) => {
          console.log(res)
          this.backlog.riskFactors=res.data.data.riskFactors;
          this.backlog.notEntryFITCode=res.data.data.notEntryFITCode;
          this.backlog.notEntryFITResult=res.data.data.notEntryFITResult;
          this.backlog.notStoolDnaCode=res.data.data.notStoolDnaCode;
          this.backlog.notReserve=res.data.data.notReserve;
          this.backlog.notFinishExamination=res.data.data.notFinishExamination;
          this.backlog.notIssueNotification=res.data.data.notIssueNotification;
          this.backlog.notIssueDna=res.data.data.notIssueDna;
          //this.allocationListData=res.data.data;
        })
      }

    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .home-message {
    height: 250px;
    text-align: center;
    background: #ffffff;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 4px;
    line-height: 30px;
  }
  .home-message img{
    height: 70%;
  }
  .home-message h3{
    color:#303133;
  }
  .home-message p{
    color:#99a9bf;
    font-size:14px;
  }
  .home-message-title{
    height: 25px;
    line-height: 25px;
    font-size: 14px;
    color:#B3B3BB;
  }
  .add-user{
    height: 260px;
    border-radius: 4px;
    background: #fff;
  }
  .home-message-list{
    margin-top: 20px;
  }
  .home-message-item{
    text-align: center;
    padding-top: 25px;
    height: 120px;
    background: #fff;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 4px;
    float: left;
    margin-left:34px;
    width:200px;
    margin-bottom:20px;
  }
  .home-message-item1{
    text-align: center;
    padding-top: 25px;
    height: 120px;
    background: #fff;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 4px;
    float: left;
    width:200px;
    margin-bottom:20px;
  }
  .home-message-top{
    margin-bottom: 20px;
  }
  .home-message-itemTitle{
    font-size: 12px;
     ;
  }
  .add-user{
    text-align: center;
    overflow-x: hidden;

  }
  .add-user-title{
    color:#419FFE;
  }
  .add-user dl{
    padding-top:60px;
    height: 260px;
    border:2px solid #AAD6F3;

  }
  .add-dna-btn{
    height:50px;
    color: #fff;
    background:#53A2E5;
    text-align: center;
    line-height:50px;
    font-size:12px ;

  }
  .home-message ul li{
    list-style-type: none;
    text-align: center;
  }
  .home-message ul{
    padding-top:40px;
  }
  .home-hospital-name{
    height:35px;
    line-height:35px;
    font-size:20px;

  }
  .home-hospital-time{
    color: #303133;
    font-size:20px;
  }
  .home-hospital-count{
    height:70px;
    line-height:70px;
    font-size:30px;
    font-weight:700;
  }
</style>
