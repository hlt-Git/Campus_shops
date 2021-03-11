//获取验证码js
function getcode() {
    var b = Math.random();
    document.getElementById("image1").src = basePath+"/images?d\x3d" + b
}
layui.config({
    base: '../../layuiadmin/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'user'], function () {
    var $ = layui.$
        , setter = layui.setter
        , admin = layui.admin
        , form = layui.form
        , router = layui.router()
        , search = router.search;

    form.render();
    //提交
    form.on('submit(LAY-user-login-submit)', function (obj) {

        $.ajax({
            type: "POST",
            url: "/admin/login",
            data: JSON.stringify(obj.field),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.status == 200) {
                    layer.msg(data.message, {
                        time: 1000,
                        icon: 1,
                        offset: '100px'
                    }, function () {
                        location.href="/admin/index";
                    });
                } else {
                    layer.msg(data.message, {
                        time: 1000,
                        icon: 5,
                        offset: '100px'
                    });
                    $("#submitlg").removeClass("layui-btn-disabled");
                    $("#submitlg").attr("disabled", false);
                }
            },error:function () {
                layer.msg("系统错误", {
                    time: 1000,
                    icon: 2,
                    offset: '100px'
                });
            }
        });
    });
});