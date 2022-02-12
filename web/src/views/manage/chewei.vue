<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:46:00
 * @LastEditTime: 2022-02-12 16:15:16
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\manage\chewei.vue
 * 少年强，中国强！
-->
<template>
  <div class="main">
    <el-form
      stripe
      style="position: absolute; right: 20px; top: 75px"
      size="mini"
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
    >
      <el-form-item label="车位编号">
        <el-input v-model="searchForm.name"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="searchForm.type" placeholder="请选择">
          <el-option
            @change="changeSelect"
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="tableData"
      style="width: 100%; position: absolute; top: 140px"
      height="450"
    >
      <el-table-column prop="name" label="车牌" width="260"> </el-table-column>
      <el-table-column prop="type" label="类型" width="200">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == '1'" type="success">月租</el-tag>
          <el-tag v-else type="info">临时</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="创建时间" width="260">
      </el-table-column>
      <el-table-column prop="used" label="状态" width="200">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.used == '1'" type="success">已占用</el-tag>
          <el-tag v-else type="info">空闲</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      style="position: absolute; bottom: 50px"
      layout="sizes, prev, pager, next"
      :page-size="pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :current-page="pageNum"
      :total="total"
      class="pagination"
      @size-change="sizeChange"
      @current-change="currentChange"
    ></el-pagination>
    <el-dialog title="车位管理" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="车位号">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="车位类型">
          <el-select
            @change="changeSelect"
            v-model="form.type"
            placeholder="请选择活动区域"
          >
            <el-option label="月租车位" value="1"></el-option>
            <el-option label="临时车位" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车位状态">
          <el-select
            @change="changeSelect"
            v-model="form.used"
            placeholder="请选择状态"
          >
            <el-option label="已占用" value="1"></el-option>
            <el-option label="空闲" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = false;
            submit();
          "
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        id: "",
        name: "",
        type: "",
        used: "",
      },
      dialogFormVisible: false,
      searchForm: {
        name: "",
        type: "",
      },
      options: [
        {
          label: "月租车位",
          value: "1",
        },
        {
          label: "临时车位",
          value: "2",
        },
      ],
      tableData: [],
      pageNum: 1,
      total: 100,
      pageSize: 5,
    };
  },
  created() {
    (Date.prototype.Format = function (fmt) {
      var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        S: this.getMilliseconds(), // 毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          (this.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    }),
      this.getTableData();
  },
  methods: {
    changeSelect() {
      this.$forceUpdate();
    },
    submit() {
      this.$http.post("/api/yuezhu/update", this.form);
      this.form = {};
      this.getTableData();
    },
    handleEdit(index, row) {
      this.form.name = row.name;
      this.form.type = row.type;
      this.form.used = row.used;
      this.form.id = row.id;
      this.form.time = row.time;
      this.dialogFormVisible = true;
    },
    handleDelete(index, row) {
      this.$http.post("/api/yuezhu/delete", {
        id: row.id,
      });
      this.getTableData();
    },
    async add() {
      let form = {
        name: this.searchForm.name,
        type: this.searchForm.type,
        time: new Date().Format("yyyy-MM-dd hh:mm:ss"),
        used: "0",
      };
      await this.$http.post("/api/yuezhu/add", form);
      this.searchForm = {};
      this.getTableData();
    },
    async getTableData() {
      const res = await this.$http.post("/api/yuezhu/findByPage", {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      });
      this.tableData = res.data.data.content;
      this.total = res.data.data.totalSize;
    },
    sizeChange(size) {
      this.pageSize = size;
      this.getTableData();
    },
    currentChange(page) {
      this.pageNum = page;
      this.getTableData();
    },
  },
};
</script>

<style>
</style>