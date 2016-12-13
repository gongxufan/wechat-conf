(function ($,window) {
    $(function () {
        //随机生成43位Key
        $('#js_random').click(function () {
            $.get(serverContext+'/wx/sysconfig/randomKey').success(function (e) {
                if(e.encoding_aeskey)
                $('#js_key').val(e.encoding_aeskey),
                    $('#js_key_len_tips').html('43&nbsp;<em>|</em>&nbsp;43'),
                    $("#keyTips").hide();
            });
        });
        //key输入检测
        $("#js_key").on("input propertychange",function () {
            if(!this.value){
                $("#keyTips").show();
                return;
            }
            $('#js_key_len_tips').html(this.value.length+'&nbsp;<em>|</em>&nbsp;43');
            if(/^[a-zA-Z0-9]{43}$/.test(this.value)){
                $("#keyTips").hide();
            }else{
                $("#keyTips").show();
            }
        }).blur(function () {
            if(/^[a-zA-Z0-9]{43}$/.test(this.value)){
                $("#keyTips").hide();
            }else{
                $("#keyTips").show();
            }
        });
        //token检测
        $("#token").on("input propertychange",function () {
            if(!/^[\w]{3,32}$/.test(this.value)){
                $("#tokenTips").show();
            }else{
                $("#tokenTips").hide();
            }
        }).blur(function () {
            if(!/^[\w]{3,32}$/.test(this.value)){
                $("#tokenTips").show();
            }else{
                $("#tokenTips").hide();
            }
        });

        $("#appId").blur(function () {
            if(!this.value){
                $("#appTips").show();
            }else{
                $("#appTips").hide();
            }
        });
        $("#appSecret").blur(function () {
            if(!this.value){
                $("#secretTips").show();
            }else{
                $("#secretTips").hide();
            }
        });
        //加密方式
        $("#js_encrypt_type").find("label").click(function () {
           var _self = $(this);
            _self.addClass("selected").parent().siblings().find("label").removeClass("selected");
        });
        //提交
        $("#commit").click(function () {
            //校验
            var appId = $("#appId").val();
            if(!appId){
                $("#appTips").show();
                return;
            }
            var appSecret = $("#appSecret").val();
            if(!appSecret){
                $("#secretTips").show();
                return;
            }
            var token = $("#token").val();
            if(!/^[\w]{3,32}$/.test(token)){
                $("#tokenTips").show();
                return
            }
            var key = $("#js_key").val();
            if(!/^[a-zA-Z0-9]{43}$/.test(key)){
                $("#keyTips").show();
                return;
            }

            var encryptMessage = $("#js_encrypt_type").find("label[class$='selected']").find("input").val();
            $.get(serverContext+'/wx/sysconfig/commitSyscofig',{
                "data":"appId:"+appId +";appSecret:" + appSecret+";token:" + token+";encodingAesKey:"+key+";encryptMessage:"+encryptMessage
            }).success(function (data) {
                dealWXApiResult(data)
            });
        });
    });
})(jQuery,window);

