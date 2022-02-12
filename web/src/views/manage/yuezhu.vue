<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:45:33
 * @LastEditTime: 2022-02-12 17:05:49
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\manage\yuezhu.vue
 * 少年强，中国强！
-->
<template>
  <div>
    <el-button type="success" plain @click="isOpenEditors = true"
      >新增月租用户</el-button
    >
    <el-form
      style="position: absolute; right: 20px; top: 75px"
      size="mini"
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
    >
      <el-form-item label="联系方式">
        <el-input v-model="searchForm.tel" placeholder="电话号码"></el-input>
      </el-form-item>
      <el-form-item label="车牌">
        <el-input v-model="searchForm.license_plates"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getTableData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 100%" height="450">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column prop="licensePlates" label="车牌" width="160">
      </el-table-column>
      <el-table-column prop="name" label="驾驶者" width="100">
      </el-table-column>
      <el-table-column prop="tel" label="电话号码" width="130">
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="160">
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="160">
      </el-table-column>
      <el-table-column prop="parkingLotId" label="车位编码" width="80">
        <template slot-scope="scope">
          <div v-for="(item, index) in allList" :key="index">
            <div v-if="item.id == scope.row.parkingLotId">
              {{ allList[index].name }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="行驶证" width="180">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 60px"
            :src="scope.row.license"
            :preview-src-list="scope.row.license"
          >
          </el-image>
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
      layout="sizes, prev, pager, next"
      :page-size="pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :current-page="pageNum"
      :total="total"
      class="pagination"
      @size-change="sizeChange"
      @current-change="currentChange"
    ></el-pagination>
    <el-dialog title="月租用户" :visible.sync="isOpenEditors" width="30%">
      <el-form v-model="form" label-width="80px">
        <el-form-item label="车牌">
          <el-input v-model="form.license_plates"></el-input>
        </el-form-item>
        <el-form-item label="驾驶者">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="form.tel"></el-input>
        </el-form-item>
        <el-form-item label="有效时间">
          <el-date-picker
            style="width: 100%"
            v-model="form.timeValue"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车位编码">
          <el-select v-model="selectValue" placeholder="请选择">
            <el-option
              v-for="item in parkingOptions"
              :key="item"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车牌">
          <input type="file" id="inputfile" @change="imgbase()" />
        </el-form-item>
        <el-form-item>
          <el-button
            @click="
              isOpenEditors = false;
              form = {};
              selectValue = {};
            "
            >取 消</el-button
          >
          <el-button
            type="primary"
            @click="
              add();
              isOpenEditors = false;
              selectValue = {};
            "
            >确 定</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allList:[],
      selectValue: 0,
      list: [], //所有车位
      parkingOptions: [],
      isUpdateState: false,
      tableData: [],
      pageNum: 1,
      total: 100,
      pageSize: 5,
      searchForm: {
        tel: "",
        license_plates: "",
      },
      isOpenEditors: false,
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      fileList: [],
      form: {
        id: "",
        license_plates: "",
        name: "",
        tel: "",
        timeValue: "",
        parkingLot: "",
        license_img: "",
      },
    };
  },
  created() {
    this.findByPage();
    this.getUnusedParking();
  },
  methods: {
    async getUnusedParking() {
      let res = await this.$http.get("/api/yuezhu/list");
      let lisx = res.data.data;
      this.allList=lisx
      let indexs = [];
      lisx.forEach((arr) => {
        if (arr.type == "1" && arr.used == "0") {
          indexs.push(arr);
        }
      });
      this.parkingOptions = indexs;
      this.list = indexs;
    },
    async getTableData() {
      const res = await this.$http.get("/api/user/findByTelAndPlates", {
        params: {
          tel: this.searchForm.tel,
          license_plates: this.searchForm.license_plates,
        },
      });
      res.data.data.forEach((arr, i) => {
        if (arr.name == "管理员") {
          res.data.data.splice(i, 1);
        }
      });
      this.tableData = res.data.data;
      this.total = res.data.data.totalSize;
    },
    async findByPage() {
      const res = await this.$http.post("/api/user/findUserByPage", {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      });
      this.tableData = res.data.data.content;
      this.total = res.data.data.totalSize;
    },
    sizeChange(size) {
      this.pageSize = size;
      this.findByPage();
    },
    currentChange(page) {
      this.pageNum = page;
      this.findByPage();
    },
    handleEdit(index, row) {
      this.getUnusedParking();
      this.selectValue = parseInt(row.parkingLotId);
      this.isUpdateState = true;
      this.isOpenEditors = true;
      this.form = row;
      this.form.license_plates = row.licensePlates;
      this.form.license_img = row.license;
      this.form.parkingLot = row.parkingLotId;
      this.isOpenEditors = true;
      this.form.id = row.id;
      this.zhanyong(row.parkingLot,0)
      this.findByPage();
    },
    handleDelete(index, row) {
      this.$http.post("/api/user/deleteUser", {
        id: row.id,
      });
      this.findByPage();
    },
    imgbase() {
      //获取选中图片对象（包含文件的名称、大小、类型等，如file.size）
      var file = document.getElementById("inputfile").files[0];
      // 声明js的文件流
      var reader = new FileReader();
      if (file) {
        //通过文件流将文件转换成Base64字符串
        reader.readAsDataURL(file);
        // 转换完毕;
        reader.onloadend = (eve) => {
          // 把转换过得base赋值给全局变量
          // 这里reader.result就是转换好的base64格式图片
          this.form["license_img"] = eve.target.result;
        };
      }
    },
    async add() {
      let baseForm = {
        id: this.form["id"],
        name: this.form["name"],
        role: "1",
        licensePlates: this.form.license_plates,
        tel: this.form.tel,
        startTime: new Date(this.form.timeValue[0]),
        endTime: new Date(this.form.timeValue[1]),
        parkingLotId: this.selectValue,
        license: this.form.license_img,
      };
      this.zhanyong(this.selectValue,1)
      if (this.isUpdateState) {
        await this.$http.post("/api/user/updateUserInfo", baseForm);
        this.isUpdateState = false;
      } else {
        await this.$http.post("/api/user/register", baseForm);
      }
      this.form = {};
      this.findByPage();
    },
    zhanyong(id, yongmeiyong) {
      // 0没有1有用
      this.$http.post("/api/yuezhu/update", {
        id: id,
        used: yongmeiyong.toString(),
      });
    },
  },
};
</script>

<style>
</style>

