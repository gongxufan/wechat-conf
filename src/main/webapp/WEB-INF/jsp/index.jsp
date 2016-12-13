<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>微信公众平台开发模式配置管理系统</title>
    <link href="<%=request.getContextPath()%>/resources/easyui/css/default.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/easyui/css/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/easyui/css/themes/icon.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>

    <script type="text/javascript" src='<%=request.getContextPath()%>/resources/js/initUI.js'></script>

    <script type="text/javascript">
        var _menus = {
            "menus": [
                {"menuname": "自定义菜单", "icon": "icon-nav", "url": "<%=request.getContextPath()%>/wx/initMenuManage"},
                {"menuname": "素材管理", "icon": "icon-users", "url": "<%=request.getContextPath()%>/weixin/api/batchGetMaterial?&group_id=1&cols=3&type=NEWS&pageNow=1&count=6&view=card"},
                {"menuname": "用户管理", "icon": "icon-userManage", "url": "<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow=1&count=10&groupName=全部用户"},
                {"menuname": "消息群发", "icon": "icon-message", "url": "<%=request.getContextPath()%>/wx/groupMessage/"},
                {"menuname": "消息管理", "icon": "icon-messageManage", "url": "<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=0&isFavorite=0&count=20&pageNow=1"},
                {"menuname": "自动回复", "icon": "icon-autoreply", "url": "<%=request.getContextPath()%>/wx/autoReply/init?replyType=1"},
                {"menuname": "系统配置", "icon": "icon-set", "url": "<%=request.getContextPath()%>/wx/sysconfig/"}
            ]
        };
        $(function () {
        });


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
        <img src="<%=request.getContextPath()%>/resources/easyui/images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div region="north" split="true" border="false" style="overflow: hidden; height: 50px;
        background: url(<%=request.getContextPath()%>/resources/easyui/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 40px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
<%--    <span style="float:right; padding-right:20px;" class="head">欢迎 XXX <a href="#" id="editpass">修改密码</a> <a href="#"
                                                                                                             id="loginOut">安全退出</a></span>--%>
    <span style="padding-left:10px; font-size: 16px; "><%--<img
            src="<%=request.getContextPath()%>/resources/images/blocks.gif" width="20" height="20" align="absmiddle"/>--%> 微信公众平台开发模式配置管理系统</span>
</div>
<div region="south" split="false" style="height: 30px; background: #D2E0F2; ">
    <div class="footer">北京数字政通</div>
</div>
<div region="west" split="true" title="导航菜单" style="width:120px;" id="west">
    <div class="easyui-accordion" fit="true" border="false">
        <!--  导航内容 -->

    </div>

</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="使用须知" style="padding:20px;overflow:hidden;" id="home">

            <h3>欢迎使用微信公众号开发模式下自定义菜单及消息管理配置平台!</h3>
            <h3 style="color: red">系统使用注意事项：</h3>
            <h3>
                <a style="color:red" href="微信公众号服务配置平台用户手册.docx">点击下载,微信公众号服务配置平台用户手册.docx
                </a>
            </h3>
            <h1>
                一、首次使用需要打开“系统配置”界面，配置好公众号的参数信息。

            </h1>
            <p>
                请使用运营公众号账号，前往登录https://mp.weixin.qq.com，获取响应的参数值。
            </p>
            <p style="color: red">
                必须设置测试公众号信息样例如下：
                appId：wxc92ee423ec60040e
                appSecret：18eba8df8323f26758f0cba569093af9
                Token：qbtest
            </p>
            <p>EncodingAESKey参数为消息加密密钥，在开启安全模式的情况下需要设置该值，并且需要微信公众号运营平台上的保持一致</p>
            <h2>
                二、系统在消息加密模式下运行如果出现下面的异常：
            </h2>
            <h3>
                <a style="color: red" href="加密模式如果报错解决方案.zip">点击下载，加密模式如果报错解决方案</a>
            </h3>
            <p>请替换%JAVA_HOME%或者JRE安装目录下jre\lib\security中的local_policy.jar和US_export_policy.jar
                附件三个压缩下分别对应JDK6,7,8，请按照系统安装的版本进行替换。
            </p>
            <p style="color: red"> java.security.InvalidKeyException: Illegal key size</p>
            <p style="color: red">at javax.crypto.Cipher.checkCryptoPerm(Cipher.java:1039)</p>
            <p style="color: red">at javax.crypto.Cipher.implInit(Cipher.java:805)</p>
            <p style="color: red">at javax.crypto.Cipher.chooseProvider(Cipher.java:864)</p>
            <p style="color: red">at javax.crypto.Cipher.init(Cipher.java:1396)</p>
            <p style="color: red">at javax.crypto.Cipher.init(Cipher.java:1327)</p>
            </p>
        </div>
    </div>
</div>
<div id="mm" class="easyui-menu" style="width:150px;">
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">全部关闭</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-exit">退出</div>
</div>
<script type="text/javascript">
    function setWeinXinHao(test_weixin_hao) {
        if(!test_weixin_hao) return;
        $.post("<%=request.getContextPath()%>/setWeinXinHao",{"test_weixin_hao":test_weixin_hao},function () {
            $.messager.alert('系统提示', '设置微信号成功', 'info');
        });
    }
</script>
</body>
</html>