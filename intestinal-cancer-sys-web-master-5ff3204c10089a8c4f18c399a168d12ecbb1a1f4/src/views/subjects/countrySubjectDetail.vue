<template>
  <div slot="right" class="content-page" v-if="country_person_detail_page">
    <div class="content">
      <div class="filter-container">
        <router-link to="/subjects/countrySubjectsList">
          <el-button size="small" class="returns">返回</el-button>
        </router-link>
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
                <el-col :span="6">
                  <div class="grid-content bg-purple">
                    地区医院:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.areaName}}
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
                    所属社区:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.communDeptName}}
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
                    分组方案:
                  </div>
                </el-col>
                <el-col :span="18">
                  <div class="grid-content bg-purple-light">
                    {{detailData.base.group | group}}
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
                    <!--<router-link to="/home/subjectInsert">
                      <el-button type="text"  v-if=" detailData.base.reviewStatus == '1'"> 录入</el-button>
                    </router-link>-->
                    <router-link :to="{path:'/subjects/showSubjectInsert',query:{sid:detailData.base.sid}}">
                      <el-button type="text"  v-if=" detailData.base.reviewStatus == '2'"> 查看</el-button>
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
                    <!--<router-link :to="{path:'/home/riskFactors',query:{id:detailData.base.sid}}">
                      <el-button type="text"  v-if=" detailData.base.riskFactorStatus == '1'"> 录入</el-button>
                    </router-link>-->
                    <router-link :to="{path:'/subjects/showRiskFactor',query:{sid:detailData.base.sid}}">
                      <el-button type="text"  v-if="detailData.base.riskFactorStatus == '2'" > 查看</el-button>
                    </router-link>
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
        </el-row>
      </div>
      <el-dialog :visible.sync="addFormDialog" :title="sampleTitle">
        <el-form :model="addForms">
          <el-form-item label="SID" :label-width="formLabelWidth" >
            <span>{{addForms.sid}}</span>
          </el-form-item>
          <el-form-item label="样本类型" :label-width="formLabelWidth" prop="sampleType">
            <span>{{addForms.sampleType | sampleType}}</span>
          </el-form-item>
          <el-form-item label="采样日期" :label-width="formLabelWidth">
            <span>{{addForms.collectDate}}</span>
          </el-form-item>
          <el-form-item label="样本ID" :label-width="formLabelWidth" >
            <span>{{addForms.sampleId}}</span>
          </el-form-item>
          <el-form-item label="冷冻盒编号" :label-width="formLabelWidth" >
            <span>{{addForms.frozenboxCode}}</span>
          </el-form-item>
          <div class="clearfloat">
            <el-form-item  label="位置" :label-width="formLabelWidth"  style="float: left;">
              <span>{{addForms.sampleColumn}}{{addForms.sampleLine}}</span>
            </el-form-item>
          </div>
          <el-form-item label="备注" :label-width="formLabelWidth" >
            <span>{{addForms.sampleNote}}</span>
          </el-form-item>
        </el-form>
      </el-dialog>
      <el-dialog
        title="血液样本"
        :visible.sync="showVisible"
        width="70%"
      >
        <el-form :model="showForm" :inline="true">
          <el-form-item label="采样日期" :label-width="formLabelWidth" prop="collectStatusDateBySql" >
            <el-date-picker
              v-model="showForm.collectStatusDateBySql"
              type="date"
              disabled
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              placeholder="采样日期"
              size="small"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="样本ID" :label-width="formLabelWidth" prop="sampleId">
            <el-input v-model="showForm.sampleId" auto-complete="off" disabled class="notification-input" size="small"></el-input>
          </el-form-item>
          <div >
            <div v-for="item,index in showForm.hospitalBiologicalSampleResultPOList" class="clearfloat" >
              <el-form-item :label="item.name" :label-width="formLabelWidth" prop="sampleId" style="float: left;">
                <el-checkbox v-model="item.checklist" disabled @change="changeList(item,index)"></el-checkbox>
              </el-form-item>
              <el-form-item label="冷冻盒编号" label-width="120px" prop="frozenBoxCode" style="float: left;">
                <el-input v-model="item.frozenBoxCode" disabled auto-complete="off" class="notification-input" size="mini" style="width: 120px;"></el-input>
              </el-form-item>
              <div class="clearfloat" style="float: left;" v-if="item.sampleType == 'W'">
                <el-form-item  label="位置" label-width="50px"  >
                  <el-input v-model="item.sampleColumnAndLine[0]" disabled auto-complete="off" style="width:60px;margin-left: 20px"  size="mini" ></el-input>
                </el-form-item>
              </div>
              <div class="clearfloat" style="float: left;" v-else>
                <el-form-item  label="位置" label-width="50px" >
                  <el-input v-model="item.sampleColumnAndLine[0]" disabled auto-complete="off" style="width:50px;margin-left: 20px"  size="mini" ></el-input>
                  <el-input v-model="item.sampleColumnAndLine[1]" disabled auto-complete="off" style="width:50px;margin-left: 20px"  size="mini" ></el-input>
                  <el-input v-model="item.sampleColumnAndLine[2]" disabled auto-complete="off" style="width:50px;margin-left: 20px"  size="mini" ></el-input>
                  <el-input v-model="item.sampleColumnAndLine[3]" disabled auto-complete="off" style="width:50px;margin-left: 20px"  size="mini" ></el-input>
                  <el-input v-model="item.sampleColumnAndLine[4]" disabled auto-complete="off" style="width:50px;margin-left: 20px"  size="mini" ></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
          <el-form-item label="备注" :label-width="formLabelWidth" prop="sampleId">
            <el-input v-model="showForm.sampleNote" auto-complete="off" disabled class="notification-input" size="small"></el-input>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--数据列表-->
      <h5>FIT列表</h5>
      <el-table
        :data="detailData.fit"
        border
        style="width: 100%;">
        <el-table-column
          label="编号录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.codeEntryStatus | codeEntryStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="fitCode"
          label="编码"
        >
        </el-table-column>
        <el-table-column
          prop="codeEntryTime"
          label="编码录入时间"
          :formatter="dateFormat"
          width="160"
        >
        </el-table-column>
        <el-table-column
          prop="releasePersonCode"
          label="工作人员编码"
          width="160"
        >
        </el-table-column>
        <el-table-column
          label="结果录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.insertStatus | codeEntryStatus}}</span>
          </template>
        </el-table-column>

        <el-table-column
          width="160"
          label="结果录入时间 "
          :formatter="dateFormat"
          prop="resultDate"
        >
        </el-table-column>
        <el-table-column
          label="结果"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.resultStatus | resultStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="upLineValue"
          label="上线值"
          width="160"
        >
        </el-table-column>
        <el-table-column
          prop="downLineValue"
          label="下线值"
          width="160"
        >
        </el-table-column>
        <el-table-column
          label="FIT阴阳性"
          width="120"
        >
          <template slot-scope="scope">
            <span>{{scope.row.result | result}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="noResonResul"
          label="无结果原因"
          width="160"
        >
        </el-table-column>

        <!--<el-table-column-->
          <!--label="操作"-->
        <!--&gt;-->
          <!--<template slot-scope="scope">-->
            <!--<el-button type="text"  > 查看</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
      <!--DNA列表-->
      <h5>DNA列表</h5>
      <el-table
        :data="detailData.dna"
        border
        style="width: 100%;">
        <el-table-column
          label="编码录入状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.codeEntryStatus | codeEntryStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dnaCode"
          label="编码"
        >
        </el-table-column>

        <el-table-column
          label="结果录入状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.dnaCheckStatus | codeEntryStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="DNA结果"
        >
          <template slot-scope="scope">
            <span>{{scope.row.dnaCheckResult | result}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dnaCheckGoal"
          label="FIT-DNA得分"
        >
        </el-table-column>
        <el-table-column
          prop="dnaCheckQuantnum"
          label="FIT定量化结果(ug hb/ml)"
          width="200"
        >
        </el-table-column>
        <el-table-column
          label="PDF文件"
        >
          <template slot-scope="scope">
              <el-button type="text" v-if="scope.row.dnaCheckFilePath != null"> <a :href="serverName + '/base/dnafile/downFile?filePath=' + scope.row.dnaCheckFilePath">下载PDF</a></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--结肠镜列表-->
      <h5>结肠镜列表</h5>
      <el-table
        :data="detailData.colonoscopy"
        border
        style="width: 100%;">
        <el-table-column
          label="肠镜预约状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.reserverStatus | reserveStatus}}</span>

          </template>
        </el-table-column>
        <el-table-column
          label="肠镜预约时间"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.reserveDate | date}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="检查状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.examinationStatus | examinationStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="完成状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.finishedStatus | finishedStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="结果录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.resultStatus | c_resultStatus}}</span>
            <router-link :to="{path:'/colonscopy/showReport1',query:{sid:scope.row.sid,id:scope.row.result_id,show:1}}" v-if="scope.row.resultStatus==2">
              <el-button type="text"  > 查看</el-button>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          label="病理录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.pathologyStatus | codeEntryStatus}}</span>
            <router-link :to="{path:'/colonscopy/showReport2',query:{sid:scope.row.sid,id:scope.row.pathology_id,show:1}}" v-if="scope.row.pathologyStatus==2">
              <el-button type="text" > 查看</el-button>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          label="告知书录入状态"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.notificationEntryStatus | notificationEntryStatus}}</span>
            <router-link :to="{path:'/colonscopy/showReport3',query:{sid:scope.row.sid,id:scope.row.id,show:1}}" v-if="scope.row.notificationEntryStatus==2">
              <el-button type="text" > 查看</el-button>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          label="告知书发放状态"
        >
          <template slot-scope="scope">
            <span>{{scope.row.notificationIssueStatus | notificationIssueStatus}}</span>
          </template>
        </el-table-column>
      </el-table>

      <h5>生物样本列表</h5>
      <el-table
        :data="detailData.biologicalSamples"
        border
        style="width: 100%;">
        <el-table-column label="血液样本" align="center">
          <el-table-column label="采集状态">
            <template slot-scope="scope">
              <span>{{scope.row.sample_A | sample_S}}</span>
              <span v-if="scope.row.sample_A == 1" style="margin-left: 15px;" @click="showData(scope.row.asid)"> <el-button type="text">查看</el-button></span>
            </template>
          </el-table-column>
          <el-table-column label="血清快递状态">
            <template slot-scope="scope">
              <span>{{scope.row.S | courierStatus}}</span>
            </template>
          </el-table-column>
          <el-table-column label="血浆快递状态" >
            <template slot-scope="scope">
              <span>{{scope.row.P | courierStatus}}</span>
            </template>
          </el-table-column>
          <el-table-column label="白细胞快递状态">
             <template slot-scope="scope">
              <span>{{scope.row.W | courierStatus}}</span>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="唾液样本"  align="center">
          <el-table-column label="录入状态">
            <template slot-scope="scope">
            <span>{{scope.row.sample_M | sample_S}}</span>
            <span v-if="scope.row.sample_M == 1" style="margin-left: 15px;" @click="showBiologicalSamples(scope.row.msid)"> <el-button type="text">查看</el-button></span>
          </template>
          </el-table-column>
          <el-table-column label="快递状态">
            <template slot-scope="scope">
              <span>{{scope.row.mCourierStatus | courierStatus}}</span>
            </template>
          </el-table-column>

        </el-table-column>
        <el-table-column label="粪便样本" align="center">
          <el-table-column label="录入状态">
            <template slot-scope="scope">
              <span>{{scope.row.sample_F | sample_S}}</span>
              <span v-if="scope.row.sample_F == 1" style="margin-left: 15px;" @click="showBiologicalSamples(scope.row.fsid)"> <el-button type="text">查看</el-button></span>
            </template>
          </el-table-column>
          <el-table-column label="快递状态">
            <template slot-scope="scope">
              <span>{{scope.row.fCourierStatus | courierStatus}}</span>
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        country_person_detail_page: false,
        btnAuth: {
          examination_query_btn: false,
        },
        serverName:SERVER_NAME,
        showVisible:false,
        sampleTitle:'',
        showForm:{},
        addForms:{},
        addFormDialog:false,
        formLabelWidth: '100px',
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
          ],
          "biologicalSamples":[//结肠镜检查
            {
              "sample_S":"",//结肠镜检查预约状态，1：未预约，2：已预约
              "sample_P":"",//结肠镜检查就诊状态，1：未就诊，2：已就诊
              "sample_W":"",//结肠镜完成状态，1：未完成，2：已完成
              "sample_M":"",//结肠镜结果状态，1：未录入，2：已录入
              "sample_F":"",//结肠镜病理录入状态，1：未录入，2：已录入
              "id":"", //血清id
              "pid":"", //血浆id
              "wid":"", //白细胞id
              "mid":"", //唾液id
              "fid":"", //粪便id
            }
          ]
        },
      }
    },
    mounted(){
      let obj = this.checkPageAuth(this, "country_person_detail_page", this.btnAuth)
      this.query();
      this.queryFit()
      this.queryDna()
      this.queryColonoscopy()
      this.queryBiological()
    },
    methods: {
      //查询
      query(){
        $axios_http({
          url: "/base/hospital/person/getPeopleListBySid",
          data: {
              sid:this.$route.query.id
          },
          vueObj: this
        }).then((res) => {
          this.detailData.base = res.data.data[0]
        })
      },
      queryFit(){
        $axios_http({
          url: "/base/hospital/person/getFitListByNationId",
          data: {
            sid:this.$route.query.id
          },
          vueObj: this
        }).then((res) => {
          this.detailData.fit = res.data.data
        })
      },
      showPDF(dnaCheckFilePath){
        window.open(dnaCheckFilePath)
      },
      //查看样本弹窗
      showData(id){
        $axios_http({
          url: "/base/area/sample/result/querySampleBloodId",
          data:{
            id:id
          },
          vueObj: this
        }).then((res) => {
          this.showVisible = true
          this.showForm = res.data.data
          for(let i = 0;i<this.showForm.hospitalBiologicalSampleResultPOList.length;i++){
            if(this.showForm.hospitalBiologicalSampleResultPOList[i].sampleColumnAndLine == null){
              this.showForm.hospitalBiologicalSampleResultPOList[i].sampleColumnAndLine = ["","","","","",]
            }
            if (this.showForm.hospitalBiologicalSampleResultPOList[i].sampleType=='S'){
              this.showForm.hospitalBiologicalSampleResultPOList[i].name = '血清'
            }else if (this.showForm.hospitalBiologicalSampleResultPOList[i].sampleType=='P'){
              this.showForm.hospitalBiologicalSampleResultPOList[i].name = '血浆'
            }else if (this.showForm.hospitalBiologicalSampleResultPOList[i].sampleType=='W'){
              this.showForm.hospitalBiologicalSampleResultPOList[i].name = '白细胞'
            }
          }
//          console.log(this.showForm);
        })
      },
      showBiologicalSamples(id){

        this.addFormDialog = true
        this.addForms = {}
        $axios_http({
          url: "/base/hospital/person/getSampleTypeDetailById",
          data: {
            id:id
          },
          vueObj: this
        }).then((res) => {

          if(res.data.data[0].sampleType == 'M'){
            this.sampleTitle = '唾液样本'
          }else {
            this.sampleTitle = '粪便样本'
          }
          this.addForms = res.data.data[0]
        })
      },
      queryDna(){
        $axios_http({
          url: "/base/hospital/person/getStollDNAListByNationId",
          data: {
            sid:this.$route.query.id
          },
          vueObj: this
        }).then((res) => {
          this.detailData.dna = res.data.data
        })
      },
      queryColonoscopy(){
        $axios_http({
          url: "/base/hospital/person/getColonoscopyByNationId",
          data: {
            sid:this.$route.query.id
          },
          vueObj: this
        }).then((res) => {
          this.detailData.colonoscopy = res.data.data
        })
      },
      queryBiological(){
        $axios_http({
          url: "/base/hospital/person/getSampleType",
          data: {
            sid:this.$route.query.id
          },
          vueObj: this
        }).then((res) => {
          // 循环血清、血浆、白细胞快递状态
          for(let val of res.data.data){
            for(let key of val.mapCourierStatus){
              if(key.W){
                val.W=key.W;
              }else if(key.P){
                val.P=key.P;
              }else if(key.S){
                val.S=key.S;
              }
            }
          }
          this.detailData.biologicalSamples = res.data.data
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
    height: 50px;
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
  .clearfloat:after{display:block;clear:both;content:"";visibility:hidden;height:0}
</style>
