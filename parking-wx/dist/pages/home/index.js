"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = Page({
  data: {
    one: "",
    two: "",
    three: "",
    four: "",
    five: "",
    six: "",
    seven: "",
    user: ""
  },
  submit: function submit() {
    var that = this;
    var str = this.data.one + this.data.two + this.data.three + this.data.four + this.data.five + this.data.six + this.data.seven;
    if (str.length == 7) {
      wx.request({
        method: "GET",
        url: "http://127.0.0.1:8848/api/user/findByLicense",
        header: {
          token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
        },
        data: {
          license_plates: str
        },
        success: function success(res) {
          wx.showToast({
            title: "成功",
            icon: "success",
            duration: 1000,
            mask: true
          });
          that.setData({
            user: res.data
          });
        }
      });
      console.log(this.data.user);
    } else {
      wx.showToast({
        title: "未填写完整",
        icon: "error",
        duration: 1000,
        mask: true
      });
    }
  }
});