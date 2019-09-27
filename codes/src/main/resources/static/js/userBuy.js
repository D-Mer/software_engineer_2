$(document).ready(function () {
    var ticketList;
    var refundStrategy;
    getMovieList();
    getRefundStrategy();

    //获取退票策略
    function getRefundStrategy(){
        getRequest(
            '/ticket/getRefundStrategy',
            function (res) {
                refundStrategy=res.message;
            }
        )
    }

    function getSchedule(scheduleid,i) {
        getRequest(
            '/schedule/'+ scheduleid,
            function (res) {
                renderTicketList(res.content,i);
            },
            function (error) {
                alert(error) ;
            }
        )
    }


    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                // console.log(res.content);
                ticketList = res.content;
                for(var i=0;i<res.content.length;i++){
                    getSchedule(ticketList[i].scheduleId,i);
                }
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空

    function renderTicketList(schedule,i) {
        var bodyContent="";
        var stateti="";
        if (ticketList[i].state==1){
            stateti="已完成";
        }
        if(ticketList[i].state==0){
            stateti="未完成";
        }
        if (ticketList[i].state==2){
            stateti="已失效";
        }
        if (ticketList[i].state==3){
            stateti="已退票";
        }
        var tiStartTime=schedule.startTime.toString();
        var smonth=tiStartTime.split("-")[1];
        var sday=tiStartTime.split("-")[2].substring(0,2);
        var sHourMin= tiStartTime.split("-")[2].substring(3,8)
        tiStartTime=smonth+"月"+sday+"日 "+sHourMin;

        var tiEndTime=schedule.endTime.toString();
        var emonth=tiEndTime.split("-")[1];
        var eday=tiEndTime.split("-")[2].substring(0,2);
        var eHourMin= tiEndTime.split("-")[2].substring(3,8)
        tiEndTime=emonth+"月"+eday+"日 "+eHourMin;

        bodyContent += "<tr><td>" + schedule.movieName + "</td>" +
            "<td>" + schedule.hallName  + "</td>" +
            "<td>" + (ticketList[i].rowIndex+1) + "排" + (ticketList[i].columnIndex+1) + "列" + "</td>" +
            "<td>" + tiStartTime + "</td>" +
            "<td>" + tiEndTime + "</td>" +
            "<td>" + stateti + "</td>"+
            '<td>' + ticketList[i].payAmount + "</td>";

        if (stateti=="已完成"){
            bodyContent+="<td><button class='btn btn-primary refundBtn' id=\""+ticketList[i].id+"\" >退票</button></td>"+"</tr>";
        }
        else {
            bodyContent+="<td></td></tr>"
        }
        $('#ticket-list-body').append(bodyContent);
    }

    $(document).on('click','.refundBtn',function (e) {
        var r=confirm(refundStrategy);
        var form;
        var ticketId = e.target.id;
        var userId = sessionStorage.getItem("id");
        if (r){
            postRequest(
                '/ticket/refund',
                {
                    ticketId : ticketId,
                    userId : userId
                },
                function (res) {
                    window.location.reload();
                    alert("根据退票策略，您得到的退款为："+res.content);
                }
            )
        }
    })

});