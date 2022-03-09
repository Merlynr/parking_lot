<template>
  <div class="home">
    <el-container class="main">
        <el-header height="50px" class="header">
          <span class="name">欢迎来到停车场计费系统</span>
            <el-container>
                <el-header class="setting">
                    <el-dropdown @command="show">
                        <i class="el-icon-setting"></i>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="goback">注销</el-dropdown-item>
                            <!-- <el-dropdown-item command="duty">签到</el-dropdown-item> -->
                        </el-dropdown-menu>
                    </el-dropdown>
                    <span>欢迎,&nbsp;{{name}}</span>
                </el-header>
            </el-container>
        </el-header>
        <el-container>
          <el-aside width="220px" :style="asideHeight">
              <el-menu router :default-active="$route.path"
              background-color="#545c64"
              text-color="rgb(186,194,198)"
              active-text-color="rgb(255,255,255)" :unique-opened="true">
                  <el-submenu index="1">
                      <template slot="title"><i class="el-icon-money"></i>车辆管理</template>
                          <el-menu-item index="/yuezhu">月租车辆信息</el-menu-item>
                          <el-menu-item index="/chewei">车位管理</el-menu-item>
                  </el-submenu>
                  <el-submenu index="2">
                      <template slot="title"><i class="el-icon-setting"></i>通行信息</template>
                          <el-menu-item index="/churu">出场记录</el-menu-item>
                          <el-menu-item index="/jinru">入场记录</el-menu-item>
                  </el-submenu>
                  <el-submenu index="3">
                      <template slot="title"><i class="el-icon-document"></i>缴费信息</template>
                          <el-menu-item index="/jiaofeirecord">缴费记录</el-menu-item>
                          <el-menu-item index="/yuezhujiaofeishezhi">月租车费用设置</el-menu-item>
                          <el-menu-item index="/linshijiaofeishezhi">临时费用设置</el-menu-item>
                  </el-submenu>
                  <el-submenu index="4">
                      <template slot="title"><i class="el-icon-setting"></i>统计报表</template>
                          <el-menu-item index="/user">收入报表</el-menu-item>
                  </el-submenu>
                  <el-menu-item index="/record">传入摄像图片</el-menu-item>
              </el-menu>
          </el-aside>
          <el-main class="content">
              <router-view></router-view>
              <!-- <el-image
              style="position:absolute;top:50px;left:215px;z-index:-20"
      :src="url"
      :fit='cover'></el-image> -->
          </el-main>
        </el-container>
         <el-dialog
          title="确认注销"
          :visible.sync="dialogVisible"
          width="30%">
          <span>确认注销当前账号？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="login()">确 定</el-button>
          </span>
        </el-dialog>
    </el-container>
  </div>
</template>

<script>

export default {
  data() {
        return {
          
            asideHeight: {
                height: "500px",
            },
            name: "",
            dialogVisible: false,
        }
    },
  created() {
      this.asideHeight.height = window.innerHeight - 50 + "px";
      this.name = localStorage.name;
      if(!localStorage.getItem("token")) {
        this.$router.push("/login");
      }
  },
  methods: {
    login() {
      localStorage.removeItem("token");
      localStorage.removeItem("name");
      localStorage.removeItem("role_id");
      this.$router.push("/login");
      this.$message({
        type: "success",
        message: "注销成功"
      })
    },
    show(command) {
      if(command == "goback") {
        this.dialogVisible = true;
      }
      else {
        const res = this.$http.post("/api/sign/doSign",{
          id: localStorage.getItem("id")
        })
        console.log(res.data.username);
      }
    }
  },
  computed: {
    isDepartManager() {
      return localStorage.roleId != 1 ;
    },
    isBoos() {
      return localStorage.roleId == 3 ;
    }
  }
}

</script>

<style lang="scss" scoped>
.main {
    width: 100%;
    height: 95vh;
    min-width: 960px;
    .header {
        background-color: rgb(35,38,46);
        color: rgb(186,187,189);
    }
    .name {
        position: absolute;
        top: 15px;
        left: 70px;
        color: rgb(18,150,124);
    }
    .setting {
        position: relative;
        text-align: right;
        top: 15px;
    }
    .el-aside {
        background-color: #545c64 !important;
        .el-menu {
            height: 100%;
        }
    }
    .content {
      overflow: hidden;
    }
}
</style>
