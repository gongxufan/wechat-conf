<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li class="keywords_rule_item open" id="regular_<%=request.getParameter("id")%>">

    <div class="keywords_rule_hd no_extra dropdown_area Js_detail_switch dropdown_opened" data-id="0" data-reply="loaded" style="-webkit-transition: all, 1s;">
        <div class="info">
            <em class="keywords_rule_num">新规则</em>
            <strong class="keywords_rule_name"></strong>
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

                </ul>
            </div>
        </div>
        <div class="keywords_info reply">
            <strong class="keywords_info_title">回复</strong>
            <div class="keywords_info_detail">
                <p class="reply_info">
                    <em  name="totalCnt"  class="num">0</em>条（
                    <em  name="txtCnt" data-type="1" class="num Js_reply_cnt2">0</em>条文字，
                    <em  name="imgCnt" data-type="2" class="num Js_reply_cnt2">0</em>条图片，
<!--                    <em data-type="3" class="num Js_reply_cnt2">0</em>条语音，
                    <em data-type="4" class="num Js_reply_cnt2">0</em>条视频，-->
                    <em  name="txtImgCnt" data-type="5" class="num Js_reply_cnt2">0</em>条图文）

                </p>
            </div>
        </div>
        <div id="Js_replyAllOverview_0" class="dn">发送全部回复</div>
    </div>


    <div class="keywords_rule_bd keywords_rule_detail">

        <div class="rule_name_area">
            <div class="frm_control_group">
                <label for="" class="frm_label">规则名</label>
                <div class="frm_controls">
                    <span class="frm_input_box"><input type="text" class="frm_input" id="Js_ruleName_<%=request.getParameter("id")%>" value=""></span>
                    <p class="frm_tips">规则名最多60个字</p>
                </div>
            </div>
        </div>


        <div class="keywords_tap keywords no_data">
            <div class="keywords_tap_hd">
                <div class="info">
                    <h4>关键字</h4>
                </div>
                <div class="opr">
                    <a href="javascript:;" data-id="0" class="Js_keyword_add">添加关键字</a>
                </div>
            </div>
            <div class="keywords_tap_bd">
                <ul name="Js_keywordList"  class="keywords_list" id="Js_keywordList_0" style="display: block;">

                </ul>
            </div>
        </div>


        <div class="keywords_tap reply no_data">
            <div class="keywords_tap_hd">
                <div class="info">
                    <h4>回复</h4>
                </div>
                <div class="opr">
                    <label id="checkAll_0" for="Js_replyAll_0" class="frm_checkbox_label">
                        <i class="icon_checkbox"></i>
                        <input id="Js_replyAll_0" type="checkbox" class="frm_checkbox Js_reply_all">
                        回复全部                        </label>
                </div>
            </div>
            <div class="keywords_tap_bd">

                <ul class="media_type_list">
                    <li data-regularid="<%=request.getParameter("id")%>" data-id="0" class="tab_text" data-tooltip="文字"><a href="javascript:;" data-type="1"  class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                    <li  data-regularid="<%=request.getParameter("id")%>"  data-id="0" class="tab_img" data-tooltip="图片"><a href="javascript:;" data-type="2"  class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                   <!-- <li class="tab_audio" data-tooltip="语音"><a href="javascript:;" data-type="3" data-id="0" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>
                    <li class="tab_video" data-tooltip="视频"><a href="javascript:;" data-type="7" data-id="0" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>-->
                    <li  data-regularid="<%=request.getParameter("id")%>"  data-id="0" class="tab_appmsg" data-tooltip="图文"><a href="javascript:;" data-type="5" class="Js_reply_add">&nbsp;<i class="icon_msg_sender"></i></a></li>


                </ul>


                <ul name="Js_replyList"  class="keywords_list" id="Js_replyList_0" style="display: block;">

                </ul>
            </div>
        </div>
        <p class="mini_tips warn dn js_warn profile_link_msg_global keywords">请勿添加其他公众号的主页链接</p>

    </div>


    <div class="keywords_rule_ft">

        <p class="media_stat info">
            文字(<em name="txtCnt" data-type="1" class="num Js_reply_cnt">0</em>)、
            图片(<em name="imgCnt" data-type="2" class="num Js_reply_cnt">0</em>)、
          <!--  语音(<em data-type="3" class="num Js_reply_cnt">0</em>)、
            视频(<em data-type="4" class="num Js_reply_cnt">0</em>)、     -->
            图文(<em name="txtImgCnt" data-type="5" class="num Js_reply_cnt">0</em>)

        </p>

        <div class="opr">
            <a href="javascript:;" data-id="<%=request.getParameter("id")%>" class="btn btn_primary Js_rule_save">保存</a>
            <a href="javascript:;" name="delRegular" data-id="0" class="btn btn_default Js_rule_del btn-disable">删除</a>
        </div>
    </div>
</li>
