<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<div class="img_pick_panel inner_container_box side_l cell_layout">
    <div class="inner_side">
        <div class="group_list">
            <div class="inner_menu_box">
                <dl class="inner_menu js_group">
                    <dl class="inner_menu">
                        <c:forEach items="${imageGroups}" var="group">
                            <dd class="inner_menu_item <c:if test="${group_id == group.group_id}">selected</c:if> js_groupitem">
                                <a href="javascript:;" onclick="dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?group_id=${group.group_id}&cols=3&type=IMAGE&pageNow=1&count=12&view=card&isGroupMessage=1')"
                                   class="inner_menu_link" title="${group.groupName}">
                                    <strong>${group.groupName}</strong><em class="num">(${group.count})</em>
                                </a>
                            </dd>
                        </c:forEach>
                    </dl>

                </dl>
            </div>
        </div>
    </div>
    <div class="inner_main">
        <div class="img_pick_area">
          <%--  <div class="sub_title_bar in_dialog">
                <div class="upload_box r align_right">
                    <span class="upload_area webuploader-container"><a id="js_imageupload7517217431663139" class="btn btn_primary js_imageupload webuploader-pick" data-groupid="">本地上传</a><ul class="upload_file_box" style="display:none"></ul><div id="rt_rt_1as4i4rib1qce17au16021sqjjnv8" style="position: absolute; top: 0px; left: 0px; width: 106px; height: 32px; overflow: hidden; bottom: auto; right: auto;"><input type="file" multiple="multiple" accept="image/*" style="display: none;"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></span>
                </div>
                <div class="img_water_tips mini_tips icon_after r weak_text">
                    大小不超过5M<span class="js_water">，已开启图片水印</span>
                    <i class="js_water_tips icon_msg_mini ask"></i>
                </div>
            </div>--%>
            <div>
                <div class="img_pick">
                    <i class="icon_loading_small white js_loading" style="display: none;"></i>
                    <ul class="group js_list img_list">
                        <c:forEach items="${imageList}" var="image">
                        <li  class="img_item js_imageitem" >
                            <label name="imageList" onclick="checkImage('${image.media_id}',this)" class="frm_checkbox_label img_item_bd" cover="${image.url}">
                                <div class="pic_box">
                                    <img class="pic js_pic" src="${image.url}" style="height: 117px;">
                                </div>
                                <span class="lbl_content">
                                        ${image.name}
                                </span>
                                <div class="selected_mask">
                                    <div class="selected_mask_inner"></div>
                                    <div class="selected_mask_icon"></div>
                                </div>
                            </label>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="js_pagebar">

                    <div class="pagination" id="wxPagebar_1470637376706">
                <span class="page_nav_area">
                    <a class="btn page_first" href="javascript:void(0);" style="display: none;"></a>
                                <c:if test="${pageNow <= pages && pageNow > 1}">
                                    <a onclick="dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=2&type=IMAGE&pageNow=${pageNow-1}&count=10&view=card&group_id=${group_id}&isGroupMessage=1')" href="javascript:;" class="btn page_prev"><i class="arrow"></i></a>
                                </c:if>
                                <span class="page_num">
                                    <label>${pageNow}</label>
                                    <span class="num_gap">/</span>
                                    <label>${pages}</label>
                                </span>
                                <c:if test="${pageNow < pages}">
                                    <a onclick="dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=2&type=IMAGE&pageNow=${pageNow+1}&count=10&view=card&group_id=${group_id}&isGroupMessage=1');" href="javascript:;" class="btn page_next"><i class="arrow"></i></a>
                                </c:if>
                    <a class="btn page_last" href="javascript:void(0);" style="display: none;"></a>
                </span>
                        <span class="goto_area">
        <input type="text" id="goPage">
        <a href="javascript:void(0);" class="btn page_go" onclick="javascript:if(!document.getElementById('goPage').value || document.getElementById('goPage').value < 1 || document.getElementById('goPage').value >${pages})return;dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?cols=3&type=IMAGE&pageNow='+document.getElementById('goPage').value+'&count=10&view=card&group_id=${group_id}&isGroupMessage=1')">跳转</a>
    </span>
                    </div>

                </div>
                <div class="dialog_ft">

                    <span class="btn btn_input js_btn_p btn_primary btn_disabled"><button id="confirmImage" type="button" class="js_btn" data-index="0">确定</button></span>

                    <span class="btn btn_default btn_input js_btn_p"><button  onclick="$('#selectImageDiv').hide();$('#mask').hide();" id="cancleImage" type="button" class="js_btn" data-index="1">取消</button></span>

                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="imgId"/>
<input type="hidden" id="urlbase64"/>
<script type="text/javascript">
    function dialogPage(url) {
        $("#selectImageDiv").show();
        //窗口居中
        centerWindow('selectImageDiv');
        $("#mask").show();
        $("#loadImages").load(url,function () {
        });
    }

</script>