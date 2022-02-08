<template>
  <div class="home">
    <div class="name">
      <p>停车场计费系统</p>
    </div>
    <div class="main">
      <div class="hint">
        <p>{{hint}}</p>
      </div>
      <div class="login">
        <el-form v-model="form" ref="form" label-width="100px" size="large">
          <el-form-item label="姓名" v-show="isRegister">
            <el-input v-model="form.name" required></el-input>
          </el-form-item>
          <el-form-item label="账号">
            <el-input v-model="form.userName" required></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.pwd" type="password" required></el-input>
          </el-form-item>
          <el-form-item label="确认密码" v-show="isRegister">
            <el-input v-model="form.password" type="password" required></el-input>
          </el-form-item>
          <div class="hide" v-if="!isRegister"></div>
          <el-form-item size="large" class="opration">
            <el-button type="primary" @click="passwordLogin" v-show="!isRegister">登录</el-button>
            <el-button type="primary" @click="register" v-show="isRegister">立即注册</el-button>
            <el-button type="primary" @click="toRegister" v-show="!isRegister">注册</el-button>
            <el-button type="primary" @click="cancel" v-show="isRegister">返回</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div> 
  </div>
</template>

<script>
import  response from "../plugins/http.js"
export default {
  data() {
        return {
            asideHeight: {
                height: "500px",
            },
            form: {
              userName: "",
              pwd: ""
            },
            isRegister: false,
        }
    },
  async created() {
      this.asideHeight.height = window.innerHeight - 50 + "px";
      if(localStorage.getItem("token")) {
        this.$router.push("/");
      }
  },
  methods: {
    async passwordLogin() {
      if(this.form.userName.length == 0) {
        this.$message({
          type: "error",
          message: "账号不能为空"
        });
      }
      else if(this.form.pwd.length == 0) {
        this.$message({
          type: "error",
          message: "密码不能为空"
        });
      }
      else {
        delete this.form.password;
        delete this.form.name;
        const res = await this.$http.post("/api/user/login", this.form);
        response(res,this);
        if(res.data.msg === "success") {
          console.log(res.data.data.user);
          localStorage.token = res.data.data.token;
          localStorage.name = res.data.data.user.name;
          localStorage.roleId = res.data.data.user.roleId;
          localStorage.id = res.data.data.user.id;
          localStorage.sectionId = res.data.data.user.section ? res.data.data.user.section.id : 0;
        }
        this.$router.push("/")
      }
    },
    async register() {
      if(this.form.name.length == 0) {
        this.$message({
          type: "error",
          message: "姓名不能为空"
        });
      }
      else if(this.form.userName.length == 0) {
        this.$message({
          type: "error",
          message: "账号不能为空"
        });
      }
      else if(this.form.pwd != this.form.password){
        this.$message({
          type: "error",
          message: "确认密码不一致"
        });
      }
      else if(this.form.pwd.length == 0) {
        this.$message({
          type: "error",
          message: "密码不能为空"
        });
      }
      else {
        const form = {name:this.form.name, roleId:1, username: this.form.userName, password: this.form.pwd}
        const res = await this.$http.post("/api/user/register", form);
        response(res,this);
        console.log(res.data);
        if(res.data.msg === "success") {
          this.passwordLogin();
        }
      }
    },
    toRegister() {
      this.isRegister = true;
      this.form = {};
    },
    cancel() {
      this.isRegister = false;
      this.form = {};
    },
  },
  computed: {
    hint() {
      return this.isRegister ? "注册账号" : "账号登录"
    }
  },
}

</script>

<style lang="scss" scoped>
.home {
  width: 100%;
  height: 100vh;
  background: url("../assets/background.png") no-repeat 0 0;
  background-size: 100% 100%;
  display: flex;
  .name {
    width: 100%;
    height: 30%;
    position: absolute;
    display: flex;
    p {
      font-size: 50px;
      color: rgb(18,150,124);
      margin: auto;
    }
  }
  .main {
    width: 25%;
    height: 35%;
    min-width: 470px;
    min-height: 400px;
    background-color: lightblue;
    margin: auto;
    border-radius: 10px;
    .hint {
      width: 100%;
      height: 20%;
      display: flex;
      p {
        margin: auto;
        font-size: 24px;
      }
    }
    .login {
      width: 100%;
      height: 50%;
      .hide {
        min-height: 50px;
      }
      .opration {
        position: relative;
        left: 10%;
      }
    }
  }
  .el-input {
    max-width: 300px;
  }
  .el-form-item__label {
    font-size: 20px !important;
  }
}
</style>
