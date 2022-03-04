<!--
 * @Author: Merlynr
 * @Date: 2022-03-03 22:46:04
 * @LastEditTime: 2022-03-04 10:15:16
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\record.vue
 * 少年强，中国强！
-->
<template>
    <div class="container">
        <div style="width:40%;margin:0px auto">
            <el-card :body-style="{ padding: '0px' }">
                <el-image
                    class="image"
                    :src="carInfo.carIn"
                    :preview-src-list="imgs"
                >
                    </el-image>
                    <div style="padding: 14px;">
                        <div class="bottom clearfix">
                           车牌：{{carInfo.license}}<br/><br/><br/>
                           用户：{{carInfo.user?carInfo.user:'临时用户'}}<br/><br/><br/>
                           进场时间：{{carInfo.startTime}}<br/><br/><br/>
                          <span v-show="carInfo.endTime!=null" >
                           出场时间：{{carInfo.endTime}}<br/><br/><br/>
                           车费：{{carInfo.user?'月租用户':carInfo.money}}
                          </span><br/><br/><br/>
                        </div>
                    </div>
            </el-card>
        </div>
    </div>
</template>
<script>
export default {
  data() {
    return {
      imgs: [],
      carInfo:{}
    };
  },
  created() {
    this.getRecord();
  },
  methods: {
    getRecord() {
      let carInfo = JSON.parse(window.localStorage.getItem("carInfo"));
      this.carInfo = carInfo;
    }
  }
};
</script>
<style>
.container {
  /* text-align: center; */
}
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>


