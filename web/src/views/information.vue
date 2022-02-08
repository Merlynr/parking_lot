<template>
    <div class="main">
      <el-form v-model="form" class="info" label-width="80px">
        <el-form-item label="姓名">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="生日">
            <el-input v-model="form.birthday"></el-input>
        </el-form-item>
        <el-form-item label="住址">
            <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
            <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" v-if="form.section">
            <el-input v-model="form.section.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
            <el-input v-model="form.password1" type="password"></el-input>
        </el-form-item>
        <el-form-item size="large" class="opration">
            <el-button type="primary" @click="change">修改资料</el-button>
          </el-form-item>
      </el-form>
  </div>
</template>

<script>
import  response from "../plugins/http.js"
export default {
    data() {
        return {
            nameList: [
                {
                    name: "张三",
                    value: "1",
                },
                {
                    name: "李四",
                    value: "2",
                }
            ],
            page: 1,
            total: 100,
            size: 5,
            dialogVisible: false,
            dialogVisible1: false,
            form: {
                name: "张三",
                degree: "研发部经理",
                depart: "研发部",
                finish: "90%",
                performance: 90,
            }
        }
    },
    methods: {
        check(row) {
            localStorage.setItem("_id", row._id);
            this.$router.push({
                name: "detail",
                params: {
                    _id: row._id
                }
            })
        },
        async remove() {
            const res = await this.$http.post("data/delete",{
                _id: this.removeId
            })
            console.log(res.data);
            this.fetchData();
            this.dialogVisible = false;
            this.$message({
                type: "success",
                message: "删除成功!"
            })
        },
        currentChange(page) {
            this.page = page;
            this.fetchData();
        },
        sizeChange(size) {
            this.size = size;
            this.fetchData();
        },
        async fetchData() {
            const res = await this.$http.get("/api/user/findUserById", {
                params: {
                    id: localStorage.id
                }
            });
            this.form = res.data.data;
            console.log(this.form);
            this.form.password1 = this.form.password;
        },
        async change() {
            if(this.form.password !== this.form.password1) {
                this.$message({
                    type: "error",
                    message: "确认密码不一致"
                })
            }
            else if(this.form.password.length == 0) {
                this.$message({
                    type: "error",
                    message: "密码不能为空"
                })
            }
            else if(this.form.name.length == 0) {
                this.$message({
                    type: "error",
                    message: "姓名不能为空"
                })
            }
            else {
                const res = await this.$http.post("/api/user/updateUserInfo", this.form);
                response(res, this);
            }
        }
    },
    created() {
        this.fetchData();
    }
}
</script>

<style lang="scss" scoped>
.main {
    width: 100%;
    height: 90%;
    position: relative;
    top: 25px;
    display: flex;
    .info {
        width: 50%;
        height: 80%;
        margin: auto;
    }
}
</style>