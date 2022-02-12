export default (res = {}, _this) => {
    if(res.data.msg === "success") {
        _this.$message({
            type: "success",
            message: "操作成功"
        });    
    }
    else {
        _this.$message({
            type: "error",
            message: res.data.msg
        });
        console.log(res.data.msg);
        // throw new Error(res.data.msg);
    }
}