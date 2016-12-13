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
          href="<%=request.getContextPath()%>/resources/material/page_message2a0043.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/pagination218878.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/processor_bar.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/mass_send29ab02.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/dropdown2f12f7.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/messageManage.js"></script>
</head>
<body>
<div id="js_container_box" class="container_box cell_layout side_l">
    <div class="col_main">
        <div class="main_hd">
            <div class="title_tab" id="topTab">
                <ul class="tab_navs title_tab" data-index="0">


                    <li data-index="0" class="tab_nav first js_top <c:if test="${isFavorite == 0}">selected</c:if>" data-id="total"><a
                            href="<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=0&isFavorite=0&count=20&pageNow=1">全部消息</a>
                    </li>


                    <li data-index="1" class="tab_nav  js_top <c:if test="${isFavorite == 1}">selected</c:if>" data-id="star"><a
                            href="<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=0&isFavorite=1&count=20&pageNow=1">已收藏的消息</a>
                    </li>


                </ul>
            </div>
            <div class="extra_info search_bar" id="searchBar"><span class="frm_input_box search with_del append ">
    <a class="del_btn jsSearchInputClose" href="javascript:" style="display:none">
        <i class="icon_search_del"></i>&nbsp;
    </a>
    <a href="javascript:" class="frm_input_append jsSearchInputBt">
    	<i class="icon16_common search_gray">搜索</i>&nbsp;
    </a>
    <input type="text" value="${content}" class="frm_input jsSearchInput" placeholder="消息内容">
