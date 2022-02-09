<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:45:33
 * @LastEditTime: 2022-02-09 17:00:21
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
          <el-input v-model="form.parkingLot"></el-input>
        </el-form-item>
        <el-form-item label="车牌">
          <input type="file" id="inputfile" @change="imgbase()" />
        </el-form-item>
        <el-form-item>
          <el-button
            @click="
              isOpenEditors = false;
              form = {};
            "
            >取 消</el-button
          >
          <el-button
            type="primary"
            @click="
              add();
              isOpenEditors = false;
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
        license_plates: "",
        name: "",
        tel: "",
        timeValue: "",
        parkingLot: "",
        license_img: "",
      },
    };
  },
  methods: {
    imgbase() {
      var license_image = "";
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
          license_image = eve.target.result;
        };
        this.form['license_img'] = JSON.parse(license_image);
      }
    },
    add() {
      console.log(this.form);
    },
  },
};
</script>

<style>
</style>

