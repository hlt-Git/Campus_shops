layui.use(['form', 'slider'], function () {
    var form = layui.form
        , layer = layui.layer;
    form.on('submit(demo1)', function(data){
        gouzaopage(data.field.keys);
        return false;
    });
});
var productList = new Vue({
    el: '#productlist',
    data() {
        return {
            productlistData: []
        }
    },
    mounted: function () {
        this.gouzaopage(key);
        window.gouzaopage = this.gouzaopage;
        window.lookproductlistData = this.lookproductlistData;
    },
    methods: {
        //创建layui的分页
        gouzaopage:function (key) {
            $.ajax({
                url: basePath + "/product/search/number/"+key,
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
                                lookproductlistData(obj.curr,key);
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
        , lookproductlistData:function (page,key) {
            var that=this;
            $.ajax({
                url: basePath + "/product/search/"+page+"/"+key,
                data: "",
                contentType: "application/json;charset=UTF-8", //发送数据的格式
                type: "get",
                dataType: "json", //回调
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
                success: function (data) {
                    that.productlistData=data.data;
                    $("#contents").show();
                },error:function () {
                    layer.msg("系统错误", {
                        time: 1000,
                        icon: 2,
                        offset: '100px'
                    });
                }
            });
        }
    }
})