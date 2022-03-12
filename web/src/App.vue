<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import  response from "./plugins/http.js"
export default {
  data() {
    return {

    }
  },
  async mounted() {
    if(localStorage.getItem("token")) {
      const res = await this.$http.post("/api/user/login", {userName: "admin",pwd:"123456"}, {
        headers: {
          token: localStorage.getItem("token")
        }
      });
      response(res, this);
      this.$router.push("/");
    }
    else {
      this.$router.push("/login");
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
