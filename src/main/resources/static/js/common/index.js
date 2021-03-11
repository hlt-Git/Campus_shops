window.onload = function() {

    let indexList = new Vue({
        el: '#indexssss',
        data() {
            return {
                indexData: [],
                latestData: [],
                categoryList: ['全部', '3C数码', '书籍', '生活用品', '服饰', '美妆', '出行','其他'],
                clilckCategory: '全部',
                newsData:[]
            }
        },
        mounted: function() {
            this.getCategoryList(this.clilckCategory);
            this.getLatestProduct();
            this.getLatestNews();
        },
        methods: {
            getCategoryList: function(choiced) {
                let that = this;
                $.ajax({
                    url: basePath + '/index/product/' + choiced,
                    data: "",
                    type: 'GET',
                    contentType: "application/json;charset=UTF-8",
                    dataType: 'json',
                    success: function(msg) {
                        if(msg.status === 200) {
                            that.indexData = msg.data;
                        }
                    },error:function () {
                        layer.msg("系统错误", {
                            time: 1000,
                            icon: 5,
                            offset: '100px'
                        });
                    }
                })
            },
            //最新商品
            getLatestProduct: function () {
                var that = this;
                $.ajax({
                    url: basePath + '/product/latest',
                    data: "",
                    type: 'GET',
                    contentType: "application/json;charset=UTF-8",
                    dataType: 'json',
                    success: function(msg) {
                        if(msg.status === 200) {
                            that.latestData = msg.data;
                        }
                    },error:function () {
                        layer.msg("系统错误", {
                            time: 1000,
                            icon: 5,
                            offset: '100px'
                        });
                    }
                })
            },
            // 点击商品的分类：把选中的分类传到后台，返回数据重新渲染数据
            sendCategoryLabel(choiceName) {
                this.clilckCategory = choiceName;
                this.getCategoryList(this.clilckCategory);
            }//查询最新公告
            ,getLatestNews: function () {
                var that = this;
                $.ajax({
                    url: basePath + '/news/all',
                    data: "",
                    type: 'get',
                    contentType: "application/json;charset=UTF-8",
                    dataType: 'json',
                    success: function(msg) {
                        that.newsData = msg.data;
                        $("#lastnews").show();
                    },error:function () {
                        layer.msg("系统错误", {
                            time: 1000,
                            icon: 5,
                            offset: '100px'
                        });
                    }
                })
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
}