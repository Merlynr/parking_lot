"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = Page({
  data: {
    queryBean: {}
  },
  onLoad: function onLoad(options) {
    var that = this;
    var queryBean = JSON.parse(options.queryBean);
    that.setData({
      queryBean: queryBean
    });
  },
  submit: function submit() {
    var that = this;
    console.log("SSS");
    wx.request({
      method: "POST",
      url: "http://127.0.0.1:8848/api/parking/update",
      header: {
        token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
      },
      data: {
        id: that.data.queryBean.id,
        money: that.data.queryBean.money
      }
    });
  }
});