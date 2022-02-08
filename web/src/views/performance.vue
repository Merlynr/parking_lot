<template>
    <div class="main">
    <el-select v-model="isYear" @change="fetchData">
        <el-option label="月度效绩" value="month"></el-option>
        <el-option label="年度效绩" value="year"></el-option>
    </el-select>
    <el-button @click="dialogVisible1 = true;" v-if="isBoos" class="top">修改效绩比例</el-button>
    <el-table
      :data="monthData"
      style="width: 100%;"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :highlight-current-row = "true"
      v-if="isYear == 'month'"
    >
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
      <el-table-column prop="user.name" label="执行人" sortable>
      </el-table-column>
      <el-table-column prop="user.role.role" label="身份">
      </el-table-column>
      <el-table-column prop="user.section.name" label="所属部门">
      </el-table-column>
      <el-table-column prop="date" label="时间段">
      </el-table-column>
      <el-table-column prop="performance" label="效绩" sortable>
      </el-table-column>
    </el-table>
     <el-table
      :data="yearData"
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :highlight-current-row = "true"
      v-if="isYear == 'year'"
    >
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
      <el-table-column prop="user.name" label="姓名" sortable>
      </el-table-column>
      <el-table-column prop="year" label="年份">
      </el-table-column>
      <el-table-column prop="description" label="年度考核等级">
      </el-table-column>
    </el-table>
    <el-dialog
      title="修改效绩比例"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>请注意,分数比例相加需等于100</span>
      <el-form v-model="form" class="form">
        <el-form-item label="签到比例分数">
            <el-input type="text" placeholder="请输入分数" v-model="form.signs"></el-input>
        </el-form-item>
        <el-form-item label="任务比例分数">
            <el-input type="text" placeholder="请输入分数" v-model="form.misss"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="changeRate()">确 定</el-button>
      </span>
    </el-dialog>
    <el-pagination background layout="sizes, prev, pager, next" :page-size="pageSize" :page-sizes="[5,10, 20, 50]" :current-page="pageNum" :total="total3" class="pagination" 
    @size-change="sizeChange" @current-change="currentChange" v-if="isYear =='month'"
    ></el-pagination>
    <!-- <el-dialog
      title="确认删除"
      :visible.sync="dialogVisible"
      width="30%">
      <span>确认删除该记录？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="remove()">确 定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
import  response from "../plugins/http.js"
export default {
    data() {
        return {
            monthData: [],
            yearData: [],
            pageNum: 1,
            total3: 100,
            pageSize: 5,
            dialogVisible: false,
            dialogVisible1: false,
            dialogVisible2: false,
            form: {},
            isYear: "month",
        }
    },
    methods: {
        async remove() {
            const res = await this.$http.post("data/delete",{
                _id: this.removeId
            })
            console.log(res.data);
            this.fetchData();
            this.dialogVisible = false;
            this.$message({
                type: "success",
                message: "删除成功!"
            })
        },
        currentChange(page) {
            this.pageNum = page;
            this.fetchData();
        },
        sizeChange(size) {
            this.pageSize = size;
            this.fetchData();
        },
        async changeRate() {
            const res = await this.$http.post("/api/signs/setSigns", this.form);
            response(res,this);
            this.dialogVisible1 = false;
        },
        async fetchData() {
            if(this.isYear == "year") {
                const push = await this.$http.get("/api/per/reCalc");
                console.log(push);
                const res  = await this.$http.get("/api/per/getAll");
                const level = await this.$http.get("/api/yeard/getAll");
                res.data.data.forEach(item => {
                    level.data.data.forEach(list => {
                        if(list.id == item.degreeId) {
                            item.description = list.yearDegree;
                        }
                    })
                });
                this.yearData = res.data.data;
            }
            else {
                const res = await this.$http.post("/api/performance/findPageByUserId", {
                    id: localStorage.id,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                });
                this.monthData = res.data.data.content;
                this.total3 = res.data.data.totalSize;
            }
        },
        async getRate() {
            const res = await this.$http.get("/api/signs/getAll");
            this.form = res.data.data;
            console.log(res.data.data);
        }
    },
    created() {
        this.fetchData();
        this.getRate();
    },
    computed:{
        isBoos() {
            return localStorage.roleId == 3;
        }
    }
}
</script>

<style lang="scss" scoped>
.main {
    width: 100%;
    height: 90vh;
    position: relative;
    overflow-y: auto;
    top: 25px;
    .pagination {
        position: relative;
        top: 50px;
        left: 400px;
    }
    .form {
        margin-top: 50px;
    }
    .top {
        margin-left: 15px;
    }
}
</style>