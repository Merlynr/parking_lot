<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:46:21
 * @LastEditTime: 2022-03-03 22:42:38
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\acess_info\chuchang.vue
 * 少年强，中国强！
-->
<template>
  <div>
    <el-form
      style="position: absolute; right: 20px; top: 75px"
      size="mini"
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
    >
      <!-- <el-form-item label="联系方式">
        <el-input
          v-model="searchForm.tel"
          placeholder="电话号码"
        ></el-input>
      </el-form-item> -->
      <el-form-item label="车牌">
        <el-input v-model="searchForm.license_plates"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="search"
        >查询</el-button>
      </el-form-item>
      </el-form>
      <el-table
        :data="tableData"
        style="width: 100%; position: absolute; top: 140px"
        height="450"
      >
      <el-table-column type="index" width="50" label="序号"> </el-table-column>
        <el-table-column
          prop="license"
          label="车牌"
          width="260"
        > </el-table-column>
          <el-table-column
          type="time"
            prop="startTime"
            label="入场时间"
            width="300"
          >
    
            </el-table-column>
            <el-table-column
              prop="endTime"
              label="出场时间"
              width="360"
            >
              </el-table-column>
              <el-table-column
                prop="money"
                label="费用"
                width="200"
              >
               
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      @click="handleEdit(scope.$index, scope.row)"
                    >查看</el-button>
                    
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
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchForm: {
        tel: "",
        license_plates: ""
      },
      tableData: [],
      pageNum: 1,
      total: 100,
      pageSize: 5
    };
  },
  created() {
    this.getTableData()
  },
  methods: {
    search(){
      const res= this.$http.get("/api/parking/searchByLicense",{
        params: {
          licensePlates:this.searchForm.license_plates
        }
      });
      this.tableData = this.isChu(res.data.data.content);
      this.total = this.isChu(res.data.data.content).length;
    },
    async getTableData() {
      const res = await this.$http.post("/api/parking/findByPage", {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      });
      this.tableData = await this.isChu(res.data.data.content);
      this.total = await this.isChu(res.data.data.content).length;
    },
    sizeChange(size) {
      this.pageSize = size;
      this.getTableData();
    },
    currentChange(page) {
      this.pageNum = page;
      this.getTableData();
    },
    handleEdit(row,value) {
      console.log(row,value);
    },
    isChu(list) {
      let newList = [];
      list.forEach(row => {
        if (row.startTime != null && row.endTime != null) {
          newList.push(row);
        }
      });
      return newList;
    }
  }
};
</script>
