<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:48:23
 * @LastEditTime: 2022-03-09 10:24:35
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\jiaofei\linshifeiyong.vue
 * 少年强，中国强！
-->
<template>
  <div>
    <el-card
      class="box-card"
      v-loading="loading"
    >
      <div
        slot="header"
        class="clearfix"
      >
        <span>计费规则</span>
        <el-button
          type="text"
          style="float: right; padding: 3px 0"
          @click="dialogVisible = true"
        >修改临时计费</el-button>
  </div>
  <span style="font-size:12px">临时车辆缴费规则：第一个小时
    <span style="color:red">{{moneyList[1].money}}</span>元，以后每个小时
    <span style="color:red">{{moneyList[2].money}}</span>元，全天封顶
    <span style="color:red">{{moneyList[3].money}}</span>元。</span>
  </el-card>
  <el-dialog
    title="临时车辆费用设置"
    :visible.sync="dialogVisible"
    width="30%"
  >
    <span>第一个小时：</span>
    <el-input
      style="width:80px"
      v-model="first"
      placeholder="请输入"
    ></el-input>
      <span>，以后每个小时：</span>
      <el-input
        style="width:80px"
        v-model="second"
        placeholder="请输入"
      ></el-input>
        <div>
          <span><br/>全天：</span>
          <el-input
            style="width:80px"
            v-model="all"
            placeholder="请输入"
          ></el-input>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="dialogVisible = false,
                submit()"
          >确 定</el-button>
            </span>
            </el-dialog>
            </div>
</template>
<script>
export default {
  data() {
    return {
      loading: true,
      dialogVisible: false,
      moneyList: [
        {
          money: ""
        }
      ],
      first: "",
      second: "",
      all: ""
    };
  },
  created() {
    this.getList();
  },
  methods: {
    submit() {
      this.update1();
      this.update2();
      this.update3();
      this.getList();
    },
    async getList() {
      let res = await this.$http.get("/api/money/list");
      this.moneyList = res.data.data;
      this.loading = false;
    },
    async update1() {
      await this.$http.post("/api/money/update", {
        id: 2,
        money: this.first
      });
    },
    async update2() {
      await this.$http.post("/api/money/update", {
        id: 3,
        money: this.second
      });
    },
    async update3() {
      await this.$http.post("/api/money/update", {
        id: 4,
        money: this.all
      });
    }
  }
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}
</style>