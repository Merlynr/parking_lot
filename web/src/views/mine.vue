<template>
    <div class="main">
      <el-form v-model="form" class="info" label-width="80px">
        <el-form-item label="姓名">
            <el-input v-model="form.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="个人职务">
            <el-input v-model="form.degree" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属部门">
            <el-input v-model="form.depart" disabled></el-input>
        </el-form-item>
        <el-form-item label="完成率">
            <el-input v-model="form.finish" disabled></el-input>
        </el-form-item>
        <el-form-item label="平均效绩">
            <el-input v-model="form.performance" disabled></el-input>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
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
            const res = await this.$http.get("data",{
                params: {
                    page: this.page,
                    size: this.size
                }
            });
            this.tableData = res.data.data;
            this.total = res.data.total;
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