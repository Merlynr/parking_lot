<template>
    <div class="main">
    <el-button @click="dialogVisible = true; isEdit = false;" v-if="!role">新增考核等级</el-button>
    <el-table
        :data="tableData"
        style="width: 100%"
        :default-sort="{ prop: 'date', order: 'descending' }"
        :highlight-current-row = "true">
        <el-table-column
        type="index"
        width="50">
        </el-table-column>
        <el-table-column prop="ssocer" label="开始分数">
        </el-table-column>
        <el-table-column prop="esocer" label="结束分数">
        </el-table-column>
        <el-table-column prop="yearDegree" label="级别描述">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template slot-scope="scope" v-if="!scope.row.socer">
            <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="dialogVisible1 = true; removeId = scope.row.id" type="text" size="small">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog
      :title="isEdit ? '编辑考核等级' : '新增考核等级'"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form v-model="form" label-width="80px">
        <el-form-item label="开始分数">
            <el-input v-model="form.ssocer" placeholder="请输入开始分数"></el-input>
        </el-form-item>
        <el-form-item label="结束分数">
            <el-input v-model="form.esocer" placeholder="请输入结束分数"></el-input>
        </el-form-item>
        <el-form-item label="级别描述">
            <el-input v-model="form.yearDegree" placeholder="请输入级别描述"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button @click="dialogVisible = false; form = {}; isEdit = false">取 消</el-button>
            <el-button type="primary" @click="add()">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      title="确认删除"
      :visible.sync="dialogVisible1"
      width="30%">
      <span>确认删除该等级？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="remove">确 定</el-button>
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
            dialogVisible: false,
            dialogVisible1: false,
            isEdit: false,
            removeId: 0,
            form: {},
        }
    },
    methods: {
        async remove() {
            const res = await this.$http.post("/api/yeard/delYeard", {
                id: this.removeId
            });
            response(res, this);
            this.dialogVisible1 = false;
            this.fetchData();
        },
        edit(row) {
            this.isEdit = true;
            this.dialogVisible = true;
            this.form = row;
        },
        async add() {
            if(this.isEdit) {
                const res = await this.$http.post("/api/yeard/updateYeard", this.form);
                response(res, this);
                this.fetchData();
                this.dialogVisible = false;
            }
            else {
                if(!this.form.ssocer || !this.form.esocer || !this.form.yearDegree) {
                    this.$message({
                        type: "warning",
                        message: "存在必填项未填"
                    })
                }
                else {
                    const res = await this.$http.post("/api/yeard/setNew", this.form)
                    response(res, this);
                    this.fetchData();
                    this.dialogVisible = false;
                }
            }
        },
        async fetchData() {
            const res = await this.$http.get("/api/yeard/getAll");
            this.tableData = res.data.data;
        },    
    },
    created() {
        this.fetchData();
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