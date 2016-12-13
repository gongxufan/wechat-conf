<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>素材管理中心</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/layout_head2880f5.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/base2fde18.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/lib2f613f.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/material/media_list2f12f7.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/media2f624f.css">
    <style>
        html {overflow-x: hidden}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material.js"></script>
</head>
<body class="zh_CN screen_small">
<div id="body" class="body page_media_list page_appmsg_list">
    <div id="js_container_box" class="container_box cell_layout side_l">
        <div class="col_main">
            <div class="main_hd">
                <div class="title_tab" id="topTab">
                    <ul class="tab_navs title_tab" data-index="0">
                        <li data-index="0" class="tab_nav first js_top selected" data-id="media10"><a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=1&count=6&view=card">图文消息</a>
                        </li>


                        <li data-index="1" class="tab_nav  js_top" data-id="media2"><a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?type=IMAGE&pageNow=1&count=12&group_id=1">图片</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main_bd" id="js_main">
                <!--<div id="js_preview_tips" class="page_msg mini media_list_global_msg invalid_msg" style="">
                    <div class="inner group"><span class="msg_icon_wrp"><i class="icon_msg_mini info"></i></span>
                        <div class="msg_content">素材库文章预览功能已升级，每次预览后的链接将在短期内失效；链接失效规则仅适用于预览情况，不影响其他功能引用素材库文章。</div>
                    </div>
                </div>-->
                <div class="page_msg mini media_list_global_msg" style="display:none" id="js_pic_clear">
                    <div class="inner group"><span class="msg_icon_wrp"><i class="icon_msg_mini info"></i></span>
                        <div class="msg_content">当前图片数量过多，为了不影响正常使用，建议进行<a href="javascript:;"
                                                                           id="js_pic_clear_a">批量清理</a>。
                        </div>
                    </div>
                </div>
                <div id="js_forbit_warn"></div>
                <!--  <div class="search_bar" id="searchDiv"><span class="frm_input_box search with_del append ">
                      <a class="del_btn jsSearchInputClose" href="javascript:" style="display:none">
                          <i class="icon_search_del"></i>&nbsp;
                      </a>
                      <a href="javascript:" class="frm_input_append jsSearchInputBt">
                          <i class="icon16_common search_gray">搜索</i>&nbsp;
                      </a>
                      <input type="text" value="" class="frm_input jsSearchInput" placeholder="标题/作者/摘要">
                      </span>
                  </div>-->
                <div class="sub_title_bar">
                    <div class="info"><h3 id="page_title"><span id="query_tips">图文消息</span>(共<span
                            id="js_count">${total_count}</span>条) </h3>
                        <div class="rank_style">
                            <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=1&count=6&view=card" class="btn_card_rank " id="js_cardview" alt="卡片式">卡片式</a>
                            <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=1&count=12&view=list" class="btn_list_rank current" id="js_listview" alt="列表式">列表式</a></div>
                        </div>
                    </div>
                    <a target="_blank" class="btn btn_add btn_primary r btn_new"
                       href="javascript:;" id="addNews">
                        <i class="icon14_common add_white"></i>新建图文消息 </a></div>
                <div class="appmsg_list" id="js_card" style="display: none"></div>
                <div class="appmsg_list_v" id="js_list" style="display:block;">
                    <ul class="inner_list_v">
                    <c:forEach items="${lists}" var="list">
                        <li cover="${list[0].thumb_url_base64}" class="appmsg_item_v js_appmsgitem" id="${list[0].media_id}" title="${list[0].title}">
                            <div class="inner">
                                <div class="content">
                                    <img src="${list[0].thumb_url_base64}" class="content_cover">
                                    <div class="content_abstract">
                                        <c:forEach items="${list}" var="l" varStatus="status">
                                            <c:if test="${fn:length(list) == 1}">
                                                <p class="js_title" data-msgid="100000033" data-idx="0"><a target="_blank" class="abstract_tit" href="${l.url}">${l.title}</a></p>
                                            </c:if>
                                            <c:if test="${fn:length(list) > 1}">
                                                <p class="js_title" data-msgid="100000033" data-idx="0"><a target="_blank" class="abstract_tit" href="${l.url}">${status.index+1}.${l.title}</a></p>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="opr">
                                    <ul>
                                        <li name="editNews" media_id="${list[0].media_id}">
                                            <a href="javascript:;">编辑</a>

                                        </li>
                                        <li name="deleteNews" media_id="${list[0].media_id}">
                                            <a href="javascript:;" class="js_del">删除</a>

                                        </li>
                                    </ul>
                                </div>
                                <div class="date">
                                    <p>13:43</p>
                                </div>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>

                </div>
                <div class="empty_tips dn" id="js_empty"><p>暂无素材</p></div>
                <div class="empty_tips dn" id="js_search_empty">没有搜索结果，请重新输入关键字或者查看<a href="javascript:;" id="reload">全部图文消息</a>
                </div>
                <div class="tool_area">
                    <div class="pagination_wrp pageNavigator" id="js_pagebar" style="display: none;">
                        <div class="pagination" id="wxPagebar_1472093652681">
                            <span class="page_nav_area">
                                <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
                                <c:if test="${pageNow <= pages && pageNow > 1}">
                                    <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=${pageNow-1}&count=12&view=card" class="btn page_prev"><i class="arrow"></i></a>
                                </c:if>
                                <span class="page_num">
                                    <label>${pageNow}</label>
                                    <span class="num_gap">/</span>
                                    <label>${pages}</label>
                                </span>
                                <c:if test="${pageNow < pages}">
                                    <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=${pageNow+1}&count=12&view=card" class="btn page_next"><i class="arrow"></i></a>
                                </c:if>
                                <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="popover pos_center" style="display: none;" id="popoverDel">
    <input type="hidden" id="materialId"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">确定删除此素材？</div>
        <div class="popover_bar"><a id="confirmDel" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a id="cancleDel" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>
<div  class="JS_TIPS page_tips error" id="wxTips" style="display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>
</body>
</html>