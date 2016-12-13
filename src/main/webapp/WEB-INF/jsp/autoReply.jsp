<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>微信公众平台</title>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/layout_head2880f5.css">

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/base2fde18.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/menu/lib2f613f.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/advanced_reply_common27d7ed.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/advanced_reply_keywords2e42db.css"/>

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/img_preview29f4d5.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/msg_sender2968da.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/processor_bar.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/autoReply.js"></script>
</head>
<body>
<div id="js_container_box" class="container_box cell_layout side_l">
<div class="col_main">
    <div class="main_bd">
        <c:if test="${autoReply == '0'}">
        <div class="highlight_box icon_wrap icon_small border" id="div_start">
            <div class="opr"><a href="javascript:;" class="btn btn_primary" id="btn_start">开启</a></div>
            <span class="icon lock">                    </span>        <h4 class="title">未开启自动回复设置</h4>
            <p class="desc"> 通过编辑内容或关键词规则，快速进行自动回复设置。如具备开发能力，可更灵活地使用该功能。></p></div>
        </c:if>
        <c:if test="${autoReply == '1'}">
        <div class="highlight_box icon_wrap icon_small border" id="div_stop" >
            <div class="opr"><a href="javascript:;" class="btn btn_warn" id="btn_stop">停用</a></div>
            <span class="icon unlock">                    </span>        <h4 class="title">已开启自动回复设置</h4>
            <p class="desc"> 通过编辑内容或关键词规则，快速进行自动回复设置。如具备开发能力，可更灵活地使用该功能。></p></div>
        <div class="page_msg mini plugin_update_tips js_authorized" style="display:none">
            <div class="inner group"><span class="msg_icon_wrp"><i class="icon_msg_mini info"></i></span>
                <div class="msg_content"><p>你已授权给<span class="js_auth_name"></span>帮助你运营公众号，点击管理<a
                        href="/cgi-bin/component_unauthorize?action=list&amp;t=service/auth_plugins&amp;token=1397870750&amp;lang=zh_CN">已授权的第三方平台</a>
                </p></div>
            </div>
        </div>
        <div class="content_wrap" id="div_replyContent" style="padding: 30px">
            <div class="global_mod mt_layout reply_tab_wrp">
                <div class="section_tab">
                    <ul class="tab_navs">
                        <li class="tab_nav  <c:if test="${replyType == 1}">selected</c:if>"><a
                                href="<%=request.getContextPath()%>/wx/autoReply/init?replyType=1">被添加自动回复</a>
                        </li>
                        <li class="tab_nav <c:if test="${replyType == 2}">selected</c:if>"><a
                                href="<%=request.getContextPath()%>/wx/autoReply/init?replyType=2">消息自动回复</a>
                        </li>
                        <li class="tab_nav no_extra <c:if test="${replyType == 3}">selected</c:if>"><a
                                href="<%=request.getContextPath()%>/wx/autoReply/init?replyType=3">关键词自动回复</a>
                        </li>
                    </ul>
                </div>
                <div class="global_extra"><span class="reply_title_tips mini_tips icon_after"><a
                        href="http://kf.qq.com/info/80935.html" target="_blank">公众平台如何设置被添加自动回复 <i
                        class="icon_mini_tips document_link"></i></a></span></div>
            </div>
            <input id="replyType" type="hidden" value="${replyType}"/>
            <input id="msgId" type="hidden" value="${replyMessage.msgId}"/>
            <input id="currKeywordsId" type="hidden" value="Js_replyList_1"/>
            <input id="regularId" type="hidden"/>
            <!--被关注和自动回复类型-->
            <c:if test="${replyType == 1 || replyType == 2}">
            <div class="msg_sender" id="js_msgSender">
                <script type="text/javascript">
                    var replyType = ${replyType};
                    var replyMessage = {};
                    replyMessage.msgId= "${replyMessage.msgId}";
                    replyMessage.msgType= "${replyMessage.msgType}";
                    replyMessage.content= "${replyMessage.content}";
                    replyMessage.media_id = "${replyMessage.media_id}";
                    //加载消息发送界面
                    $("#js_msgSender").load(serverContext+"/wx/groupMessage/msgSender",function () {
                        $.get(serverContext+"/resources/js/msgSender.js",function () {
                            //图文消息不显示
                            $("#sendImgtext").hide();
                            $("#imgtextTab").hide();
                            //初始状态显示文字编辑
                            if(replyMessage.msgId == ""){
                                $("#sendText").show();
                                $("#textTab").trigger("click");
                            }
                            //被关注和消息自动回复，只支持文字和图片
                            if(replyMessage.msgId != "" &&　replyMessage.media_id == ""){//文字消息
                                $("#textTab").trigger("click");
                                //隐藏图片，显示文字消息
                                $("#sendImage").hide();
                                $("#sendText").show();
                                var content = replyMessage.content;
                                $(".js_emotion_i").each(function (i,item) {
                                    var emoj = $(item).attr("data-title");
                                    var src = $(item).attr("data-gifurl");
                                    content = content.replace(new RegExp("/"+emoj,"gm"),'<img title="'+emoj+'" src="'+src+'"/>');
                                });
                                $(".js_editorArea").html(content);
                            }
                            if(replyMessage.msgId != "" &&　replyMessage.content == ""){//图片消息
                                //隐藏文字，显示图片
                                $("#sendImage").show();
                                $("#sendText").hide();
                                $("#selectImg").hide();
                                $("#imgTab").trigger("click");
                                $("#imgTab").attr("media_id", replyMessage.media_id);
                                //加载图片
                                $("#showImage").show();
                                autoReply.getImgViaMediaId(replyMessage.media_id,$("#imageMsg"));
                            }
                        });
                    });
                </script>
            </div>
            <div class="tool_bar">
                    <span id="js_save" class="btn btn_primary btn_input"><button>保存</button></span> <span
            id="js_del" class="btn btn_default btn_input <c:if test="${replyMessage.msgId == null}">btn_disabled</c:if>"><button>删除回复</button></span>
            </div>
            </c:if>
            <!--关键字自动回复-->
            <c:if test="${replyType == 3}">
                <div class="btn_wrp">
                    <a href="javascript:void(0);" class="btn btn_add btn_primary" id="Js_rule_add" data-status="not">
                        <i class="icon14_common add_white"></i>添加规则
                    </a>
                </div>

                <ul id="Js_ruleList" class="keywords_rule_list">
                    <c:forEach items="${regularMessageList}" var="regular" varStatus="status">
                        <li class="keywords_rule_item" id="regular_${regular.regularId}">

                            <div class="keywords_rule_hd no_extra dropdown_area Js_detail_switch dropdown_closed" data-id="425395223" data-reply="empty" style="-webkit-transition: all, 1s;">
                                <div class="info">
                                    <em class="keywords_rule_num">规则${status.index+1}:</em>
                                    <strong class="keywords_rule_name">${regular.regularName}</strong>
                                </div>
                                <div class="opr">
                                    &nbsp;
                                    <a href="javascript:void(0);" class="icon_dropdown_switch"><i class="arrow arrow_up"></i><i class="arrow arrow_down"></i></a>
                                </div>
                            </div>


                            <div class="keywords_rule_bd keywords_rule_overview">
                                <div class="keywords_info keywords">
                                    <strong class="keywords_info_title">关键词</strong>
                                    <div class="keywords_info_detail">
                                        <ul class="overview_keywords_list">
                                            <c:forEach items="${regular.keywordList}" var="keyword">
                                                 <li data-id="${keyword.keywordId}"><em class="keywords_name">${keyword.keywordName}</em></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="keywords_info reply">
                                    <strong class="keywords_info_title">回复</strong>
                                    <div class="keywords_info_detail">
                                        <p class="reply_info">
                                            <em name="totalCnt" class="num">${fn:length(regular.messageList)}</em>条（
                                            <em name="txtCnt" data-type="1" class="num Js_reply_cnt2">${regular.totalTextMsg}</em>条文字，
                                            <em name="imgCnt" data-type="2" class="num Js_reply_cnt2">${regular.totalImgMsg}</em>条图片，
                                          <%--  <em data-type="3" class="num Js_reply_cnt2">0</em>条语音，
                                            <em data-type="4" class="num Js_reply_cnt2">0</em>条视频，--%>
                                            <em name="txtImgCnt" data-type="5" class="num Js_reply_cnt2">${regular.totalTxtImgMsg}</em>条图文）

                                        </p>
                                    </div>
                                </div>
                                <div id="Js_replyAllOverview_425395223" class="dn">发送全部回复</div>
                            </div>


                            <div class="keywords_rule_bd keywords_rule_detail">

                                <div class="rule_name_area">
                                    <div class="frm_control_group">
                                        <label  class="frm_label">规则名</label>
                                        <div class="frm_controls">
                                            <span class="frm_input_box"><input type="text" class="frm_input" id="Js_ruleName_${regular.regularId}" value="${regular.regularName}"></span>
                                            <p class="frm_tips">规则名最多60个字</p>
                                        </div>
                                    </div>
                                </div>


                                <div class="keywords_tap keywords  no_data">
                                    <div class="keywords_tap_hd">
                                        <div class="info">
                                            <h4>关键字</h4>
                                        </div>
                                        <div class="opr">
                                            <a href="javascript:;" data-id="${status.index+1}" class="Js_keyword_add">添加关键字</a>
                                        </div>
                                    </div>
                                    <div class="keywords_tap_bd">
                                        <ul name="Js_keywordList" class="keywords_list" id="Js_keywordList_${status.index+1}">
                                            <c:forEach items="${regular.keywordList}" var="keyword2" varStatus="status2">
                                                <li data-id="${keyword2.keywordId}"  data-index="${status2.index+1}">
                                                    <div class="desc">
                                                        <strong class="title Js_keyword_val">${keyword2.keywordName}</strong>
                                                    </div>
                                                    <div class="opr">
                                                        <a href="javascript:;" class="keywords_mode_switch Js_keyword_mode" data-mode="${keyword2.mode}">
                                                            <c:if test="${keyword2.mode == 0}">未全匹配</c:if>
                                                            <c:if test="${keyword2.mode == 1}">完全匹配</c:if>
                                                        </a>
                                                        <a href="javascript:;" data-index="${status2.index+1}" class="icon14_common edit_gray Js_keyword_edit">编辑</a>
                                                        <a href="javascript:;" class="icon14_common del_gray Js_keyword_del">删除</a>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>


                                <div class="keywords_tap reply no_data">
                                    <div class="keywords_tap_hd">
                                        <div class="info">
                                            <h4>回复</h4>
                                        </div>
                                        <div class="opr">
                                            <label id="checkAll_${regular.regularId}" for="Js_replyAll_425395223" class="frm_checkbox_label <c:if test="${regular.replyAll == 1}">selected</c:if>">
                                                <i  class="icon_checkbox"></i>
                                                <input id="Js_replyAll_425395223" type="checkbox" class="frm_checkbox Js_reply_all">
                                                回复全部                        </label>
                                        </div>
                                    </div>
                                    <div class="keywords_tap_bd">

                                        <ul class="media_type_list">
                                            <li  data-regularid="${regular.regularId}" data-id="${status.index+1}" class="tab_text" data-tooltip="文字"><a href="javascript:;" data-type="1" data-id="425395223" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                                            <li  data-regularid="${regular.regularId}" data-id="${status.index+1}" class="tab_img" data-tooltip="图片"><a href="javascript:;" data-type="2" data-id="425395223" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                                           <%-- <li data-id="${status.index+1}" class="tab_audio" data-tooltip="语音"><a href="javascript:;" data-type="3" data-id="425395223" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                                            <li data-id="${status.index+1}" class="tab_video" data-tooltip="视频"><a href="javascript:;" data-type="7" data-id="425395223" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>--%>
                                            <li  data-regularid="${regular.regularId}" data-id="${status.index+1}" class="tab_appmsg" data-tooltip="图文"><a href="javascript:;" data-type="5" data-id="425395223" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                                        </ul>


                                        <ul name="Js_replyList" class="keywords_list" id="Js_replyList_${status.index+1}">

                                            <c:forEach items="${regular.messageList}" var="message" varStatus="status3">
                                                <!--文字消息-->
                                                <c:if test="${message.msgType == 1}">
                                                    <li  data-type="${message.msgType}" data-index="${status3.index+1}"  style="z-index: 0">
                                                        <div class="desc">
                                                            <div name="content" class="media_content Js_media_content" id="Js_mediaContent_425395223_0" data-index="${status3.index+1}" data-type="1">${message.content}</div>
                                                        </div>
                                                        <div class="opr">

                                                            <a data-index="${status3.index+1}"  href="javascript:;" class="icon14_common edit_gray  Js_reply_edit">编辑</a>

                                                            <a href="javascript:;" data-id="425395223" class="icon14_common del_gray Js_reply_del">删除</a>
                                                        </div>
                                                    </li>
                                                </c:if>
                                                <!--图片消息-->
                                                <c:if test="${message.msgType == 2}">
                                                    <li  data-type="${message.msgType}" data-mediaid="${message.media_id}" style="z-index: 0">
                                                        <div class="desc">
                                                            <div class="media_content Js_media_content" id="Js_mediaContent_425395223_1" data-index="1" data-type="2"><div class="appmsgSendedItem simple_img">
                                                                <a class="title_wrp" href="" target="_blank">
                                                                    <img media_id="${message.media_id}" class="icon" src="">
                                                                    <span class="title">[图片]</span>
                                                                </a>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        <div class="opr">

                                                            <a href="javascript:;" data-id="425395223" class="icon14_common del_gray Js_reply_del">删除</a>
                                                        </div>
                                                    </li>

                                                </c:if>

                                                <!--图文消息-->
                                                <c:if test="${message.msgType == 3}">
                                                    <c:forEach items="${regular.newsList}" var="news">
                                                        <c:if test="${fn:contains(message.media_id, news.thumb_media_id)}">
                                                            <li data-type="${message.msgType}" data-mediaid="${message.media_id}" style="z-index: 0">
                                                                <div class="desc">
                                                                    <div class="media_content Js_media_content" id="Js_mediaContent_425395223_2" data-index="2" data-type="5"><div class="appmsgSendedItem simple_appmsg">
                                                                        <a class="title_wrp" href="${news.url}" target="_blank" title="预览文章" data-msgid="100000025" data-idx="0">
                                                                            <img name="thumbUrl" class="icon icon_lh" src="${news.thumb_url}"/>
                                                                            <span class="title">[图文消息]${news.title}</span>
                                                                        </a>
                                                                        <p class="desc" title="预览文章" data-msgid="100000025" data-idx="0"><a href="${news.url}" class="appmsg_desc" target="_blank">${news.digest}</a></p>
                                                                    </div>
                                                                    </div>
                                                                </div>
                                                                <div class="opr">
                                                                    <a href="javascript:;" data-id="425395223" class="icon14_common del_gray Js_reply_del">删除</a>
                                                                </div>
                                                            </li>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <p class="mini_tips warn dn js_warn profile_link_msg_global keywords">请勿添加其他公众号的主页链接</p>

                            </div>


                            <div class="keywords_rule_ft">

                                <p class="media_stat info">
                                    文字(<em name="txtCnt" data-type="1" class="num Js_reply_cnt">${regular.totalTextMsg}</em>)、
                                    图片(<em name="imgCnt" data-type="2" class="num Js_reply_cnt">${regular.totalImgMsg}</em>)、
                                   <%-- 语音(<em data-type="3" class="num Js_reply_cnt">0</em>)、
                                    视频(<em data-type="4" class="num Js_reply_cnt">0</em>)、  --%>
                                    图文(<em name="txtImgCnt" data-type="5" class="num Js_reply_cnt">${regular.totalTxtImgMsg}</em>)

                                </p>

                                <div class="opr">
                                    <a href="javascript:;" data-id="${regular.regularId}" class="btn btn_primary Js_rule_save">保存</a>
                                    <a href="javascript:;" name="delRegular" data-id="${regular.regularId}" class="btn btn_default Js_rule_del">删除</a>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                    <c:if test="${fn:length(regularMessageList) == 0}>">
                        <p class="empty_tips">暂无创建规则</p>
                    </c:if>

                </ul>
            </c:if>
        </div>
        </c:if>
    </div>
    <div class="dn" id="pop_desc"><p class="title">什么是临时会话？</p>
        <p class="desc">微信用户在没有关注该公众号的前提下，临时发起的会话。</p></div>
