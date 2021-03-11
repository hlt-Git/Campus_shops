var logregurl =  window.location.href;
var time=60;
var p_timer = null;
if(Cookies.get('times') != undefined){
    time=Cookies.get('times');
    p_timer = window.setInterval('timers()', 1000);
}else{
    time=60;
    $("#LAY-user-getsmscode").removeClass("layui-btn-disabled");
    $("#LAY-user-getsmscode").attr("disabled", false);
}
layui.use(['form', 'layer'], function () {
    var form = layui.form
        , layer = layui.layer;
    form.on('submit(oldPhone)', function (data) {
        $.ajax({
            url: basePath + "/user/updatephone/"+data.field.oldPhone+'/'+data.field.code,
            data: "",
            contentType: "application/json;charset=UTF-8",
            type: "put",
            dataType: "json",
            beforeSend: function () {
                layer.load(1, { //icon支持传入0-2
                    content: '修改中...',
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
            success: function (data) {
                if (data.status == 200) {
                    layer.msg(data.message, {
                        time: 1000,
                        icon: 1,
                        offset: '100px'
                    }, function () {
                        location.reload();
                    });
                } else {
                    layer.msg(data.message, {
                        time: 1000,
                        icon: 2,
                        offset: '100px'
                    });
                }
            }, error: function () {
                layer.msg('系统异常');
            }
        });
        return false;
    });
});
function getphonecode() {
    var phone=$("#oldPhone").val();
    if(phone.length!=11){
        layer.msg("请输入正确的手机号", {
            time: 1000,
            icon: 2,
            offset: '100px'
        });
        return;
    }
    let object = new Object();
    object["mobilephone"] = phone;
    object["type"] = 2;
    let jsonData = JSON.stringify(object); //根据数据生成json数据
    $.ajax({
        url: basePath + "/user/sendupdatephone",
        data: jsonData,
        contentType: "application/json;charset=UTF-8",
        type: "post",
        dataType: "json",
        beforeSend: function () {
            layer.load(1, { //icon支持传入0-2
                content: '发送中...',
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
        success: function (data) {
            if (data.status == 200) {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                });
                p_timer = window.setInterval('timers()', 1000);
            } else {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 2,
                    offset: '100px'
                });
            }
        }, error: function () {
            layer.msg('系统异常');
        }
    });
}
//倒计时
function timers() {
    if (time == 0) {
        window.clearInterval(p_timer);
        $("#LAY-user-getsmscode").html("获取验证码");
        $("#LAY-user-getsmscode").removeClass("layui-btn-disabled");
        $("#LAY-user-getsmscode").attr("disabled", false);
        Cookies.remove('times', {path: logregurl});
        time=60;
    } else {
        time = time - 1;
        $("#LAY-user-getsmscode").addClass("layui-btn-disabled");
        $("#LAY-user-getsmscode").attr("disabled", true);
        $("#LAY-user-getsmscode").html(time+"s后可重新发送");
        Cookies.remove('times', {path: logregurl});
        Cookies.set('times', time, {path: logregurl});
    }
}