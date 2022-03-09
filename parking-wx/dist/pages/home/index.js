"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

var regenerator = require("../../utils/runtime/runtime.js");
exports.default = Page({
  data: {
    one: "",
    two: "",
    three: "",
    four: "",
    five: "",
    six: "",
    seven: "",
    user: "",
    parkingRecords: []
  },
  submit: function () {
    var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
      var _this = this;

      var that, str;
      return regeneratorRuntime.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              that = this;
              str = (this.data.one + this.data.two + this.data.three + this.data.four + this.data.five + this.data.six + this.data.seven).toString();

              if (!(str.length == 7)) {
                _context.next = 10;
                break;
              }

              _context.next = 5;
              return this.searchUser(str);

            case 5:
              _context.next = 7;
              return this.searchRecord(str);

            case 7:
              setTimeout(function () {
                _this.panduan();
                wx.navigateTo({
                  url: '../money/money'
                });
              }, 300);
              _context.next = 11;
              break;

            case 10:
              wx.showToast({
                title: "未填写完整",
                icon: "error",
                duration: 1000,
                mask: true
              });

            case 11:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, this);
    }));

    function submit() {
      return _ref.apply(this, arguments);
    }

    return submit;
  }(),
  panduan: function panduan() {
    var that = this;
    console.log(that.data.user);
    console.log(this.data.parkingRecords);
    if (that.data.parkingRecords == null || that.data.parkingRecords.length == 0) {
      wx.showToast({
        title: "该车辆无停车记录",
        icon: "success",
        duration: 1000,
        mask: true
      });
    } else {
      if (that.data.user == "" || that.data.user == null) {
        // 临时用户出
        console.log("临时用户");
        this.yuezhuCarOut(20);
      } else {
        // 月租用户出
        console.log("月租用户");
        this.yuezhuCarOut();
      }
    }
  },
  searchUser: function searchUser(license_plates) {
    var that = this;
    wx.request({
      method: "GET",
      url: "http://127.0.0.1:8848/api/user/findByLicense",
      header: {
        token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
      },
      data: {
        license_plates: license_plates
      },
      success: function success(res) {
        that.setData({
          user: res.data.data
        });
      }
    });
  },
  searchRecord: function searchRecord(license) {
    var that = this;
    wx.request({
      method: "GET",
      url: "http://127.0.0.1:8848/api/parking/searchByLicense",
      header: {
        token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
      },
      data: {
        licensePlates: license
      },
      success: function success(res) {
        that.setData({
          parkingRecords: res.data.data
        });
      }
    });
  },
  linshiCarOut: function linshiCarOut(lincense) {
    // 获取计费算法，并进行计算
  },
  yuezhuCarOut: function yuezhuCarOut(money) {
    var that = this;
    var Smoney = money;
    var mId = that.data.parkingRecords.length;
    if (that.data.parkingRecords[mId - 1].endTime == null || that.data.parkingRecords[mId - 1].endTime == "") {
      wx.request({
        method: "POST",
        url: "http://127.0.0.1:8848/api/parking/update",
        header: {
          token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
        },
        data: {
          id: that.data.parkingRecords[mId - 1].id,
          endTime: new Date(),
          money: Smoney
        }
      });
    } else {
      wx.showToast({
        title: "该车辆无停车记录",
        icon: "success",
        duration: 1000,
        mask: true
      });
    }
  }
});