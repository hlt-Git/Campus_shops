//当前选中窗口的用户id
//var actuserid="";
//接入WebSocket
var socket = new WebSocket('ws://localhost/websocket/'+userid);

layui.use('layim', function(layim){
    //监听修改签名
    layim.on('sign', function(value){
        //通过Ajax将新的签名同步到数据库
        var object = new Object();
        object["id"] = userid;
        object["sign"] = value;
        var jsonData = JSON.stringify(object);
        $.ajax({
            url: basePath+"/chat/upsigin",
            data: jsonData,
            contentType: "application/json;charset=UTF-8", //发送数据的格式
            type: "post",
            dataType: "text",
            success: function (data) {
            },error:function () {
                layer.msg('系统错误', {
                    time: 2000,
                    icon: 2,
                    offset: '150px'
                });
            }
        });
    });
    //监听自定义工具栏-添加代码
    layim.on('tool(code)', function(insert, send, obj){ //事件中的tool为固定字符，而code则为过滤器，对应的是工具别名（alias）
        layer.prompt({
            title: '插入代码'
            ,formType: 2
            ,shade: 0
        }, function(text, index){
            layer.close(index);
            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器，主要由insert完成
            //send(); //自动发送
        });
    });

    //基础配置
    layim.config({
        init: {
            url:basePath+ '/initim'
            ,type: 'get' //默认get，一般可不填
            ,data: {} //额外参数
        }
        //获取群员接口
        ,members: {
            url: basePath+ '/group/member'
            ,type: 'get'
            ,data: {} //额外参数
        }
        //上传图片接口
        ,uploadImage: {
            url: basePath+'/chat/upimg' //接口地址
            ,type: 'post' //默认post
        }
        //上传文件接口
        ,uploadFile: {
            url: basePath+'/chat/upfile' //接口地址
            ,type: 'post' //默认post
        }
        //扩展工具栏
        ,tool: [{
            alias: 'code' //工具别名
            ,title: '代码' //工具名称
            ,icon: '&#xe64e;' //工具图标，参考图标文档
        }]
        ,isAudio: true
        ,isVideo: true
        ,title: "我的云聊" //自定义主面板最小化时的标题
        ,voice: "default.wav"  //新消息提醒音频
        ,notice: false //是否开启桌面消息提醒，默认false
        //,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        ,chatLog: basePath + '/tochatlog' //聊天记录界面
    });
    // //删除本地聊天数据
    layui.data('layim', {key: userid,remove: true});
    //监听发送的消息
    layim.on('sendMessage', function(res){
        var mine = res.mine; //包含发送的消息及登录用户信息
        var to=res.to;
        var object = new Object();
        object["username"] = mine.username;
        object["avatar"] = mine.avatar;
        object["id"] = mine.id;
        object["content"] = mine.content;
        object["toid"] = to.id;
        object["type"] = to.type;
        var jsonData = JSON.stringify(object);
        //发送给websocket
        socket.send(jsonData);
    });
    //监听聊天窗口的切换
    layim.on('chatChange', function(obj){
        //actuserid=obj.data.id;
    });
    //监听收到的消息
    socket.onmessage = function(res){
        if(res.data == "0"){
            layer.msg('该用户不在线', {
                time: 2000,
                icon: 2,
                offset: '150px'
            });
        }else {
            var jsonObject = JSON.parse(res.data);// Json字符串转对象
            if(jsonObject.type==="group"){
                //新消息提醒
                layim.getMessage({
                    username: jsonObject.username
                    ,avatar: jsonObject.avatar
                    ,id: jsonObject.toid
                    ,type: jsonObject.type
                    ,content: jsonObject.content
                    ,timestamp: new Date().getTime()
                });
            }else if(jsonObject.type==="friend"){
                    //新消息提醒
                    layim.getMessage({
                        username: jsonObject.username
                        ,avatar: jsonObject.avatar
                        ,id: jsonObject.id
                        ,type: jsonObject.type
                        ,content: jsonObject.content
                        ,timestamp: new Date().getTime()
                    });
                }
        }
    };
    //事件名：ready，用于监听LayIM初始化就绪
    layim.on('ready', function(options){
        //layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
        //console.log(options);
        //do something
        //layim.setFriendStatus(11111, 'online'); //设置指定好友在线，即头像取消置灰
        //layim.setFriendStatus("1571476959767947441", 'offline'); //设置指定好友不在线，即头像置灰
    });

    //更新当前会话状态
    layim.on('chatChange', function(res){
        var type = res.data.type;
        if(type === 'friend'){
            //layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
            if(res.data.status==="offline"){
                layim.setChatStatus('<span style="color:#FF5722;">离线</span>'); //模拟标注好友在线状态
            }else if(res.data.status==="online"){
                layim.setChatStatus('<span style="color:#777777;">在线</span>'); //模拟标注好友在线状态
            }
        }else if(type === 'group'){
            //模拟系统消息
            // layim.getMessage({
            //     system: true //系统消息
            //     ,id: 111111111
            //     ,type: "group"
            //     ,content: '贤心加入群聊'
            // });
        }
    });
});