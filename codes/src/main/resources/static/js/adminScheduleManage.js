var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

$(document).ready(function() {

    if (sessionStorage.getItem('identity') == "管理员") {
        $('.nav-stacked').append(
            "<li role='presentation'><a href='/admin/role/manage'><i class='icon-group'></i> 角色管理</a></li>"
        )
    }

    var hallId,
        scheduleDate = formatDate(new Date()),
        schedules = [];

    initSelectAndDate();

    function getSchedules() {
        var _this = this

        getRequest(
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }

    function renderScheduleTable(schedules){
        $('.schedule-date-container').empty();
        $(".schedule-time-line").siblings().remove();
        schedules.forEach(function (scheduleOfDate) {
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");
            $(".schedule-container").append(scheduleDateDom);
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }

    function mapScheduleStyle(schedule) {
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',
            height: 40*(end-start)+'px'
        }
    }

    function initSelectAndDate() {
        $('#schedule-date-input').val(scheduleDate);
        getCinemaHalls();
        getAllMovies();
        
        // 过滤条件变化后重新查询
        $('#hall-select').change (function () {
            hallId=$(this).children('option:selected').val();
            getSchedules();
        });
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            getSchedules();
        });
    }

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                movieList.forEach(function (movie) {
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $(document).on('click','.schedule-item',function (e) {
        var schedule = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;
        console.log(schedule);
    });
    
    $('#schedule-form-btn').click(function () {
        var form = {
            hallId: $("#schedule-hall-input").children('option:selected').val(),
            movieId : $("#schedule-movie-input").children('option:selected').val(),
            startTime: $("#schedule-start-date-input").val(),
            endTime: $("#schedule-end-date-input").val(),
            fare: $("#schedule-price-input").val()
        };
        //todo 需要做一下表单验证？
        // console.log(form.startTime);
        // console.log(form.endTime);
        if (form.fare<0){
            alert("费用必须大于0");
            return;
        }

        var s1=form.startTime.split("-");
        var s2=form.endTime.split("-");
        var subs1=s1[2].split("T");
        var subs2=s2[2].split("T");
        // function checkFormDate() {
            if (s1[0]>s2[0]) {
                alert("年份不对，开始年份应该比结束年份早");
                return;
            }
            else if (s1[1]>s2[1]){
                alert("月份不对，开始月份应该比结束月份早");
                return;
            }
            else if (subs1[0]>subs2[0]||(subs1[0]!=subs2[0])){
                alert("日子不对，开始日子应该比结束日子早,且不能跨天");
                return;
            }
            else if (subs1[1].substring(0,2)>subs2[1].substring(0,2)){
                alert("小时不对，开始时间应该比结束时间晚");
                return;
            }
            else if (subs1[1].substring(3,5)>subs2[1].substring(3,5)) {
                alert("分钟不对，开始时间应该比结束时间晚");
                return;
            }
        if (!form.endTime){
            alert("结束时间没填完");
            return;
        }
        if (!form.startTime){
            alert("开始时间没填完");
            return;
        }
        if (!form.fare){
            alert("费用没填完");
            return;
        }
        if (!form.hallId) {
            alert("影厅没填完")
            return;
        }
        if (!form.movieId ){
            alert("影片名称没填完")
            return;
        }
        // }
        // checkFormDate();
        postRequest(
            '/schedule/add',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
            movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
            startTime: $("#schedule-edit-start-date-input").val(),
            endTime: $("#schedule-edit-end-date-input").val(),
            fare: $("#schedule-edit-price-input").val()
        };
        //todo 需要做一下表单验证？

        var s1=form.startTime.split("-");
        var s2=form.endTime.split("-");
        var subs1=s1[2].split("T");
        var subs2=s2[2].split("T");
        // function checkFormDate() {
        if (s1[0]>s2[0]) {
            alert("年份不对，开始年份应该比结束年份早");
            return;
        }
        else if (s1[1]>s2[1]){
            alert("月份不对，开始月份应该比结束月份早");
            return;
        }
        else if (subs1[0]>subs2[0]||(subs1[0]!=subs2[0])){
            alert("日子不对，开始日子应该比结束日子早,且不能跨天");
            return;
        }
        else if (subs1[1].substring(0,2)>subs2[1].substring(0,2)){
            alert("小时不对，开始时间应该比结束时间晚");
            return;
        }
        else if (subs1[1].substring(3,5)>subs2[1].substring(3,5)) {
            alert("分钟不对，开始时间应该比结束时间晚");
            return;
        }
        if (!form.endTime){
            alert("结束时间没填完");
            return;
        }
        if (!form.startTime){
            alert("开始时间没填完");
            return;
        }
        if (!form.fare){
            alert("费用没填完");
            return;
        }
        if (!form.hallId) {
            alert("影厅没填完")
            return;
        }
        if (!form.movieId ){
            alert("影片名称没填完")
            return;
        }
        // }
        // checkFormDate();
        postRequest(
            '/schedule/update',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗");
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

});

