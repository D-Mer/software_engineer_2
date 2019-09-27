$(document).ready(function () {

    // 若当前用户的身份为“管理员”，侧边栏要加入“角色管理”操作
    if (sessionStorage.getItem('identity') == "管理员") {
        $('.nav-stacked').append(
            "<li role='presentation'><a href='/admin/role/manage'><i class='icon-group'></i> 角色管理</a></li>"
        )
    }

    // 因为各方法都会用到users集合，故将其设置为全局变量，方便调用
    var target_vip_users = [];

    getStrategies();

    getTargetVip(0);

    getCoupons();

    // 从后台GET所有的会员卡充值优惠策略
    function getStrategies() {
        getRequest(
            '/vip/getVIPPromotion',
            function (res) {
                var strategies = res.content;
                renderStrategies(strategies);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    // 前端显示请求到的会员卡充值优惠策略
    function renderStrategies(strategies) {

        $(".content-strategy").empty();
        var strategiesDomStr = "";

        strategies.forEach(function (strategy) {
            strategiesDomStr +=
                "<div class='wrapper'>" +
                    "<div class='content'>" +
                        "<div class='coupon_title'>会员卡充值优惠</div>" +
                        "<div class='id' style='display:none;'>ID: " + strategy.id + "</div>" +
                        "<div class='start_time' style='display:none;'>生效日期: " + formatDate(new Date(strategy.startTime)) + "</div>" +
                        "<div class='end_time'>有效期至: " + formatDate(new Date(strategy.endTime)) + "</div>" +
                    "</div>" +
                    "<div class='split-line'></div>" +
                    "<div class='edit_container'>" +
                        "<div class='front'>" +
                            "<div class='tip'>" +
                                "<div class='money'>$" + strategy.minus + "</div>" +
                                "<div class='pay-line' style='white-space:nowrap;'>满" + strategy.standard + "元送</div>" +
                            "</div>" +
                        "</div>" +
                        "<div class='back'>" +
                            "<div class='tip' style='background-image:linear-gradient(60deg, #96deda 0%, #50c9c3 100%);'>" +
                                "<button class='button' onclick='changeStrategy(this)' data-backdrop='static' data-toggle='modal' data-target='#editStrategyModal'>修改</button>" +
                            "</div>" +
                        "</div>" +
                    "</div>" +
                "</div>";
        });

        $(".content-strategy").append(strategiesDomStr);
    }

    // 点击"strategy-form-btn" -> 发布充值优惠策略
    $("#strategy-form-btn").click(function () {
        var form = {
            standard: $("#coupon-target-input").val(),
            minus: $("#coupon-discount-input").val(),
            startTime: new Date($("#strategy-start-date-input").val()).toISOString().split('T')[0],
            endTime: new Date($("#strategy-end-date-input").val()).toISOString().split('T')[0]
        };
        postRequest(
            '/vip/releaseVIPPromotion',
            form,
            function (res) {
                if (res.success) {
                    getStrategies();
                    $("#strategyModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    // 为方便修改会员卡充值优惠策略时，当前页面已有优惠策略数据的调用，将changeStrategy函数置于window内
    window.changeStrategy = function (strategy) {
        var strObj = strategy.parentNode.parentNode.parentNode.parentNode;
        var reg = /[\u4e00-\u9fa5]/g;
        var id = parseInt(strObj.getElementsByClassName('id').item(0).innerHTML.split(' ')[1]);
        var startTime = formatDate(new Date(strObj.getElementsByClassName('start_time').item(0).innerHTML.split(' ')[1]));
        var endTime = formatDate(new Date(strObj.getElementsByClassName('end_time').item(0).innerHTML.split(' ')[1]));
        var targetAmount = parseInt(strObj.getElementsByClassName('pay-line').item(0).innerHTML.replace(reg, ''));
        var discountAmount = parseInt(strObj.getElementsByClassName('money').item(0).innerHTML.slice(1));
        $("#edit-strategy-id-input").val(id);
        $("#edit-strategy-start-date-input").val(startTime);
        $("#edit-strategy-end-date-input").val(endTime);
        $("#edit-coupon-target-input").val(targetAmount);
        $("#edit-coupon-discount-input").val(discountAmount);
    };

    // 点击"edit-strategy-form-btn"，触发函数，POST修改后的优惠策略到后台
    $("#edit-strategy-form-btn").click(function () {
        var form = {
            id: $("#edit-strategy-id-input").val(),
            standard: $("#edit-coupon-target-input").val(),
            minus: $("#edit-coupon-discount-input").val(),
            startTime: new Date($("#edit-strategy-start-date-input").val()).toISOString().split('T')[0],
            endTime: new Date($("#edit-strategy-end-date-input").val()).toISOString().split('T')[0]
        };

        postRequest(
            '/vip/updateVIPPromotion',
            form,
            function (res) {
                if (res.success) {
                    getStrategies();
                    $("#editStrategyModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    // 从后端GET所以消费额超过管理员指定额度的VIP
    function getTargetVip(target_amount) {
        getRequest(
            '/coupon/getVIPList?target_amount=' + target_amount,
            function (res) {
                if (res.success) {
                    target_vip_users = res.content;
                    showTargetVip(target_vip_users);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    // 前端显示请求到的VIP
    function showTargetVip(vipUsers) {
        var flag = 0;
        $(".module-body").empty();
        var targetVipDomStr = "";

        vipUsers.forEach(function (vip) {
            if (flag % 2 == 0){
                targetVipDomStr += "<div class='row-fluid'>";
            }
            targetVipDomStr +=
                "<div class='span6'>" +
                    "<div class='media user'>" +
                        "<a class='media-avatar pull-left' href='#'> <img src='/images/user.png'> </a>" +
                        "<div class='media-body'>" +
                            "<h3 class='media-title'>" + vip.username + "</h3>" +
                            "<p><small class='muted'>VIP</small></p>" +
                            "<div class='media-option btn-group shaded-icon'>" +
                                "<button class='btn btn-small'>" +
                                    "<i class='icon-yen' style='display:inline;'></i>" +
                                    "<h5 style='display:inline;padding-right:4px;'>" + parseInt(vip.cost) + "</h5>" +
                                "</button>" +
                            "</div>" +
                        "</div>" +
                    "</div>" +
                "</div>";
            if (flag % 2 != 0) {
                targetVipDomStr += "</div>";
                flag = 0;
            } else {
                flag = 1;
            }
        });

        $(".module-body").append(targetVipDomStr);
    }

    // 用户点击"vip-form-btn"后，POST请求消费额超过管理员指定额度的VIP
    $("#vip-form-btn").click(function () {
        var target_amount = $('#target_amount').val();
        if (target_amount == '' || target_amount == undefined || target_amount == null) {
            target_amount = 0;
        }
        getTargetVip(target_amount);
        return false;
    });

    // AJAX提交筛选出的会员
    $('#nextBtn').click(function(){
        var i = $(this).index() + 1;
        $('.processorBox li').removeClass('current').eq(i).addClass('current');
        $('.step').fadeOut(300).eq(i).removeClass('hide');
    });

    // 从后台GET所有可赠送的coupons
    function getCoupons() {
        getRequest(
            '/coupon/getAllCoupon',
            function (res) {
                if (res.success) {
                    showCoupons(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    // 前端显示GET到的coupons
    function showCoupons(coupons) {

        var flag = 0;
        $(".coupon-row").empty();
        var couponsDomStr = "";

        coupons.forEach(function (coupon) {
            if (flag % 2 == 0){
                couponsDomStr +=
                    "<div class='col-lg-3 cool-md-6 col-sm-12'>" +
                        "<div class='coupon-card'>" +
                            "<ul class='coupon-pricing coupon-body'>" +
                                "<li style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><big>" + coupon.name + "</big></li>" +
                                "<li style='display:none;'>ID: " + coupon.id + "</li>" +
                                "<li>" + coupon.description + "</li>" +
                                "<li>" + coupon.startTime.split('T')[0] + "</li>" +
                                "<li>" + coupon.endTime.split('T')[0] + "</li>" +
                                "<li>" +
                                    "<h3>¥" + coupon.targetAmount + "</h3>" +
                                    "<span>-" + coupon.discountAmount + "</span>" +
                                "</li>" +
                                "<li>" +
                                    "<button class='coupon-btn coupon-btn-primary coupon-btn-round coupon-btn-simple'>赠送" +
                                    "<div style='display:none;'>" + coupon.id + "</div>" +
                                    "</button>" +
                                "</li>" +
                            "</ul>" +
                        "</div>" +
                    "</div>";
                flag = 1;
            } else {
                couponsDomStr +=
                    "<div class='col-lg-3 cool-md-6 col-sm-12'>" +
                        "<div class='coupon-card'>" +
                            "<ul class='coupon-pricing coupon-body active'>" +
                                "<li style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><big>" + coupon.name + "</big></li>" +
                                "<li style='display:none;'>ID: " + coupon.id + "</li>" +
                                "<li>" + coupon.description + "</li>" +
                                "<li>" + coupon.startTime.split('T')[0] + "</li>" +
                                "<li>" + coupon.endTime.split('T')[0] + "</li>" +
                                "<li>" +
                                    "<h3>¥" + coupon.targetAmount + "</h3>" +
                                    "<span>-" + coupon.discountAmount + "</span>" +
                                "</li>" +
                                "<li>" +
                                    "<button class='coupon-btn coupon-btn-primary coupon-btn-round'>赠送" +
                                        "<div style='display:none;'>" + coupon.id + "</div>" +
                                    "</button>" +
                                "</li>" +
                            "</ul>" +
                        "</div>" +
                    "</div>";
                flag = 0;
            }
        });

        $(".coupon-row").append(couponsDomStr);
    }

    // AJAX提交优惠券，并提示用户发送成功（5s后跳转到初始界面）
    $(document).on('click','.coupon-btn',function() {
        var i = $(this).index() + 2;
        var couponForm = [];
        var couponId = parseInt($(this)[0].textContent.split('赠送')[1]);
        $('.processorBox li').removeClass('current').eq(i).addClass('current');
        $('.step').fadeOut(300).eq(i).removeClass('hide');


        target_vip_users.forEach(function (vip) {
            var form = {
                userId: vip.id,
                couponId: couponId
            };
            couponForm.push(form);
        });

        postRequest(
            '/coupon/send',
            couponForm,
            function (res) {
                if (res.success) {
                    console.dir(res);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

        countdown({
            maxTime: 5,
            minTime: 0,
            ing: function(c){
                $("#times").text(c);
            },
            after: function(){
                window.location.href="http://localhost:8080/admin/cinema/vip";
            }
        });
    });

});