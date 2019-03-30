<template>
  <div slot="right" class="content-page" v-if="subjectsDetail_page">
    <div class="content">
     <!-- <div class="filter-container">
          <router-link to="/subjects/subjectsList">
          <!--</router-link>-->
      <el-button size="small" class="returns" @click=goBack()>返回</el-button>
      </div>
      <div class="booking-list">
        <span class="booking-list-tit">个人信息</span>
        <el-row class="booking-list-item">
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="4">
                  <div class="grid-content bg-purple">
                    SID:
                  </div>
                </el-col>
                <el-col :span="20">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.sid}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple-light">
              <el-row>
                <el-col :span="4">
                  <div class="grid-content bg-purple">
                    姓名:
                  </div>
                </el-col>
                <el-col :span="20">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.name}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple-light">
              <el-row>
                <el-col :span="4">
                  <div class="grid-content bg-purple">
                    性别:
                  </div>
                </el-col>
                <el-col :span="20">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.sex | reverseSex}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
        <el-row class="booking-list-item">
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="6">
                  <div class="grid-content bg-purple">
                    总体状态:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.overallStatusCy | overallStatusCy}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="6">
                  <div class="grid-content bg-purple">
                    入组日期:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.inGroupDate | date}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="6">
                  <div class="grid-content bg-purple">
                    分组方案:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.group}} 组<span v-if="detailData.base.group == 'C'">{{detailData.base.riskLevel | riskLevel}}</span>
                    <span v-if="detailData.base.group == 'A'">(结肠镜组)</span>
                    <span v-if="detailData.base.group == 'B'">(便潜血组)</span>
                  </div>

                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
        <el-row class="booking-list-item">
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="5">
                  <div class="grid-content bg-purple">
                    手机号:
                  </div>
                </el-col>
                <el-col :span="19">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.phone}}
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="7">
                  <div class="grid-content bg-purple">
                    资格审核表:
                  </div>
                </el-col>
                <el-col :span="17">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.reviewStatus | insertStatus}}
                    <router-link to="/home/subjectInsert">
                      <el-button type="text"  v-if=" detailData.base.reviewStatus == '1'"> 录入</el-button>
                    </router-link>
                    <router-link :to="{path:'/subjects/showSubjectInsert',query:{id:detailData.base.id,sid:detailData.base.sid}}">
                    <el-button type="text"  v-if=" detailData.base.reviewStatus == '2' && detailData.base.approvalStatus!=1"> 查看</el-button>
                    </router-link>
                    <router-link :to="{path:'/home/subjectInsert',query:{sid:detailData.base.sid,id:detailData.base.id}}">
                    <el-button type="text"  v-if=" detailData.base.editStatus == '1'"> 编辑</el-button>
                    </router-link>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <el-row>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    危险因素调查:
                  </div>
                </el-col>
                <el-col :span="16">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.riskFactorStatus | insertStatus}}
                    <router-link :to="{path:'/home/riskFactors',query:{sid:detailData.base.sid,flag:5}}">
                    <el-button type="text"  v-if=" detailData.base.riskFactorStatus == '1'"> 录入</el-button>
                    </router-link>
                    <router-link :to="{path:'/subjects/showRiskFactor',query:{id:detailData.hospitalRiskFactor.id,sid:detailData.hospitalRiskFactor.sid}}" v-if="detailData.hospitalRiskFactor">
                      <el-button type="text"  v-if="detailData.base.riskFactorStatus == '2' && detailData.hospitalRiskFactor.approvalStatus!=1"> 查看</el-button>
                    </router-link>
                    <router-link :to="{path:'/home/riskFactors',query:{sid:detailData.base.sid,id:detailData.hospitalRiskFactor.id,flag:5}}" v-if="detailData.hospitalRiskFactor">
                      <el-button type="text"  v-if=" detailData.hospitalRiskFactor.editStatus == '1'"> 编辑</el-button>
                    </router-link>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>

      <!--数据列表-->
      <h5>FIT列表</h5>
      <el-table
        :data="detailData.fit"
        border
        style="width: 100%;">
        <el-table-column
          prop="fitCode"
          label="编码"
        >
        </el-table-column>
        <el-table-column
          prop="releaseDate"
          label="编码录入时间"
          :formatter="dateFormat"
          width="160"
        >
        </el-table-column>
        <el-table-column
          label="结果录入状态"
          width="120"
        >
          <template slot-scope="scope">
            <span>{{scope.row.insertStatus | insertStatus}}</span>
          </template>
        </el-table-column>

        <el-table-column
          width="120"
          label="结果录入时间 "
          :formatter="dateFormat"
          prop="resultDate"
        >
        </el-table-column>
        <el-table-column
          label="是否有结果"
          width="120"
        >
          <template slot-scope="scope">
            <span>{{scope.row.resultStatus | resultStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="upLineValue"
          label="上线值"
          width="120"
        >
        </el-table-column>
        <el-table-column
          prop="downLineValue"
          label="下线值"
          width="100"
        >
        </el-table-column>
        <el-table-column
          label="FIT结果"
          width="100"
        >
          <template slot-scope="scope">
            <span>{{scope.row.result | result}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="noResonResult"
          label="无结果原因"
          width="160"
        >
        </el-table-column>

        <!--<el-table-column-->
          <!--label="操作"-->
        <!--&gt;-->
          <!--<template slot-scope="scope">-->
            <!--<el-button type="text"> 查看</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
      <!--DNA列表-->
      <h5>粪便DNA列表</h5>
      <el-table
        :data="detailData.dna"
        border
        style="width: 100%;">
        <el-table-column
          label="编码录入状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.code_entry_status | codeEntryStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dna_code"
          label="编码"
        >
        </el-table-column>

        <el-table-column
          prop="person_code"
          label="工作人员编码"
        >
        </el-table-column>
        <el-table-column
          label="DNA结果"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.code_entry_status == 2 && scope.row.dna_check_inform_status ==2">{{scope.row.dna_check_result | result}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="DNA结果状态"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.code_entry_status == 2 && scope.row.dna_check_inform_status ==2">已返回</span>
            <span v-if="scope.row.code_entry_status == 2 && scope.row.dna_check_inform_status != 2">待返回</span>
            <!--<span>{{scope.row.dna_check_enter_status | codeEntryStatus}}</span>-->
          </template>
        </el-table-column>
        <el-table-column
          prop="dna_community_inform_status"
          label="结果发放状态"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.code_entry_status == 2 && scope.row.dna_check_inform_status ==2">{{scope.row.dna_community_inform_status | dnaCommunityInformStatus }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="PDF文件"
        >
          <template slot-scope="scope">
            <el-button type="text" class="btnStyle" size="small" v-if="scope.row.code_entry_status == 2 && scope.row.dna_check_inform_status ==2 &&scope.row.dna_check_filePath != null&&scope.row.dna_check_filePath != ''" ><a :href="serverName + '/base/dnafile/downFile?filePath=' + scope.row.dna_check_filePath">下载PDF</a></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--结肠镜列表-->
      <h5>结肠镜列表</h5>
      <el-table
        :data="detailData.colonoscopy"
        border
        style="width: 100%;">
        <!--<el-table-column
          prop="sid"
          label="SID"

        >
        </el-table-column>-->
        <el-table-column
          label="肠镜预约状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.reserve_status | reserveStatus}}</span>
          </template>
        </el-table-column>
        <!--<el-table-column
          label="肠镜预约时间"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.result_status | resultStatus}}</span>
          </template>
        </el-table-column>-->
        <el-table-column
          label="肠镜检查状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.examination_status | examinationStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="肠镜完成状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.finished_status | finishedStatus}}</span>
          </template>
        </el-table-column>
        <!--<el-table-column
          label="结果状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.result_status | resultStatus}}</span>
          </template>
        </el-table-column>-->
        <!--<el-table-column
          label="病理录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.pathology_status | codeEntryStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="告知书录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.notification_entry_status | codeEntryStatus}}</span>
          </template>
        </el-table-column>-->
        <el-table-column
          label="告知书发放状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.notification_issue_status | notificationIssueStatus}}</span>
            <router-link :to="{path:'/colonscopy/report3',query:{sid:scope.row.sid,id:scope.row.id,show:1}}" v-if="scope.row.notification_entry_status==2">
              <el-button type="text"  > 查看</el-button>
            </router-link>
          </template>
        </el-table-column>
        <!--<el-table-columnx
          label="操作"
        >
          <template slot-scope="scope">
            <router-link to="reportForm/regionColonscopyList">
              <el-button type="text"  > 查看</el-button>
            </router-link>
          </template>
        </el-table-column>-->
      </el-table>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        subjectsDetail_page: false,
        btnAuth: {
          examination_query_btn: false,
        },
        formLabelWidth: '100px',
        serverName:SERVER_NAME,
        "detailData":{
          "base":{//基本信息
            "sid":"",
            "name":"",
            "age":"",
            "sex":"",
            "phone":"",
            "inGroupDate":"",
            "group":"",
            "overall_status_cy":"",
            "reviewStatus":"",
            "riskFactorStatus":""
          },
          "hospitalRiskFactor":{},
          "fit":[//fit检查
            {
              "code_entry_status":"",//编号录入状态
              "insert_status":"",//结果录入状态
              "fit_sode":"",//FIT编码，即噗噗管ID
              "release_date":"",//发放日期，录入FIT编码日期
              "release_person_code":"",//FIT编码，即噗噗管ID
              "test_date":"",//检测日期
              "result_status":"",//是否有结果，1：有结果，2：无结构
              "result_date":"",//录入结果时间
              "up_line_value":"",//上线值
              "down_line_value":"",//下线值
              "result":"",//FIT阴阳性判断：阳性：上线>=4且下线>=4；阴性：上线>=4且下线<4；无效：上线<4；无结果
              "no_reson_result":"",//无结果原因
              "sid":""//受试者唯一标识
            }
          ],
          "dna":[//DNA检查
            {
              "sid":"",//受试者唯一标识
              "dna_code":"",//粪便DNA编号
              "person_code":"",//发放经手人工作编码
              "release_date":"",//发放日期
              "code_entry_status":""//编码录入状态，1：未录入，2：已录入
            }
          ],
          "colonoscopy":[//结肠镜检查
            {
              "sid":"",//受试者唯一标识
              "reserve_Status":"",//结肠镜检查预约状态，1：未预约，2：已预约
              "examination_status":"",//结肠镜检查就诊状态，1：未就诊，2：已就诊
              "finished_status":"",//结肠镜完成状态，1：未完成，2：已完成
              "result_status":"",//结肠镜结果状态，1：未录入，2：已录入
              "pathology_status":"",//结肠镜病理录入状态，1：未录入，2：已录入
              "notification_entry_status":"",//结肠镜告知书录入状态，1：未录入，2：已录入
              "notification_issue_status":""//结肠镜告知书发放状态，1：未发放，2：已发放
            }
          ]
        },
      }
    },
    mounted(){
      let obj = this.checkPageAuth(this, "subjectsDetail_page", this.btnAuth)
      this.query();
    },
    methods: {
      showPDF(dnaCheckFilePath){
        window.open(dnaCheckFilePath)
      },
      //查询
      query(){
        $axios_http({
          url: "/base/hospital/person/detail/get/"+this.$route.query.id,
          data: {},
          vueObj: this
        }).then((res) => {
          this.detailData.base = res.data.data.base
          this.detailData.hospitalRiskFactor=res.data.data.hospitalRiskFactor
          this.detailData.fit = res.data.data.fit
          this.detailData.dna = res.data.data.dna
          this.detailData.colonoscopy = res.data.data.colonoscopy
        })
      },
      //日期格式化
      dateFormat:function(row, column) {
        var date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return date.substring(0,11)
      },
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .booking-list{
    margin:60px auto;
    padding: 20px;
    border:1px solid #e5e5e5;
    position:relative;
  }
  .booking-list-item{
    height: 40px;
    line-height: 40px;
  }
  .booking-list-tit{
    position: absolute;
    top:-22px;
    display:inline-block;
    padding: 10px;
    background: #ffffff;

  }
  .returns{
    float: left;
  }
  .print{
    float: right;
  }
  .content-page{
    padding: 20px;
    background: #fff;
    width:100%;
    height:100%;
  }
  .booking-list-welcome{
    margin: 50px auto;
    width: 50%;
    font-size:14px;
  }
  .booking-list-welcome p{
    height: 24px;
    line-height: 24px;
  }
  .p-title{
    margin-bottom:20px;
  }
  h5{
    height: 50px;
    line-height: 50px;
  }
</style>
