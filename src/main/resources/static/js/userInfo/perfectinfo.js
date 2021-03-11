layui.config({
    base: '../../../layuiadmin/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'set']);

layui.use('laydate', function(){
    var laydate = layui.laydate;

    //年选择器 限定可选日期
    var ins22 = laydate.render({
        elem: '#time'
        ,type: 'year'
        ,theme: 'molv'
        ,min: '2010-01-01'
        ,max: maxDate()
        ,btns: ['confirm']
    });
});
function maxDate() {
    var now = new Date();
    return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
}

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
            layer.msg('上传成功', {
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
        object["school"] = data.field.school;
        object["faculty"] = data.field.faculty;
        object["startime"] = data.field.startime;
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
                    content: '完善中...',
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
                        var mylay = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(mylay);
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