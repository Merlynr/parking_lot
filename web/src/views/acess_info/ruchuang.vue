<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:46:29
 * @LastEditTime: 2022-03-07 20:32:33
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\acess_info\ruchuang.vue
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
      <el-form-item label="时间段">
        <div class="block">
          <el-date-picker
            v-model="searchForm.time"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['12:00:00']"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
            </el-date-picker>
        </div>
      </el-form-item>
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
        <el-table-column
          type="index"
          width="50"
          label="序号"
        > </el-table-column>
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
                prop="parkingLotId"
                label="车位编码"
                width="100"
              >
                <template slot-scope="scope">
                  <div
                    v-for="(item, index) in allList"
                    :key="index"
                  >
                    <div v-if="item.id == scope.row.parkingLot">
                      {{ allList[index].name }}
                    </div>
  </div>
  </template>
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
      allList: [],
      searchForm: {
        time: [],
        license_plates: ""
      },
      tableData: [],
      pageNum: 1,
      total: 100,
      pageSize: 5
    };
  },
  created() {
    this.getTableData();
  },
  methods: {
    async search() {
      const res = await this.$http.get("/api/parking/searchByTimes", {
        params: {
          startTime: this.searchForm.time[0],
          endTime: this.searchForm.time[1],
          licensePlates: this.searchForm.license_plates
        }
      });

      this.tableData = this.isRu(res.data.data.data);
      this.total = this.isRu(res.data.data.data).length;
    },
    async getTableData() {
      let chewei = await this.$http.get("/api/yuezhu/list");
      this.allList = chewei.data.data;
      const res = await this.$http.post("/api/parking/findByPage", {
        pageNum: this.pageNum,
        pageSize: this.pageSize+5
      });
      this.tableData = await this.isRu(res.data.data.content);
      this.total = await this.isRu(res.data.data.content).length;
    },
    sizeChange(size) {
      this.pageSize = size;
      this.getTableData();
    },
    currentChange(page) {
      this.pageNum = page;
      this.getTableData();
    },
    handleEdit(index, row) {
      window.localStorage.setItem("carInfo", JSON.stringify(row));
      this.$router.push("/img");
    },
    isRu(list) {
      let newList = [];
      list.forEach(row => {
        if (row.startTime != null && row.endTime == null) {
          newList.push(row);
        }
      });
      return newList;
    }
  }
};
</script>
