layui.use(['form', 'slider'], function () {
    var form = layui.form
        , layer = layui.layer;
});
var newslist = new Vue({
    el: '#newslist',
    data() {
        return {
            newslistdata: []
        }
    },
    mounted: function () {
        this.gouzaopage();
        window.gouzaopage = this.gouzaopage;
        window.looknewslistData = this.looknewslistData;
    },
    methods: {
        //创建layui的分页
        gouzaopage:function () {
            $.ajax({
                url: basePath + "/news/index/number",
                data: "",
                contentType: "application/json;charset=UTF-8", //发送数据的格式
                type: "get",
                dataType: "json", //回调
                success: function (data) {
                    layui.use(['laypage', 'layer'], function () {
                        var laypage = layui.laypage
                            , layer = layui.layer;
                        //完整功能
                        laypage.render({
                            elem: 'layuipage'
                            , count: data.dataNumber
                            ,limit: 16
                            ,limits: [16, 32, 48]
                            , layout: ['count', 'prev', 'page', 'next']
                            , jump: function (obj) {
                                looknewslistData(obj.curr);
                            }
                        });
                    });
                },error:function () {
                    layer.msg("系统错误", {
                        time: 1000,
                        icon: 2,
                        offset: '100px'
                    });
                }
            });
        }//分页查询
        , looknewslistData:function (page) {
            var that=this;
            $.ajax({
                url: basePath + "/news/index/"+page,
                data: "",
                contentType: "application/json;charset=UTF-8", //发送数据的格式
                type: "get",
                dataType: "json", //回调
                success: function (data) {
                    that.newslistdata=data.data;
                    $("#newscontent").show();
                },error:function () {
                    layer.msg("系统错误", {
                        time: 1000,
                        icon: 2,
                        offset: '100px'
                    });
                }
            });
        },showNewsDetail:function(id){
            layer.open({
                type: 2,
                title: '公告详情',
                shadeClose: true,
                shade: 0.8,
                maxmin: true,
                area: ['50%', '60%'],
                content: basePath+'/news/detail/'+id
            });
        }
    }
})