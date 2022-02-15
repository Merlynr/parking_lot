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
    parkingRecord: ""
  },
  submit: function submit() {
    var that = this;
    var str = this.data.one + this.data.two + this.data.three + this.data.four + this.data.five + this.data.six + this.data.seven;
    if (str.length == 7) {
      wx.request({
        url: "http://127.0.0.1:8848/api/TODO",
        success: function success(res) {
          wx.showToast({
            title: "成功",
            icon: "success",
            duration: 1000,
            mask: true
          });
          that.setData({
            parkingRecord: res.data
          });
        }
      });
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