</span></div>
        </div>
        <div class="main_bd">
            <%--<div class="filter_bar">
                <div class="dropdown_wrp dropdown_menu" id="dayselect">
                    <a href="javascript:;" class="btn dropdown_switch jsDropdownBt"><label
                        class="jsBtLabel">最近五天</label><i class="arrow"></i></a>
                    <div class="dropdown_data_container jsDropdownList" style="display: none;">
                        <ul class="dropdown_data_list">


                            <li class="dropdown_data_item ">
                                <a onclick="return false;" href="javascript:;" class="jsDropdownItem" data-value="7"
                                   data-index="0" data-name="最近五天">最近五天</a>
                            </li>

                            <li class="dropdown_data_item ">
                                <a onclick="return false;" href="javascript:;" class="jsDropdownItem" data-value="0"
                                   data-index="1" data-name="今天">今天</a>
                            </li>

                            <li class="dropdown_data_item ">
                                <a onclick="return false;" href="javascript:;" class="jsDropdownItem" data-value="1"
                                   data-index="2" data-name="昨天">昨天</a>
                            </li>

                            <li class="dropdown_data_item ">
                                <a onclick="return false;" href="javascript:;" class="jsDropdownItem" data-value="2"
                                   data-index="3" data-name="前天">前天</a>
                            </li>

                            <li class="dropdown_data_item ">
                                <a onclick="return false;" href="javascript:;" class="jsDropdownItem" data-value="3"
                                   data-index="4" data-name="更早">更早</a>
                            </li>


                        </ul>
                    </div>
                </div>
            </div>--%>
            <div class="sub_title_bar white">
                <div class="info">
                    <c:if test="${isFavorite == 0}">
                        <h3 id="page_title">全部消息<span>(${total}条)</span></h3>
                        <label class="frm_checkbox_label" style="">
                            <i class="icon_checkbox <c:if test="${isKeyword == 1}">selected</c:if>"></i>
                            <input type="checkbox" class="frm_checkbox" data-label="不提醒">隐藏关键词消息</label>
                    </c:if>

                    <c:if test="${isFavorite == 1}">
                        <h3 id="page_title">已收藏的消息<span>(${total}条)</span></h3>
                    </c:if>
                </div>
            </div>
            <div id="newMsgTip" style="display:none;" class="msg_box">
                <div class="inner"><a
                        href="/cgi-bin/message?t=message/list&amp;token=1930543951&amp;count=20&amp;day=7"><span
                        id="newMsgNum"></span>条新消息，点击查看</a></div>
            </div>
            <ul class="message_list" id="listContainer">
                <c:forEach items="${inMessageList}" var="inMessage" varStatus="status">
                    <li class="message_item " id="msgListItem425760135" data-id="425760135">

                        <div class="message_opr">

                            <c:if test="${inMessage.isFavorite == 0}">
                            <a href="javascript:;" class="js_star icon18_common star_gray" action="" data-id="${inMessage.msgId}"
                               starred="" title="收藏消息" data-favorite="0">收藏消息</a>
                            </c:if>
                            <c:if test="${inMessage.isFavorite == 1}">
                            <a href="javascript:;" class="js_star icon18_common star_orange" action="" data-id="${inMessage.msgId}"
                               starred="" title="取消收藏" data-favorite="1">取消收藏</a>

                            </c:if>
                           <%-- <a href="javascript:;" class="js_save icon18_common save_gray" idx="425760135" data-type="2"
                               title="保存为素材">保存为素材</a>


                            <a href="/cgi-bin/downloadfile?msgid=425760135&amp;source=&amp;token=1930543951"
                               class="icon18_common download_gray" target="_blank" idx="425760135" title="下载">下载</a>--%>

                            <a href="javascript:;" data-id="425760135" data-tofakeid="o5FaswEuv-7EUG3AHcwDGRU_VPhw"
                               class="icon18_common reply_gray js_reply" title="快捷回复">快捷回复</a>
                        </div>

                        <div class="message_info">
                            <div class="message_status">
                                <c:if test="${inMessage.hasReply == 1}">
                                    <em class="tips">已回复</em>
                                </c:if>
                            </div>
                            <div class="message_time">${inMessage.createTime}</div>
                            <div class="user_info">

                                <a href="javascript:;"
                                   target="_blank" data-fakeid="o5FaswEuv-7EUG3AHcwDGRU_VPhw" data-id="425760135"
                                   class="remark_name">${inMessage.nickname}</a>

                                <c:if test="${inMessage.remark != null && inMessage.remark != ''}">
                                <span class="nickname" data-fakeid=""
                                      data-id="425760135">(${inMessage.remark})</span>
                                </c:if>


                                <a href="javascript:;" class="icon14_common edit_gray js_changeRemark"
                                   data-fakeid="o5FaswEuv-7EUG3AHcwDGRU_VPhw" title="修改备注" style="display:none;"></a>


                                <a target="_blank"
                                   href="javascript:;"
                                   class="avatar" data-fakeid="o5FaswEuv-7EUG3AHcwDGRU_VPhw" data-id="425760135">
                                    <img name="msgImg" style="width: 48px;height: 48px" src="${inMessage.headimgurl}"
                                         data-fakeid="${inMessage.openid}">
                                </a>

                            </div>
                        </div>

                        <div class="message_content ">
                            <div id="wxMsg425760135" data-id="425760135" class="wxMsg ">
                                <c:if test="${inMessage.content != ''}">${inMessage.content}</c:if>
                                <c:if test="${inMessage.content == ''}">
                                    <div class="appmsgSendedItem simple_img">
                                        <a class="title_wrp"
                                           href="javascript:;"
                                           target="_blank">
                                            <img name="msgImg" style="width: 80px;height: 80px" class="icon" src="${inMessage.media_id}">
                                            <span class="title">[图片]</span>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>


                        <div id="quickReplyBox425760135" class="js_quick_reply_box quick_reply_box">
                            <div class="emoion_editor_wrp js_editor msg_sender"></div>
                            <p class="frm_msg fail js_warn">请勿添加其他公众号的主页链接</p>
                            <div class="verifyCode"></div>
                            <p class="quick_reply_box_tool_bar">
                <span class="btn btn_primary btn_input" data-id="425760135">
                    <button class="js_reply_OK" data-id="425760135"
                            data-fakeid="${inMessage.openid}">发送</button>
                </span><a onclick="javascript:$(this).parent().parent().hide().children().eq(0).empty()" class="js_reply_pickup btn btn_default pickup" data-id="425760135" href="javascript:;">收起</a>
                            </p>`
                        </div>

                    </li>
                </c:forEach>
            </ul>
            <div class="tool_area">
                <div class="pagination_wrp pageNavigator">
                    <input type="hidden" id="isKeyword" value="${isKeyword}"/>
                    <input type="hidden" id="isFavorite" value="${isFavorite}"/>
                    <div class="pagination" id="wxPagebar_1475479797537">
                          <span class="page_nav_area">
                           <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
                           <c:if test="${pageNow <= pages && pageNow > 1}">
                               <a href="<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=${isKeyword}&isFavorite=${isFavorite}&count=20&pageNow=${pageNow-1}<c:if test="${content != null}">&content=${content}</c:if>"
                                  class="btn page_prev"><i class="arrow"></i></a>
                           </c:if>
                              <span class="page_num">
                                  <label>${pageNow}</label>
                                   <span class="num_gap">/</span>
                                   <label>${pages}</label>
                            </span>
                           <c:if test="${pageNow < pages}">
                               <a href="<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=${isKeyword}&isFavorite=${isFavorite}&count=20&pageNow=${pageNow+1}<c:if test="${content != null}">&content=${content}</c:if>"
                                  class="btn page_next"><i class="arrow"></i></a>
                           </c:if>

                           <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>
                          </span>

                          <span class="goto_area">
                           <input type="text" id="goPage">
                           <a href="javascript:void(0);" class="btn page_go"
                              onclick="javascript:if(!document.getElementById('goPage').value || document.getElementById('goPage').value < 1 || document.getElementById('goPage').value >${pages})return;document.location.href='<%=request.getContextPath()%>/wx/messageManage/init?isKeyword=${isKeyword}&isFavorite=${isFavorite}&count=20&pageNow='+document.getElementById('goPage').value +'<c:if test="${content != null}">&content=${content}</c:if>'">跳转</a>
                           </span>
                    </div>
                </div>
            </div>
            <div id="searchMore" href="javascript:;" class="search_more ">
                <div class="msg_box">
                    <div class="inner"><a href="javascript:void(0);" id="moreTxt">查看更多</a></div>
                </div>
                <i id="moreLoading" class="icon_loading_small white"></i></div>
        </div>
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
</body>