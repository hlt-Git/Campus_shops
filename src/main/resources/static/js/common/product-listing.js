let prices = 0;//价格排序
let category = "全部";//商品类别
let actcategoryid="ac1";
let area = "全部";//商品区域
let minmoney = 0;//商品最低价
let maxmoney = 5000;//商品最高价
layui.use(['form', 'slider'], function () {
    var form = layui.form
        , layer = layui.layer;
    var $ = layui.$
        , slider = layui.slider;
    //开启范围选择
    slider.render({
        elem: '#slideTest9'
        , value: [0, 5000] //初始值
        , range: true //范围选择
        , min: 0 //最小值
        , max: 30000 //最大值
        , change: function (value) {
            minmoney=value[0];
            maxmoney=value[1];
            gouzaopage();
        }
    });
    form.on('select(setprices)', function (data) {
        var indexGID = data.elem.selectedIndex;
        prices = data.elem[indexGID].title;
        gouzaopage();
    });
    form.on('select(setareas)', function (data) {
        var indexGID = data.elem.selectedIndex;
        area = data.elem[indexGID].title;
        if(area!="全部"){
            if(userid==null){
                layer.msg("登录后才能查看"+area+"的数据", {
                    time: 1000,
                    icon: 2,
                    offset: '300px'
                });
            }else{
                gouzaopage();
            }
        }else {
            gouzaopage();
        }
    });
});
function setcategory(categroys,actid){
    category=categroys;
    $("#"+actcategoryid).removeClass("current");
    actcategoryid=actid;
    $("#"+actcategoryid).addClass("current");
    gouzaopage();
}
var productList = new Vue({
    el: '#productlist',
    data() {
        return {
            productlistData: []
        }
    },
    mounted: function () {
        this.gouzaopage();
        window.gouzaopage = this.gouzaopage;
        window.lookproductlistData = this.lookproductlistData;
    },
    methods: {
        //创建layui的分页
        gouzaopage:function () {
            $.ajax({
                url: basePath + "/list-number/"+category+"/"+area+"/"+minmoney+"/"+maxmoney,
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
                                lookproductlistData(obj.curr);
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
        , lookproductlistData:function (page) {
            var that=this;
            $.ajax({
                url: basePath + "/product-listing/"+category+"/"+page+"/"+area+"/"+minmoney+"/"+maxmoney+"/"+prices,
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