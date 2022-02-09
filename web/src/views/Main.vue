<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 20:45:45
 * @LastEditTime: 2022-02-08 13:25:28
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\main.vue
 * 少年强，中国强！
-->
<template>
  <div>
    <el-image
      style="position: absolute; top: 50px; left: 215px; z-index: -20"
      :src="url"
      :fit="fill"
    ></el-image>
  </div>
</template>

<script>
export default {
  data() {
    return {
      url: "https://img.xiaopiu.com/userImages/img1938817e32d292c0.jpeg",
    };
  },
  methods: {},
};
</script>

<style>
</style>