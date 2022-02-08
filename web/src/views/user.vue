<template>
    <div class="main">
    <!-- <el-button @click="dialogVisible1 = true">添加人员</el-button> -->
    <el-table
      :data="tableData"
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :highlight-current-row = "true">
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="role.role" label="人员职位">
      </el-table-column>
      <el-table-column prop="section.name" label="所属部门">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="showRemove(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="sizes, prev, pager, next" :page-size="pageSize" :page-sizes="[5, 10, 20, 50]" :current-page="pageNum" :total="total2" class="pagination" 
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
      title="编辑人员"
      :visible.sync="dialogVisible1"
      width="30%">
      <el-form v-model="form" label-width="80px">
        <el-form-item label="姓名">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="人员职位">
            <el-select v-model="form.roleId" placeholder="请选择职位">
                <el-option v-for="(item, index) in role" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="所属部门">
            <el-select v-model="form.sectionId" placeholder="请选择部门">
                <el-option v-for="(item, index) in depart" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button @click="dialogVisible1 = false; isEdit = false; form = {}">取 消</el-button>
            <el-button type="primary" @click="add()">确 定</el-button>
        </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import  response from "../plugins/http.js"
export default {
    data() {
        return {
            tableData: [],
            role: [{
                name: "普通员工",
                id: 1
            },{
                name: "部门经理",
                id: 2,
            }],
            pageNum: 1,
            total2: 100,
            pageSize: 5,
            dialogVisible: false,
            dialogVisible1: false,
            form: {},
            depart: [],
            isEdit: false,
            removeId: 0,
        }
    },
    methods: {
        async remove() {
            const res = await this.$http.post("/api/user/deleteUser", {
                id: this.removeId
            });
            this.dialogVisible = false;
            response(res, this);
            this.fetchData();
        },
        showRemove(row) {
            this.dialogVisible = true; 
            this.removeId = row.id;
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
            this.dialogVisible1 = true;
            this.isEdit = true;
            this.form = row;
            this.row_id = row.id;
        },
        async fetchData() {
            const res = await this.$http.post("/api/user/findUserByPage", {
                pageNum: this.pageNum,
                pageSize: this.pageSize
            });
            this.tableData = res.data.data.content;
            this.total2 = res.data.data.totalSize;
        },
        async add() {
            if(this.isEdit) {
                const res = await this.$http.post("/api/user/updateUserInfo", this.form);
                response(res, this);
            }
            this.fetchData();
            this.dialogVisible1 = false;
            this.isEdit = false;
        },
        async getDepart() {
            const res = await this.$http.get("/api/section/list");
            this.depart = res.data.data;
        }
    },
    created() {
        this.fetchData();
        this.getDepart();
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
    .el-input {
        width: 300px;
    }
    .el-select {
        width: 300px;
    }
}
</style>