$(document).ready(function() {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();
    getReStrategies();

    // 若当前用户的身份为“管理员”，侧边栏要加入“角色管理”操作
    if (sessionStorage.getItem('identity') == "管理员") {
        $('.nav-stacked').append(
            "<li role='presentation'><a href='/admin/role/manage'><i class='icon-group'></i> 角色管理</a></li>"
        )
    }

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                renderHall(res.content);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        console.log(halls);
        $('#hall-card').empty();
        var hallDomStr = "";
        var hid=1;
        halls.forEach(function (hall,e) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +"<span>"+"<button id=\""+hall.id+"\" type=\"button\"  class=\"btn btn-primary editHall\" data-backdrop=\"static\" data-toggle=\"modal\"  >修改 "+hall.name+" 影厅信息</button>"+"</span>"+
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
            localStorage.setItem("hallid"+hid,hid)
            hid+=1;
        });
        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
        $("#canview-modify-btn").hide();
        $("#canview-set-input").val(canSeeDate);
        $("#canview-set-input").show();
        $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $(document).on('click','.editHall',function (e) {
        console.log(e.target.id);
        var hallid=e.target.id;
        $('#hallEditModal').modal('show');
        var form=getHallEditForm();
        form["id"]=hallid;
        localStorage.setItem("hallid",hallid);
        console.log(form);
    });

    $('#hall-form-btn').click(function () {
        var form=getHallForm();
        console.log(form);
        // if(!validateHallForm(form)) {
        //     return;
        // }
        postRequest(
            '/hall/addHall',
            form,
            function (res) {
                console.log(res);
                $('#hallModal').modal('hide');
                getCinemaHalls();
            },
            function (error) {
                $('#hallModal').modal('hide');
                alert(error);
            }
        )
    });

    $('#hall-edit-form-btn').click(function () {
        var form=getHallEditForm();
        form["id"]=localStorage.getItem("hallid");
        console.log("----------下面是要传送的东西------------")
        console.log(form);
        if(validateHallForm(form)){
            console.log("不合法");
            return;
        }
        postRequest(
            '/hall/updateHall',
            form,
            function (res) {
                $('#hallEditModal').modal('hide');
                getCinemaHalls();
            },
            function (error) {
                alert(error);
                $('#hallEditModal').modal('hide');
            }
        )
    });

    function getHallForm() {
        return  {
            name : $('#hall-name-input').val(),
            row : $('#hall-row-input').val(),
            column : $('#hall-col-input').val()
        }
    }

    function getHallEditForm() {
        return  {
            // id:
            name : $('#hall-edit-name-input').val(),
            row : $('#hall-edit-row-input').val(),
            column : $('#hall-edit-col-input').val()
        }
    }

    function validateHallForm(data) {
        var isValidate=true;
        if (!data.hallName){
            isValidate = false;
            $('#hall-name-input').parent('.form-group').addClass('has-error');
        }
        if (!data.row){
            isValidate = false;
            $('#hall-row-input').parent('.form-group').addClass('has-error');
        }
        if (!data.col){
            isValidate = false;
            $('#hall-col-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

    // 从后台GET所有退票策略
    function getReStrategies() {
        getRequest(
            '/ticket/getRefundStrategy',
            function (res) {
                var strategies = res.content;
                showReStrategies(strategies);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    // 前端显示请求到的退票策略
    function showReStrategies(reStrategies) {
        $("#table").tabullet({
            data: reStrategies,
            action: function (mode, data) {
                if (mode == 'save') {
                    addReStrategy(data);
                }
                if (mode == 'edit') {
                    updateReStrategy(data);
                }
            }
        });
    }

    // 将用户新添加的退票策略POST到后台
    function addReStrategy(reStrategy) {
        var form = {
            hoursBeforeEnd: reStrategy.hoursBeforeEnd,
            rate: reStrategy.rate
        };

        postRequest(
            '/ticket/addRefundStrategy',
            form,
            function (res) {
                if (res.success) {
                    getReStrategies();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    // 后台更新用户修改的退票策略
    function updateReStrategy(reStrategy) {
        console.log(reStrategy.id);
        var form = {
            id: reStrategy.id,
            hoursBeforeEnd: reStrategy.hoursBeforeEnd,
            rate: reStrategy.rate
        };

        postRequest(
            '/ticket/updateRefundStrategy',
            form,
            function (res) {
                if (res.success) {
                    getReStrategies();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});