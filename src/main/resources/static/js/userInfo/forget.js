//重置密码部分
var re = /^1\d{10}$/; //正则表达式验证手机号

var app = new Vue({
    el: '#forget',
    data() {
        return {
            listimages: [],
            mainimg: "",
            videourl: ""
        }
    },
    mounted: function () {
        //将vue中的函数设置成全局的
        window.jiantingphone = this.jiantingphone;
    },
    methods: {
        jiantingphone: function () {
            var phone=$("#mobilephone").val();
            if(phone.length==0){
                layer.tips("请输入手机号", '#mobilephone', {
                    tips: [1, "#0FA6D8"],
                    tipsMore: !1,
                    time: 1300
                });
                $("#mobilephone").focus();
                return 0;
            }else if(!re.test(phone)){
                layer.tips("请输入合法的手机号", '#mobilephone', {
                    tips: [1, "#FF5722"],
                    tipsMore: !1,
                    time: 1300
                });
                $("#mobilephone").focus();
                return 0;
            }
            return 1;
        }
    }
});

function submitforget() {
    var mobilephone=$("#mobilephone").val();
    var password=$("#password").val();
    var vercode=$("#vercode").val();
    var t=jiantingphone();
    if(t==0){
        return;
    }
    if (password.length == 0) {
        layer.tips("请输入密码", '#password', {
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 1300
        });
        $("#password").focus();
        return;
    }
    if (password.length > 20 || password.length < 5) {
        layer.tips("密码长度为：5-20", '#password', {
            tips: [1, "#FF5722"],
            tipsMore: !1,
            time: 1500
        });
        $("#password").focus();
        return;
    }
    if (vercode.length === 0) {
        layer.tips("请输入验证码", '#vercode', {
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 1300
        });
        $("#vercode").focus();
        return;
    } else if (vercode.length != 6) {
        layer.tips("请输入正确验证码", '#vercode', {
            tips: [1, "#FF5722"],
            tipsMore: !1,
            time: 1300
        });
        $("#vercode").focus();
        return;
    }
    $("#submitrg").addClass("layui-btn-disabled");
    $("#submitrg").attr("disabled", true);
    var object = new Object(); //创建一个存放数据的对象
    object["mobilephone"] = mobilephone;
    object["password"] = password;
    object["vercode"] = vercode;
    var jsonData = JSON.stringify(object); //根据数据生成json数据
    $.ajax({
        url: basePath + "/user/resetpwd",
        data: jsonData,
        contentType: "application/json;charset=UTF-8", //发送数据的格式
        type: "post",
        dataType: "json", //回调
        beforeSend: function () {
            layer.load(1, { //icon支持传入0-2
                content: '重置中...',
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
                    location.href=basePath+"/";
                });
            }else {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 2,
                    offset: '100px'
                }, function () {
                    location.reload();
                });
            }
            $("#submitrg").removeClass("layui-btn-disabled");
            $("#submitrg").attr("disabled", false);
        },error:function () {
            layer.msg("系统错误", {
                time: 1000,
                icon: 5,
                offset: '100px'
            });
        }
    });
}


var url =  window.location.href;
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
//获取短信验证码
function getphonecode() {
    //判断手机号是否合法
    var t=jiantingphone();
    if(t==0){
        return;
    }
    //ajax到后台
    var mobilephone=$("#mobilephone").val();
    var object = new Object(); //创建一个存放数据的对象
    object["mobilephone"] = mobilephone;
    object["type"] = 1;
    var jsonData = JSON.stringify(object); //根据数据生成json数据
    $.ajax({
        url: basePath + "/user/sendresetpwd",
        data: jsonData,
        contentType: "application/json;charset=UTF-8", //发送数据的格式
        type: "post",
        dataType: "json", //回调
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
            } else {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 2,
                    offset: '100px'
                });
            }
        },error:function () {
            layer.msg("系统错误", {
                time: 1000,
                icon: 5,
                offset: '100px'
            });
        }
    });
    p_timer = window.setInterval('timers()', 1000);
}
//倒计时
function timers() {
    if (time == 0) {
        window.clearInterval(p_timer);
        $("#LAY-user-getsmscode").html("获取验证码");
        $("#LAY-user-getsmscode").removeClass("layui-btn-disabled");
        $("#LAY-user-getsmscode").attr("disabled", false);
        Cookies.remove('times', {path: url});
        time=60;
    } else {
        time = time - 1;
        $("#LAY-user-getsmscode").addClass("layui-btn-disabled");
        $("#LAY-user-getsmscode").attr("disabled", true);
        $("#LAY-user-getsmscode").html(time+"s后可重新发送");
        Cookies.remove('times', {path: url});
        Cookies.set('times', time, {path: url});
    }
}
