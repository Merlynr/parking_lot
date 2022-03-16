<!--
 * @Author: Merlynr
 * @Date: 2022-02-07 19:48:36
 * @LastEditTime: 2022-03-16 21:48:13
 * @LastEditors: your name
 * @Description: 
 * @FilePath: \web\src\views\tongji\shouru.vue
 * 少年强，中国强！
-->
<template>
    <div>
        <div
            style="display:inline-block;width:600px;height:600px;margin-left:50px"
            id="up"
        >
    </div>
    <div
        style="display:inline-block;width:600px;height:600px;;margin-left:50px"
        id="down"
    >
        </div>
        </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    async drawChart() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("down"));
      // 指定图表的配置项和数据
      let res = await this.$http.get("/api/yuezhu/list");
      let list = res.data.data;
      let num1 = 0;
      let num2 = 0;
      let num3 = 0;
      let num4 = 0;
      list.forEach(data => {
        if (data.type == 1) {
          if (data.used == 0) {
            //   月租未用
            num1++;
          } else {
            num2++;
          }
        } else {
          if (data.used == 0) {
            //   临时未用
            num3++;
          } else {
            num4++;
          }
        }
      });
      let option = {
        legend: {
          top: "bottom"
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            type: "pie",
            radius: [50, 250],
            center: ["50%", "50%"],
            roseType: "area",
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: num4, name: "已占有临时车位" + num4 },
              { value: num3, name: "未占用临时车位" + num3 },
              { value: num2, name: "已占有月租车位" + num2 },
              { value: num1, name: "未占月租时车位" + num1 }
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    async drawUpChart() {
      let res = await this.$http.get("/api/parking/list");
      let list = res.data.data;
      let recordList = [];
      list.forEach(data => {
        if (data.endTime != null) {
          recordList.push(data);
        }
      });
      let fenLei = [
        {
          month: 1,
          data: []
        },
        {
          month: 2,
          data: []
        },
        {
          month: 3,
          data: []
        },
        {
          month: 4,
          data: []
        },
        {
          month: 5,
          data: []
        },
        {
          month: 6,
          data: []
        },
        {
          month: 7,
          data: []
        },
        {
          month: 8,
          data: []
        },
        {
          month: 9,
          data: []
        },
        {
          month: 10,
          data: []
        },
        {
          month: 11,
          data: []
        },
        {
          month: 12,
          data: []
        },
      ];
      recordList.forEach(data => {
          let month = new Date(data.endTime).getMonth() + 1;
          fenLei[month-1].data.push(data)
      });
      let yueList=[]
      let cheNum=[]
      let moneyList=[]
      fenLei.forEach((data) => {
          if (data.data.length>0){
              yueList.push(data.month+"月")
              cheNum.push(data.data.length)
              let Money = 0
              data.data.forEach((row) => {
                  if(row.user==null){
                      Money+=parseInt(row.money)
                  }
              })
              moneyList.push(Money)
          }
      })
      console.log(yueList,cheNum,moneyList)
      let myChart = this.$echarts.init(document.getElementById("up"));
      let option = {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["date1", "date2"]
        },
        label: {
          show: true,
          position: "bottom", // 设置拐点处数字出现的位置
          textStyle: {
            color: "#00ca95"
          }
        },
        xAxis: {
          type: "category",
          data: yueList
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "停车数量",
            data: cheNum,
            type: "line",
            smooth: true
          },
          {
            name: "收入",
            data: moneyList,
            type: "line",
            smooth: true
          }
        ]
      };
      myChart.setOption(option);
    }
  },
  mounted() {
    this.drawChart();
    this.drawUpChart();
  }
};
</script>


