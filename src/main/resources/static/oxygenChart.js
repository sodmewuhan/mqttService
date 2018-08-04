var option
var myChart

var chartData; //得到的数据

$(function () {
    initEvent();
})

function initEvent() {
    $("#query_btn").click(function(){
        var days_par = $('#days').val()
        if (days_par == '') {
            alert("请输入查询条件")
            return false
        }
        $.ajax(
            {
                url:"oxygen",
                data:{"days":days_par},
                type:"post",
                dataType:"json",
                success:function(data){
                  option = {
                     title: {
                         text: data.title
                     },
                     tooltip: {},
                     legend: {
                         data:['溶氧量']
                     },
                     xAxis: {
                         data: data.xAxis
                     },
                     yAxis: {},
                     series: [{
                         name: data.title,
                         type: 'line', //'bar'
                         data: data.yAxis
                     }]
                 };
                 myChart = echarts.init(document.getElementById('main'));
                 myChart.setOption(option);
                },
                error: function() {
                    alert("error");
                }
            });


    })


}

function initChart() {


}