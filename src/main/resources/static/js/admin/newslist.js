layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#newslist'
        , url: basePath+'/news/queryall'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'qid', title: 'ID',width:60, align:'center'}
            , {field: 'newstitle', title: '公告标题', width: 190, align:'center'}
            , {field: 'newsdesc', title: '公告简介', width: 580, align:'center'}
            , {field: 'username', title: '发布者', width: 100, align:'center'}
            , {field: 'createtime', title: '发布时间', width: 160, align:'center'}
            , {fixed: 'right', title: '操作', toolbar: '#barDemo', width:200, align:'center'}
        ]]
        ,height: 500
        , done: function (res, curr, count) {
            var i=1;
            $("[data-field='qid']").children().each(function () {
                if($(this).text() == 'ID') {
                    $(this).text("ID")
                }else{
                    $(this).text(i++)
                }
            });
        }
    });
    //监听行工具事件
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xiangqing') {
            layer.open({
                type: 2,
                title: '公告详情',
                shadeClose: true,
                shade: 0.8,
                maxmin: true,
                area: ['50%', '60%'],
                content: basePath+'/news/detail/'+data.id
            });
        }else if (obj.event === 'bianji') {
            layer.open({
                type: 2,
                title: '修改公告',
                shadeClose: true,
                shade: 0.8,
                maxmin: true,
                area: ['60%', '80%'],
                content: basePath+'/news/toupdate/'+data.id,
                end: function () {
                    location.reload();
                }
            });
        }else if(obj.event === 'shanchu'){
            layer.confirm('确认删除公告吗？', {
                btn: ['确定','算了'], //按钮
                title:"删除公告",
                offset:"50px"
            }, function(){
                layer.closeAll();
                $.ajax({
                    url: basePath+'/news/delect/'+data.id,
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "put",
                    dataType: "json", //回调
                    beforeSend: function () {
                        layer.load(1, { //icon支持传入0-2
                            content: '请稍等...',
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
                        console.log(data)
                        if(data.status===200){
                            layer.msg(data.message, {
                                time: 1000,
                                icon: 1,
                                offset: '50px'
                            }, function () {
                                location.reload();
                            });
                        }else {
                            layer.msg(data.message, {
                                time: 1000,
                                icon: 2,
                                offset: '50px'
                            });
                        }
                    }
                });
            }, function(){
            });
        }
    });
});