layui.use(['form', 'element', 'util', 'carousel', 'form', 'laypage', 'layer','table','upload'], function () {
    var element = layui.element;
    var util = layui.util;
    var carousel = layui.carousel;
    var form = layui.form;
    var laypage = layui.laypage
        , layer = layui.layer
        , upload=layui.upload;
    //普通图片上传
    upload.render({
        elem: '#uimage'
        ,url: basePath+'/user/updateuimg'
        ,before: function(obj){
            layer.load(1, { //icon支持传入0-2
                content: '上传中',
                success: function (layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '60px'
                    });
                }
            });
        },done: function(res){
            layer.closeAll('loading');
            layer.msg('修改成功', {
                time: 1000,
                icon: 1,
                offset: '150px'
            }, function () {
                location.reload();
            });
        },error: function(){
            layer.msg('上传失败');
        }
    });
    form.on('submit(demo1)', function(data){
        var object = new Object();
        object["username"] = data.field.username;
        object["email"] = data.field.email;
        object["sex"] = data.field.sex;
        var jsonData = JSON.stringify(object);
        $.ajax({
            url: basePath + "/user/updateinfo",
            data: jsonData,
            contentType: "application/json;charset=UTF-8",
            type: "post",
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
            },error:function () {
                layer.msg('系统异常');
            }
        });
        return false;
    });
});