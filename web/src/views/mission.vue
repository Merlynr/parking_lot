<template>
    <div class="main">
    <el-button @click="dialogVisible1 = true; isEdit = false;" v-if="!role">新增任务</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :highlight-current-row = "true">
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
      <el-table-column prop="title" label="任务名" width="200">
      </el-table-column>
      <el-table-column prop="content" label="任务描述" :show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column prop="promulgator.name" label="发布人" width="120">
      </el-table-column>
      <el-table-column prop="executor.name" label="执行人" width="120">
      </el-table-column>
      <el-table-column prop="degreeId" label="执行难度" sortable width="150">
      </el-table-column>
      <el-table-column prop="createdTime" label="创建时间" sortable width="180">
      </el-table-column>
      <el-table-column prop="finishTime" label="结束时间" sortable width="180">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" v-if="!role">
        <template slot-scope="scope" v-if="!scope.row.socer">
          <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="finish(scope.row)" type="text" size="small" v-if="scope.row.finishTime > nowTime">完成</el-button>
          <el-button @click="dialogVisible = true; removeId = scope.row.id" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="sizes, prev, pager, next" :page-size="pageSize" :page-sizes="[5, 10, 20, 50]" :current-page="pageNum" :total="total" class="pagination" 
    @size-change="sizeChange" @current-change="currentChange"
    ></el-pagination>
     <el-dialog
      title="确认删除"
      :visible.sync="dialogVisible"
      width="30%">
      <span>确认删除该记录？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="remove()">确 定</el-button>
      </span>
    </el-dialog>
     <el-dialog
      :title="isEdit ? '编辑任务' : '新增任务'"
      :visible.sync="dialogVisible1"
      width="30%">
      <el-form v-model="form" label-width="80px">
        <el-form-item label="任务名">
            <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="任务内容">
            <el-input v-model="form.content" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="执行人">
            <el-select v-model="form.executorId" placeholder="请选择执行者">
                <el-option v-for="(item,index) in userList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="任务难度">
            <el-select v-model="form.degreeId" placeholder="请选择任务难度">
                <el-option v-for="item in 10" :key="item" :label="item" :value="item"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="结束时间">
            <el-date-picker
            v-model="form.finishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
            </el-date-picker>
        </el-form-item>
        <el-form-item>
            <el-button @click="dialogVisible1 = false; form = {}; isEdit = false">取 消</el-button>
            <el-button type="primary" @click="add()">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      title="确认完成"
      :visible.sync="dialogVisible2"
      width="30%">
      <span>确认完成该任务？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="addScore">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import  response from "../plugins/http.js"
export default {
    data() {
        return {
            tableData: [],
            pageNum: 1,
            total: 100,
            pageSize: 5,
            dialogVisible: false,
            dialogVisible1: false,
            dialogVisible2: false,
            isEdit: false,
            form: {
                title: "",
                content: "",
                executorId: "",
                degreeId: 1,
                finishTime: "",
            },
            userList: [],
            removeId: 0,
            score: 90,
            nowTime: "",
        }
    },
    methods: {
        async remove() {
            const res = await this.$http.post("/api/mission/deleteMission", {
                id: this.removeId
            });
            response(res, this);
            this.fetchData();
            this.dialogVisible = false;
        },
        currentChange(page) {
            this.pageNum = page;
            this.fetchData();
        },
        sizeChange(size) {
            this.pageSize = size;
            this.fetchData();
        },
        edit(row) {
            this.isEdit = true;
            this.dialogVisible1 = true;
            this.form = row;
        },
        async add() {
            if(this.isEdit) {
                const res = await this.$http.post("/api/mission/editorMission", this.form);
                response(res, this);
                this.fetchData();
                this.dialogVisible1 = false;
            }
            else {
                if(!this.form.executorId || !this.form.title || !this.form.content || !this.form.degreeId || !this.form.finishTime) {
                    this.$message({
                        type: "warning",
                        message: "存在必填项未填"
                    })
                }
                else if(this.form.executorId == localStorage.id) {
                    this.$message({
                        type: "error",
                        message: "不可为自己发布任务"
                    })
                }
                else {
                    const getMonth = new Date().getMonth() + 1;
                    const year = new Date().getFullYear(), day = new Date().getDate(), month = getMonth > 9 ? getMonth : "0" + getMonth;
                    this.form.createdTime = `${year}-${month}-${day}`;
                    this.form.promulgatorId = localStorage.getItem("id");
                    const res = await this.$http.post("/api/mission/issuance", this.form);
                    response(res, this);
                    this.fetchData();
                    this.dialogVisible1 = false;
                }
            }
        },
        finish(row) {
            this.form = row;
            this.dialogVisible2 = true;
        },
        async addScore() {
            this.form.socer = this.score;
            console.log(this.form);
            const res = await this.$http.post("/api/mission/editorMission", this.form);
            response(res, this);
            this.fetchData();
            this.dialogVisible2 = false;
        },
        async fetchData() {
            if(localStorage.roleId == 3) {
                const res = await this.$http.post("/api/mission/findByPage", {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                });
                this.tableData = res.data.data.content;
                this.total = res.data.data.totalSize;
            }
            else if(localStorage.roleId == 2) {
                const res = await this.$http.post("/api/mission/findByPromulgatorId", {
                    id: localStorage.id,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                });
                this.tableData = res.data.data.content;
                this.total = res.data.data.totalSize;
            }
            else {
                 const res = await this.$http.post("/api/mission/findPageByUserId", {
                    id: localStorage.id,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                });
                this.tableData = res.data.data.content;
                this.total = res.data.data.totalSize;
            }
        },    
        async getUserList() {
            const res = await this.$http.post("/api/user/findUserBySelection", {
                id: localStorage.sectionId
            });
            this.userList = res.data.data.map(v => {
                return {
                    name: v.name,
                    id: v.id
                }
            });
        }
    },
    created() {
        this.fetchData();
        this.getUserList();
        this.nowTime = new Date().getFullYear() + "-" + (new Date().getMonth() > 8 ? new Date().getMonth() + 1 : "0" + (new Date().getMonth() + 1)) + "-" + (new Date().getDate() > 10 ? new Date().getDate() : "0" + new Date().getDate())
        console.log(this.nowTime);
    },
    computed: {
        role() {
            return localStorage.roleId === 1;
        }
    }
}
</script>

<style lang="scss" scoped>
.main {
    width: 100%;
    height: 90%;
    position: relative;
    top: 25px;
    .pagination {
        position: relative;
        top: 50px;
        left: 400px;
    }
}
</style>