<!--
 * @Author: Merlynr
 * @Date: 2022-02-12 17:16:16
 * @LastEditTime: 2022-02-13 22:53:45
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\paizhao.vue
 * 少年强，中国强！
-->
<template>
  <div>
    <input type="file" id="inputfile" @change="imgbase()" />
    <el-button icon="el-icon-search" circle @click="paizhao"></el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      license_img: "",
      tParking: [],
    };
  },
  created() {
    Date.prototype.Format = function (fmt) {
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
    };
    this.searchUnusedTempParking();
  },
  methods: {
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
          this.license_img = eve.target.result;
        };
      }
    },
    async paizhao() {
      let img = "image=" + encodeURIComponent(this.license_img.slice(23));
      const res = await this.$httpX.post("", img);
      let license = res.data.words_result.number;
      this.searchFromLicense(license);
    },
    async searchFromLicense(license) {
      const res = await this.$http.get("/api/user/findByLicense", {
        params: {
          license_plates: license,
        },
      });
      let user = res.data.data;
      // 查询是否进入
      const resIn = await this.$http.get("/api/parking/searchByLicense", {
        params: {
          licensePlates: license,
        },
      });
      let parkingRecord = resIn.data.data;
      if (user == null) {
        // parkingRecord.sort(function (a, b) {
        //   return (
        //     Date.parse(b.startTime.replace(/-/g, "/")) -
        //     Date.parse(a.startTime.replace(/-/g, "/"))
        //   );
        // });
        console.log(parkingRecord);
        console.log("临时用户的进出z");
        // if (parkingRecord == null) {
        const res1 = this.$http.post("/api/parking/add", {
          license: license,
          user: res.data.name,
          // TODO 时区存在问题
          startTime: new Date('June 15, 2019 23:15:30 GMT+10:00'),
          parkingLot: this.tParking[0].id,
          carIn: this.license_img,
        });
        this.zhanyong(this.tParking[0].id,1)
        console.log(res1);
        // } else {
        //   console.log("出");
        // }
      } else {
        // 月租用户的进出
        console.log("月租用户的进出");
      }
    },
    async searchUnusedTempParking() {
      let res = await this.$http.get("/api/yuezhu/list");
      let lisx = res.data.data;
      let indexs = [];
      lisx.forEach((arr) => {
        if (arr.type == "2" && arr.used == "0") {
          indexs.push(arr);
        }
      });
      this.tParking = indexs;
      if (indexs.length === 0) {
        this.$message({
          type: "error",
          message: "无空余车位",
        });
      }
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
