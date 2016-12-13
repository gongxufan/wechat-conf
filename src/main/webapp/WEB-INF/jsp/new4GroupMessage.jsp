<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<input id="currNews" type="hidden"/>
<div class="dialog_media_container appmsg_media_dialog">
    <div class="dialog_media_inner">
        <div class="js_appmsg_list appmsg_list media_dialog">
            <div class="appmsg_col" title="点击选择该图文消息">
                <div class="inner">
                    <div class="appmsgCnt">
                        <c:forEach items="${col1}" var="news1">
                            <c:if test="${(news1)!= null && fn:length(news1) == 1}">
                                <c:forEach items="${news1}" var="news2">
                                    <div  name="msg" class="appmsg single has_first_cover" url="${news2.url}" id="${news2.media_id}">
                                        <div class="appmsg_content">
                                            <div class="appmsg_info">
                                                <em class="appmsg_date">${news2.update_time}</em>
                                            </div>
                                            <div class="appmsg_item">
                                                <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;">${news2.title}</a></h4>
                                                <div cover="${news2.thumb_url}"
                                                     class="appmsg_thumb_wrp">
                                                    <img style="width:100%;height: 100%" src="${news2.thumb_url_base64}"/>
                                                </div>
                                                <p class="appmsg_desc">${news2.digest}</p>
                                            </div>
                                        </div>
                                        <div class="edit_mask" style="display: none">
                                            <i class="icon_card_selected">已选择</i>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${(news1)!= null && fn:length(news1) > 1}">
                                <div  url="${news1[0].url}" class="appmsg multi has_first_cover"  name="msg" id="${news1[0].media_id}">
                                    <div class="appmsg_content">
                                        <div class="appmsg_info">
                                            <em class="apc pmsg_date">${news1[0].update_time}</em>
                                        </div>
                                        <div class="cover_appmsg_item">
                                            <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;">${news1[0].title}</a></h4>
                                            <div cover="${news1[0].thumb_url}"
                                                 class="appmsg_thumb_wrp">
                                                <img style="width:100%;height: 100%" src="${news1[0].thumb_url_base64}"/>
                                            </div>

                                        </div>
                                        <c:forEach items="${news1}" var="news2" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                <div class="appmsg_item has_cover">
                                                    <div cover="${news2.thumb_url}"
                                                         class="appmsg_thumb_wrp">
                                                        <img style="width:100%;height: 100%" src="${news2.thumb_url_base64}"/>
                                                    </div>
                                                    <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;" >${news2.title}</a></h4>
                                                </div>
                                            </c:if>

                                        </c:forEach>
                                    </div>
                                    <div class="edit_mask" style="display: none;">
                                        <i class="icon_card_selected">已选择</i>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
            &nbsp;
            <div class="appmsg_col" title="点击选择该图文消息">
                <div class="inner">
                    <div class="appmsgCnt">

                        <c:forEach items="${col2}" var="news3">
                            <c:if test="${(news3)!= null && fn:length(news3) == 1}">
                                <c:forEach items="${news3}" var="news4">
                                    <div url="${news4.url}"   name="msg" class="appmsg single has_first_cover" id="${news4.media_id}">
                                        <div class="appmsg_content">
                                            <div class="appmsg_info">
                                                <em class="appmsg_date">${news4.update_time}</em>
                                            </div>
                                            <div class="appmsg_item">
                                                <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;">${news4.title}</a></h4>
                                                <div cover="${news4.thumb_url}"
                                                     class="appmsg_thumb_wrp">
                                                    <img style="width:100%;height: 100%" src="${news4.thumb_url_base64}"/>
                                                </div>
                                                <p class="appmsg_desc">${news4.digest}</p>
                                            </div>
                                        </div>
                                        <div class="edit_mask" style="display: none">
                                            <i class="icon_card_selected">已选择</i>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${(news3)!= null && fn:length(news3) > 1}">
                                <div class="appmsg multi has_first_cover"  url="${news3[0].url}"   name="msg" id="${news3[0].media_id}">
                                    <div class="appmsg_content">
                                        <div class="appmsg_info">
                                            <em class="apc pmsg_date">${news3[0].update_time}</em>
                                        </div>
                                        <div class="cover_appmsg_item" >
                                            <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;">${news3[0].title}</a></h4>
                                            <div cover="${news3[0].thumb_url}"
                                                 class="appmsg_thumb_wrp">
                                                <img style="width:100%;height: 100%" src="${news3[0].thumb_url_base64}"/>
                                            </div>

                                        </div>
                                        <c:forEach items="${news3}" var="news4" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                <div class="appmsg_item has_cover" >
                                                    <div cover="${news4.thumb_url}"
                                                         class="appmsg_thumb_wrp">
                                                        <img style="width:100%;height: 100%" src="${news4.thumb_url_base64}"/>
                                                    </div>
                                                    <h4 class="appmsg_title js_title"><a target="_blank" href="javascript:;" >${news4.title}</a></h4>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="edit_mask" style="display: none;">
                                        <i class="icon_card_selected">已选择</i>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>

                    </div>
                </div>
            </div>
        </div>
        <div class="pagination_wrp pageNavigator" style="text-align: center;padding: 0px">
            <div class="pagination" id="wxPagebar_1470637376706">
                <span class="page_nav_area">
                    <a class="btn page_first" href="javascript:void(0);" style="display: none;"></a>
                                <c:if test="${pageNow <= pages && pageNow > 1}">
                                    <a onclick="dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?isGroupMessage=1&cols=2&type=NEWS&pageNow=${pageNow-1}&count=4&view=card')" href="javascript:;" class="btn page_prev"><i class="arrow"></i></a>
                                </c:if>
                                <span class="page_num">
                                    <label>${pageNow}</label>
                                    <span class="num_gap">/</span>
                                    <label>${pages}</label>
                                </span>
                                <c:if test="${pageNow < pages}">
                                    <a onclick="dialogPage('<%=request.getContextPath()%>/weixin/api/batchGetMaterial?isGroupMessage=1&cols=2&type=NEWS&pageNow=${pageNow+1}&count=4&view=card');" href="javascript:;" class="btn page_next"><i class="arrow"></i></a>
                                </c:if>
                    <a class="btn page_last" href="javascript:void(0);" style="display: none;"></a>
                </span>
            </div>
            <div class="dialog_ft">

                <span class="btn btn_primary btn_input js_btn_p"><button id="confirmNews" data-index="0" class="js_btn" type="button">确定</button></span>

                <span class="btn btn_default btn_input js_btn_p"><button onclick='$("#selectNewDiv").hide();$("#mask").hide();' id="cancleNews" data-index="1" class="js_btn" type="button">取消</button></span>

            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    function dialogPage(url) {
        $("#selectNewDiv").show();
        //窗口居中
        centerWindow('selectNewDiv');
        $("#mask").show();
        $("#loadNews").load(url,function () {
        });
    }

</script>



