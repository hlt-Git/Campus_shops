var app = new Vue({
    el: '#relnews',
    data() {
        return {
            mainimg: ""
        }
    },
    mounted: function () {
        //将vue中的函数设置成全局的
        window.setmainimgurl = this.setmainimgurl;
        window.getmainimgurl = this.getmainimgurl;
    },
    methods: {
        setmainimgurl: function (imgurl) {
            var that = this;
            that.mainimg = imgurl;
        }, getmainimgurl: function () {
            var that = this;
            return that.mainimg;
        }
    }
});

layui.use(['form', 'upload', 'element', 'layedit'], function () {
    let upload = layui.upload;
    let form = layui.form, layedit = layui.layedit;
    layedit.set({
        uploadImage: {
            url: basePath + '/relgoods/video'
            , type: 'post'
        }
    });
    //创建一个题目编辑器
    let editIndex = layedit.build('newscontents', {
        tool: [
            'strong'
            , 'italic'
            , 'underline'
            , 'del'
            , '|'
            , 'link'
            , 'face'
            , 'image'
        ]
    });
    upload.render({
        elem: '#test2'
        , url: basePath + '/relgoods/video'
        , accept: 'images' //图片
        , size: 1024 * 20
        , exts: 'png|jpg|jpeg'
        , progress: function (n) {
            var percent = n + '%'; //获取进度百分比
            layer.msg(percent, {
                icon: 16
                , shade: 0.01
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            } else {
                layer.closeAll('loading');
                layer.msg('上传成功', {
                    time: 1000,
                    icon: 1,
                    offset: '150px'
                });
                setmainimgurl(res.data.src);
                $("#mainimage").show();
            }
        }, error: function () {
            layer.closeAll('loading');
            layer.msg('上传失败', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
        }
    });
    form.on('submit(demo1)', function (data) {
        let vuemainimg = getmainimgurl();
        if (vuemainimg.length === 0) {
            layer.msg('请上传公告的主图', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
            return false;
        }
        if (data.field.newstitle.length > 200) {
            layer.msg('公告标题过长', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
            return false;
        }
        var object = new Object();
        object["newstitle"] = data.field.newstitle;
        object["newscontent"] = layedit.getContent(editIndex);
        object["image"] = vuemainimg;
        object["newsdesc"] = data.field.newsdesc;
        var jsonData = JSON.stringify(object);
        $.ajax({
            url: basePath + "/news/insert",
            data: jsonData,
            contentType: "application/json;charset=UTF-8",
            type: "post",
            dataType: "json",
            beforeSend: function () {
                layer.load(1, {
                    content: '发布中...',
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
                layer.msg("发布成功", {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                    window.location.reload();
                });
            }, error: function () {
                layer.msg('发布失败', {
                    time: 1000,
                    icon: 2,
                    offset: '150px'
                });
            }
        });
        return false;
    });
});