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
    seven: ""
  },
  submit: function submit() {
    var str = this.data.one + this.data.two + this.data.three + this.data.four + this.data.five + this.data.six + this.data.seven;
    if (str.length == 7) {
      wx.showToast({
        title: '成功',
        icon: 'succes',
        duration: 1000,
        mask: true
      });
    } else {
      wx.showToast({
        title: '未填写完整',
        icon: 'error',
        duration: 1000,
        mask: true
      });
    }
  }
});