</div>
</div>

<div id="selectNewDiv" class="dialog_wrp media align_edge ui-draggable" style="z-index: 9999;width: 960px;height: 500px; display: none;">
    <div class="dialog" style="overflow-y: auto">
        <div class="dialog_hd">
            <h3>选择图文消息</h3>
            <a href="javascript:;" onclick="$('#selectNewDiv').hide();$('#mask').hide();" class="icon16_opr closed pop_closed" >关闭</a>
        </div>
        <div class="dialog_bd" id="loadNews">
            loading...
        </div>
    </div>
</div>
<div id="selectTextDiv" class="dialog_wrp media align_edge ui-draggable" style="z-index: 9999;width: 960px;height: 500px; display: none;">
    <div class="dialog" style="overflow-y: auto">
        <div class="dialog_hd">
            <h3 id="textDivTitle"></h3>
            <a href="javascript:;" onclick="$('#selectTextDiv').hide();$('#mask').hide();" class="icon16_opr closed pop_closed" >关闭</a>
        </div>
        <div class="dialog_bd" id="loadText">
            loading...
        </div>
    </div>
</div>

<div id="selectImageDiv" class="dialog_wrp img_dialog_wrp ui-draggable" style="z-index: 9999;width: 846px; height:500px;display: none;">
    <div class="dialog">
        <div class="dialog_hd">
            <h3>选择图片</h3>
            <a href="javascript:;" onclick="$('#selectImageDiv').hide();$('#mask').hide();" class="icon16_opr closed pop_closed" >关闭</a>
        </div>
        <div class="dialog_bd" id="loadImages">
            loading...
        </div>
    </div>
</div>
<div id="mask" class="mask ui-draggable" style="display: none;"><iframe frameborder="0" style="filter:progid:DXImageTransform.Microsoft.Alpha(opacity:0.5);position:absolute;top:0px;left:0px;width:100%;height:100%;" src="about:blank"></iframe></div>
<div  class="JS_TIPS page_tips error" id="wxTips" style="display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>

<div class="popover  pos_center" style="display: none;" id="confirmDel">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">删除后，关注该公众号的用户将不再接收该回复，确定删除？</div>
        <div class="popover_bar"><a id="confirmDelMsg" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;
            <a id="cancleDelMsg" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>
<div class="popover  pos_center" style="display: none;" id="deleteRegular">
    <input type="hidden" id="regularIdPop"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">确定删除该规则？</div>
        <div class="popover_bar"><a id="confirmDelRegular" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;
            <a id="cancleDelRegular" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>
</body>