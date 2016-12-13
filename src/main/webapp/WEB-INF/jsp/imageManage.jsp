<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/material/media_list_img2f613f.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/material/processor_bar218878.css">
    <style>
        html {
            overflow-x: hidden
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/image.js"></script>
</head>
<body class="zh_CN screen_small">
<div class="col_main">
    <div class="main_hd">
        <div class="title_tab" id="topTab">
            <ul class="tab_navs title_tab" data-index="0">
                <li data-index="0" class="tab_nav first js_top"><a
                        href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=NEWS&pageNow=1&count=12&view=card">图文消息</a>
                </li>

                <li data-index="1" class="tab_nav  js_top selected"><a
                        href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?type=IMAGE&pageNow=1&count=12">图片</a>
                </li>

            </ul>
        </div>
    </div>
    <div class="main_bd">
        <div class="img_pick_panel">
            <div class="inner_container_box side_r cell_layout">
                <div class="inner_main">
                    <div class="bd">
                        <div class="media_list">
                            <div class="global_mod float_layout"><p class="global_info"><span
                                    class="group_name global_info_ele" id="js_currentgroup">${groupName}</span>
                               <c:if test="${group_id != '1'}">
                                   <a groupname="${groupName}" href="javascript:;" class="global_info_ele js_popover" id="js_rename">重命名</a>
                                   <a href="javascript:;" class="global_info_ele js_popover" id="js_delgroup">删除分组</a>
                               </c:if>
                            </p>
                                <div class="global_extra">
                                    <div class="upload_box align_right r">
                                        <span class="upload_area webuploader-container">
                                            <a data-gid="1" class="btn btn_primary webuploader-pick" id="js_upload">本地上传</a>
                                            <ul style="display:none" class="upload_file_box"></ul>
                                            <div style="position: absolute; top: 0px; left: 0px; width: 106px; height: 32px; overflow: hidden; bottom: auto; right: auto;" id="rt_rt_1arckl6ar1j7v1kd76esg45j5r1">
                                                <input onchange="imageManage.uploadFile('${group_id}')" id="uploadImg" type="file" style="display: none;" accept="image/*" multiple="multiple">
                                                <label id="uploadDiv" style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                                            </div>
                                           </span>
                                    </div>
                                    <div class="mini_tips weak_text icon_after img_water_tips r">
                                        大小不超过5M<span id="js_water">，已开启图片水印</span>
                                        <i class="icon_msg_mini ask" id="js_water_tips"></i>
                                    </div>                                                            </div>
                            </div>
                            <div class="oper_group group">
                                <div class="frm_controls oper_ele l"><label id="js_all_label" class="frm_checkbox_label"
                                                                            for="js_all">
                                    <input id="js_all" type="checkbox" class="frm_checkbox" data-label=" 全选"><i
                                        class="icon_checkbox"></i><span class="lbl_content">&nbsp;全选</span></label>
                                </div>
                                <a id="js_batchmove" class="btn btn_default btn_disabled oper_ele l js_popover"
                                   href="javascript:;" title="移动分组">移动分组</a> <a title="删除" id="js_batchdel"
                                                                   class="btn btn_default btn_disabled oper_ele l js_popover"
                                                                   href="javascript:;">删除</a></div>
                            <div class="group img_pick" id="js_imglist">
                                <ul class="group">
                                    <c:forEach items="${imageList}" var="image">
                                    <li class="img_item js_imgitem">
                                        <div class="img_item_bd" cover="${image.url}">
                                            <span class="js_img_icon pic wxmImg Zoomin test cover"
                                                  src="${image.url}"
                                                  data-previewsrc="${image.url}">
                                                <img src="${image.url}" style="width: 160px;height: 170px"/>
                                            </span>

                                            <span class="check_content">
<label name="checkedInput" media_id="${image.media_id}" class="frm_checkbox_label" for="checkbox61"><input type="checkbox" class="frm_checkbox" data-label="${image.name}"
                                                          data-oristatus="0" id="checkbox61"><i
        class="icon_checkbox"></i><span name="${image.media_id}" class="lbl_content">${image.name}</span></label>
</span>
                                        </div>
                                        <div class="msg_card_ft">
                                            <ul class="grid_line msg_card_opr_list">
                                                <li class="grid_item size1of3 msg_card_opr_item">

                                                    <a data-id="${image.media_id}" class="js_edit js_tooltip js_popover" id="editImg"
                                                       name="${image.name}" href="javascript:;" title="编辑">
                    <span class="msg_card_opr_item_inner">
<i class="icon18_common edit_gray">编辑</i></span><span class="vm_box"></span>
                                                    </a>
                                                </li>

                                                <li class="grid_item size1of3 msg_card_opr_item">
                                                    <a id="moveImg" class="js_move js_tooltip js_popover" href="javascript:;"
                                                       title="移动分组" media_id="${image.media_id}">
<span class="msg_card_opr_item_inner">
<i class="icon18_common move_gray">移动分组</i>
</span>
                                                        <span class="vm_box"></span>
                                                    </a>
                                                </li>


                                                <li class="grid_item size1of3 no_extra msg_card_opr_item">
                                                    <a class="js_del js_tooltip js_popover" onclick="imageManage.deletImg('${image.media_id}','${image.group_id}')"
                                                       data-oristatus="0" href="javascript:;" title="删除">
                            <span class="msg_card_opr_item_inner">
                                <i class="icon18_common del_gray">删除</i>
                            </span>
                                                        <span class="vm_box"></span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="pagination_wrp pageNavigator" id="js_pagebar">
                                <div class="pagination" id="wxPagebar_1472199068822">
                                    <span class="page_nav_area">
                                        <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
                                         <c:if test="${pageNow <= pages && pageNow > 1}">
                                             <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?group_id=${group_id}&cols=3&type=IMAGE&pageNow=${pageNow-1}&count=12&view=card" class="btn page_prev"><i class="arrow"></i></a>
                                         </c:if>
                                            <span class="page_num">
                                                <label>${pageNow}</label>
                                                <span class="num_gap">/</span>
                                                <label>${pages}</label>
                                            </span>
                                        <c:if test="${pageNow < pages}">
                                            <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?group_id=${group_id}&cols=3&type=IMAGE&pageNow=${pageNow+1}&count=12&view=card" class="btn page_next"><i class="arrow"></i></a>
                                        </c:if>

                                        <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>
                                    </span>

                                                                    <span class="goto_area">
                                        <input type="text" id="goPage">
                                        <a href="javascript:void(0);" class="btn page_go" onclick="javascript:if(!document.getElementById('goPage').value || document.getElementById('goPage').value < 1 || document.getElementById('goPage').value >${pages})return;document.location.href='<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=IMAGE&pageNow='+document.getElementById('goPage').value+'&count=12&view=card';">跳转</a>
                                    </span>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="inner_side">
                    <div class="bd">
                        <div class="group_list">
                            <div class="inner_menu_box" id="js_group">
                                <dl class="inner_menu">
                                    <c:forEach items="${imageGroups}" var="group">
                                        <dd class="inner_menu_item <c:if test="${group_id == group.group_id}">selected</c:if> js_groupitem">
                                            <a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?group_id=${group.group_id}&cols=3&type=IMAGE&pageNow=1&count=12&view=card"
                                               class="inner_menu_link" title="${group.groupName}">
                                                <strong>${group.groupName}</strong><em class="num">(${group.count})</em>
                                            </a>
                                        </dd>
                                    </c:forEach>
                                </dl>
                            </div>
                            <div class="inner_menu_item"><a href="javascript:;" class="inner_menu_link js_popover"
                                                            id="js_creategroup"><i
                                    class="icon14_common add_gray">新建分组</i>新建分组</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="popover  pos_center" style="display: none" id="addGroup"  name="popOver">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent"><div class="popover_edit">
            <label for="" class="frm_label">创建分组</label>

            <div class="frm_controls">
        <span class="frm_input_box">
            <input type="text" class="frm_input js_name" value="" id="saveGroup">
        </span>

                <p class="frm_tips"></p>

                <p class="frm_msg fail">
                    <span class="frm_msg_content">填错了！！！！</span>
                </p>
            </div>
        </div></div>
        <!--#0001#-->

        <!--%0001%-->

        <div class="popover_bar"><a href="javascript:;" class="btn btn_primary jsPopoverBt" id="confirmGroup">确定</a>&nbsp;<a id="cancleGroup" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none;" id="renameGroup"  name="popOver">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent"><div class="popover_edit">
            <label for="" class="frm_label">编辑名称</label>

            <div class="frm_controls">
        <span class="frm_input_box">
            <input type="text" class="frm_input js_name" id="renameGroupaValue">
        </span>

                <p class="frm_tips"></p>

                <p class="frm_msg fail">
                    <span class="frm_msg_content">填错了！！！！</span>
                </p>
            </div>
        </div></div>
        <!--#0001#-->

        <!--%0001%-->

        <div class="popover_bar"><a groupId="${group_id}" id="confirmRename" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleRename">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none" id="deleteGroup"  name="popOver">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent"><p>仅删除分组，不删除图片，组内图片将自动归入未分组</p></div>
        <!--#0001#-->

        <!--%0001%-->

        <div class="popover_bar"><a groupId="${group_id}"  id="confimDelGroup" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a id="cancleDelGroup" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none" id="deleteImg"  name="popOver">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">
            <p class="tl">确定删除此素材吗？</p>
        </div>
       <input type="hidden" id="imgMediaId"/>
        <div class="popover_bar"><a onclick="imageManage.confirmDelImg()" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" onclick="imageManage.cancleDelImg()">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none;" id="renameImg"  name="popOver">
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent"><div class="popover_edit">
            <label for="" class="frm_label">编辑名称</label>

            <div class="frm_controls">
        <span class="frm_input_box">
            <input type="text" class="frm_input js_name" value="" data-id="" id="imgName">
            <input type="hidden" id="imgId"/>
        </span>

                <p class="frm_tips"></p>

                <p class="frm_msg fail">
                    <span class="frm_msg_content">填错了！！！！</span>
                </p>
            </div>
        </div></div>
        <div class="popover_bar"><a id="confirmRenameImg" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a id="cancleRenameImg" href="javascript:;" class="btn btn_default jsPopoverBt">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none;" id="moveGroup" name="popOver">
    <input type="hidden" id="moveMediaId"/>
    <input type="hidden" id="moveGroupId" value="${group_id}"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">
            <div class="no_group" <c:if test="${fn:length(imageGroups)>1}">style="display:none;"</c:if>>你还没有任何分组。</div>
            <div class="frm_control group_select">
                <c:forEach items="${imageGroups}" var="group">
                    <c:if test="${group.group_id != '1'}">
                <label class="frm_radio_label <c:if test="${group_id == group.group_id}">selected</c:if>"><input type="radio" class="frm_radio" value="${group.group_id}"><i class="icon_radio"></i><span class="lbl_content">${group.groupName}</span></label>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="popover_bar"><a id="confirmMove" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleMove">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div  class="JS_TIPS page_tips error" id="wxTips" style="opacity: 0.414035;display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>

</body>
</html>