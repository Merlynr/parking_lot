<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:47:48
 * @LastEditTime: 2022-03-07 22:05:55
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\jiaofei\yuezhu.vue
 * 少年强，中国强！
-->

<template>
    <div>
        <el-card class="box-card">
            <div
                slot="header"
                class="clearfix"
            >
                <span>计费规则</span>
                <el-button
                    type="text"
                    style="float: right; padding: 3px 0"
                    @click="open"
                >修改月费</el-button>
    </div>
    <span style="font-size:12px">月租车辆缴费规则：每个月<span style="color:red">{{moneyObj.money}}</span>元</span>
    </el-card>
    </div>
</template>
<script>
export default {
  data() {
    return {
      moneyObj: {
        id: 0,
        type: "",
        money: ""
      }
    };
  },
  created() {
    this.getYueMoney();
  },
  methods: {
    open() {
      this.$prompt("请输入新的月费", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "新的月租费用是: " + value
          });
          this.$http.post("/api/money/update",{
              id:this.moneyObj.id, 
              money: value
          })
          this.moneyObj.money = value
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入"
          });
        });
    },
    async getYueMoney() {
      let res = await this.$http.get("/api/money/searchByType", {
        params: {
          type: "1"
        }
      });
      this.moneyObj = res.data.data;
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