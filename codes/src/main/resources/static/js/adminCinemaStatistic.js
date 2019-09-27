$(document).ready(function() {

    if (sessionStorage.getItem('identity') == "管理员") {
        $('.nav-stacked').append(
            "<li role='presentation'><a href='/admin/role/manage'><i class='icon-group'></i> 角色管理</a></li>"
        )
    }

    var statisticDate = formatDate(new Date());

    var days = 30;

    var movies = 5;

    initStatisticDate();

    getScheduleRate();

    getBoxOffice();

    getAudiencePrice();

    getPlacingRate();

    getPolularMovie(days, movies);

    function initStatisticDate() {
        $('#statistic-date-input').val(statisticDate);

        // 过滤条件变化后重新查询
        $('#statistic-date-input').change(function () {
            statisticDate = $('#statistic-date-input').val();
            getPlacingRate();
        });
    }

    function getScheduleRate() {

        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content||[];
                var tableData = data.map(function (item) {
                   return {
                       value: item.time,
                       name: item.name
                   };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x: 'center',
                        y: 'bottom',
                        data: nameList
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    series: [
                        {
                            name: '面积模式',
                            type: 'pie',
                            radius: [30, 110],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            data: tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '所有电影票房',
                        subtext: '截止至'+new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title : {
                        text: '每日客单价',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'line'
                    }]
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPlacingRate() {
        // todo
        getRequest(
            '/statistics/placingRate?date=' + statisticDate.replace(/-/g,'/'),
            function (res) {
                var data = res.content || [];
                console.log(data);
                var tableData = [{
                    value: data * 100,
                    name: '上座率'
                }, {
                    value: (1 - data) * 100,
                    name: '空座率'
                }];
                console.log(tableData);
                var option = {
                    title: {
                        text: '上座率',
                        subtext: new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} : {c}%"
                    },
                    legend: {
                        x: 'center',
                        y: 'bottom',
                        data: tableData
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    series: [{
                        name: '上座率',
                        type: 'pie',
                        radius: [30, 110],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        data: tableData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }]
                };
                var placingRateChart = echarts.init($("#place-rate-container")[0]);
                placingRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getPolularMovie(days, movies) {
        // todo
        getRequest(
            '/statistics/popular/movie?days=' + days + '&movieNum=' + movies,
            function (res) {
                $("#day-num").text(days);
                $("#movie-num").text(movies);
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.movieName;
                });
                var option = {
                    title: {
                        text: '最受欢迎电影',
                        subtext: '最近' + days + '天，TOP ' + movies,
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    $('#days-modify-btn').click(function () {
        $("#days-modify-btn").hide();
        $("#days-set-input").val(days);
        $("#days-set-input").show();
        $("#days-confirm-btn").show();
    });

    $('#days-confirm-btn').click(function () {
        var dayNum = +$("#days-set-input").val();
        // 验证一下是否为数字
        if (typeof(dayNum) === 'number') {
            getPolularMovie(dayNum, movies);
            days = dayNum;
            $("#days-modify-btn").show();
            $("#days-set-input").hide();
            $("#days-confirm-btn").hide();
        } else {
            alert("请输入正确的数字！");
        }
    });

    $('#movies-modify-btn').click(function () {
        $("#movies-modify-btn").hide();
        $("#movies-set-input").val(movies);
        $("#movies-set-input").show();
        $("#movies-confirm-btn").show();
    });

    $('#movies-confirm-btn').click(function () {
        var movieNum = +$("#movies-set-input").val();
        // 验证一下是否为数字
        if (typeof(movieNum) === 'number') {
            getPolularMovie(days, movieNum);
            movies = movieNum;
            $("#movies-modify-btn").show();
            $("#movies-set-input").hide();
            $("#movies-confirm-btn").hide();
        } else {
            alert("请输入正确的数字！");
        }
    })

});