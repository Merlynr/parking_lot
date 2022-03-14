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
    parkingRecords: [],
    moneyList: [],
    queryBean: {}
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
                _context.next = 13;
                break;
              }

              _context.next = 5;
              return this.searchUser(str);

            case 5:
              _context.next = 7;
              return this.searchRecord(str);

            case 7:
              _context.next = 9;
              return this.getMoneyList();

            case 9:
              setTimeout(function () {
                _this.panduan();
              }, 500);
              setTimeout(function () {
                wx.navigateTo({
                  url: "../money/money?queryBean=" + JSON.stringify(that.data.queryBean)
                });
                that.setData({
                  user: "",
                  parkingRecords: [],
                  queryBean: {},
                  one: "",
                  two: "",
                  three: "",
                  four: "",
                  five: "",
                  six: "",
                  seven: ""
                });
              }, 1500);
              _context.next = 14;
              break;

            case 13:
              wx.showToast({
                title: "未填写完整",
                icon: "error",
                duration: 1000,
                mask: true
              });

            case 14:
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
  getMoneyList: function getMoneyList() {
    var that = this;
    wx.request({
      method: "GET",
      url: "http://127.0.0.1:8848/api/money/list",
      header: {
        token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
      },
      success: function success(res) {
        that.setData({
          moneyList: res.data.data
        });
      }
    });
  },
  panduan: function panduan() {
    var that = this;
    if (that.data.parkingRecords == null || that.data.parkingRecords.length == 0) {
      wx.showToast({
        title: "该车辆无停车记录",
        icon: "success",
        duration: 1000,
        mask: true
      });
    } else {
      var money = "";
      var TEMP = this.data.parkingRecords[that.data.parkingRecords.length - 1];
      var time = this.getTime(this.format(TEMP.startTime), this.format(TEMP.endTime));
      if (time <= 60) {
        money = that.data.moneyList[1].money * time;
      } else if (time > 60 && time <= 1440) {
        money = 60 * that.data.moneyList[1].money + (time - 60) * that.data.moneyList[2].money;
      } else if (time > 1440) {
        money = that.data.moneyList[3].money;
      }
      if (that.data.user == "" || that.data.user == null) {
        // 临时用户出
        console.log("临时用户");
        // setTimeout(() => {
        this.yuezhuCarOut(money, time);
        // }, 300);
      } else {
        // 月租用户出
        console.log("月租用户");
        // setTimeout(() => {
        this.yuezhuCarOut(-1, time);
        // }, 300);
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
  yuezhuCarOut: function yuezhuCarOut(money, time) {
    var that = this;
    var temp = that.data.parkingRecords[that.data.parkingRecords.length - 1];
    that.setData({
      queryBean: {
        id: temp.id,
        license: temp.license,
        startTime: this.format(temp.startTime),
        time: time,
        endTime: this.format(temp.endTime),
        money: money
      }
    });
    // let that = this;
    // let Smoney = money;
    // let mId = that.data.parkingRecords.length;
    // console.log(that.data.parkingRecords);
    // if (
    //   that.data.parkingRecords[mId - 1].endTime == null ||
    //   that.data.parkingRecords[mId - 1].endTime == ""
    // ) {
    //   wx.request({
    //     method: "POST",
    //     url: "http://127.0.0.1:8848/api/parking/update",
    //     header: {
    //       token:
    //         "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.qfd0G-elhE1aGr15LrnYlIZ_3UToaOM5HeMcXrmDGBM"
    //     },
    //     data: {
    //       id: that.data.parkingRecords[mId - 1].id,
    //       money: Smoney
    //     }
    //   });
    // } else {
    //   wx.showToast({
    //     title: "该车辆无停车记录",
    //     icon: "success",
    //     duration: 1000,
    //     mask: true
    //   });
    // }
  },
  getTime: function getTime(day1, day2) {
    var day11 = day1.replace(/-/g, "/");
    var day22 = day2.replace(/-/g, "/");

    var day_day1 = new Date(day11);
    var day_day2 = new Date(day22);

    var disparity = day_day2.getTime() - day_day1.getTime();
    // 转为分钟数的时候，可能会出现精度丢失;你需要注意下
    var min = Math.round(disparity / 1000 / 60);
    // 计算时间
    // this.data.time = MIN
    return min;
  },
  format: function format(date) {
    var dates = new Date(date).toJSON();
    return new Date(+new Date(dates) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
  }
});