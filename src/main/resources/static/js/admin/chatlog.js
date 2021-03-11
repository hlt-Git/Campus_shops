layui.use(['layim', 'laypage'], function () {
    var layim = layui.layim
        , layer = layui.layer
        , laytpl = layui.laytpl
        , $ = layui.jquery
        , laypage = layui.laypage;
    //开始请求聊天记录
    var param = location.search; //获取url上面的参数，参数上有消息的类型
    var array=param.replace("?id=", "").replace("type=", "").split("&");
    var id = array[0];
    var type=array[1];
    var res = {
        code: 0
        , msg: ''
        , uid: userid
        , data:""
    };
    if(type==="friend"){
        $.ajax({
            type: 'get',
            url: basePath + '/chatlog/' + id,
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            beforeSend: function () {
                layer.load(1, { //icon支持传入0-2
                    content: '查询中...',
                    success: function (layero) {
                        layero.find('.layui-layer-content').css({
                            'padding-top': '39px',
                            'width': '60px'
                        });
                    }
                });
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (msg) {
                res.data=msg;
                var html = laytpl(LAY_tpl.value).render({
                    data: res
                });
                $('#LAY_view').html(html);
            },
            error: function (err) {
                console.log("err:", err);
            }
        });
    }else if(type==="group"){
        $.ajax({
            type: 'get',
            url: basePath + '/group/chatlog/' + id,
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            beforeSend: function () {
                layer.load(1, { //icon支持传入0-2
                    content: '查询中...',
                    success: function (layero) {
                        layero.find('.layui-layer-content').css({
                            'padding-top': '39px',
                            'width': '60px'
                        });
                    }
                });
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (msg) {
                res.data=msg;
                var html = laytpl(LAY_tpl.value).render({
                    data: res
                });
                $('#LAY_view').html(html);
            },
            error: function (err) {
                console.log("err:", err);
            }
        });
    }

});