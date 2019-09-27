$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    $("#movie-edit-form-btn").click(function () {
        var form={
                name: $('#movie-edit-name-input').val(),
                startDate: $('#movie-edit-date-input').val(),
                posterUrl: $('#movie-edit-img-input').val(),
                description: $('#movie-edit-description-input').val(),
                type: $('#movie-edit-type-input').val(),
                length: $('#movie-edit-length-input').val(),
                country: $('#movie-edit-country-input').val(),
                starring: $('#movie-edit-star-input').val(),
                director: $('#movie-edit-director-input').val(),
                screenWriter: $('#movie-edit-writer-input').val(),
                language: $('#movie-edit-language-input').val(),
                id: parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]),
        }

        //表单验证
        if (!form.name) {
            alert("名称空缺");
            return;
        }
        if (!form.starring){
            alert("主演空缺");
            return;
        }
        if (!form.country){
            alert("国家地区空缺");
            return;
        }
        if (!form.description){
            alert("描述空缺");
            return;
        }
        if (!form.language){
            alert("语言空缺");
            return;
        }
        if (!form.director){
            alert("导演空缺");
            return;
        }
        if (!form.screenWriter){
            alert("编剧空缺");
            return;
        }
        if (!form.posterUrl){
            alert("海报空缺");
            return;
        }
        if (!form.type){
            alert("类型空缺");
            return;
        }

        postRequest(
            '/movie/update',
            form,
            function (res) {
                if(res.success){
                    getMovie();
                    $("#movieEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

       // alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');

    });
    $("#delete-btn").click(function () {
        // alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
        var r=confirm("确认下架该电影吗？");
        // alert(parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]));//验证成功锁定id
        if (r){
            postRequest(
                '/movie/off/batch',
                {movieIdList:[parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1])]},
                function () {
                    if(res.success){
                        getMovie();
                        // $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

});