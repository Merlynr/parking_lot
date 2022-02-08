<template>
    <div class="main">
    <el-button @click="dialogVisible1 = true">添加部门</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :highlight-current-row = "true">
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
      <el-table-column prop="name" label="部门名称">
      </el-table-column>
      <el-table-column prop="manNum" label="部门人数">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
          <!-- <el-button @click="dialogVisible = true; removeId = scope.row.id" type="text" size="small">删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <!-- <el-pagination background layout="sizes, prev, pager, next" :page-size="size" :page-sizes="[5, 7, 10, 15, 20, 50]" :current-page="page" :total="total" class="pagination" 
    @size-change="sizeChange" @current-change="currentChange"
    ></el-pagination> -->
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
    <el-dialog
      :title="isEdit ? '编辑部门' : '新增部门'"
      :visible.sync="dialogVisible1"
      width="30%">
      <el-form v-model="form" label-width="80px">
        <el-form-item label="部门名称">
            <el-input v-model="form.name"></el-input>
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
            page: 1,
            total: 100,
            size: 5,
            dialogVisible: false,
            dialogVisible1: false,
            form: {},
            userList: [],
            isEdit: false,
            removeId: 0,
        }
    },
    methods: {
        currentChange(page) {
            this.page = page;
            this.fetchData();
        },
        sizeChange(size) {
            this.size = size;
            this.fetchData();
        },
        edit(row) {
            this.isEdit = true;
            this.dialogVisible1 = true;
            this.form = row;
            this.row_id = row.id;
        },
        async fetchData() {
            const res = await this.$http.get("/api/section/list");
            this.tableData = res.data.data;
            this.total = res.data.total;
        },
        async getUser() {
            const res = await this.$http.get("/api/user/list");
            this.userList = res.data.data.map( v => {
                return {
                    name: v.name,
                    id: v.id
                }
            })
        },
        async add() {
            if(this.isEdit) {
                const res = await this.$http.post("/api/section/update", this.form);
                response(res, this);
            }
            else {
                const res = await this.$http.post("/api/section/insert", this.form);
                response(res, this);
            }
            this.fetchData();
            this.dialogVisible1 = false;
            this.isEdit = false;
        }
    },
    created() {
        this.fetchData();
        this.getUser();
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