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
          href="<%=request.getContextPath()%>/resources/material/processor_bar.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/mass_send29ab02.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/dropdown2f12f7.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/groupMessage.js"></script>
</head>
<body class="zh_CN screen_small">
<div id="body" class="body page_mass_send">
    <div id="js_container_box" class="container_box cell_layout side_l">
        <div class="col_main">
            <div class="main_hd">
                <div class="title_tab" id="topTab">
                    <ul class="tab_navs title_tab" data-index="0">


                        <li data-index="0" class="tab_nav first js_top selected" data-id="send"><a
                                href="<%=request.getContextPath()%>/wx/groupMessage/">新建群发消息</a>
                        </li>


                      <%--  <li data-index="1" class="tab_nav  js_top" data-id="list"><a
                                href="<%=request.getContextPath()%>/wx/groupMessage/initMessageSent">已发送</a>
                        </li>--%>


                    </ul>
                </div>
                <div class="extra_info mini_tips icon_after"><a
                        href="http://kf.qq.com/faq/120911VrYVrA131025QniAfu.html" target="_blank">群发消息规则说明</a><i
                        class="icon_mini_tips document_link"></i></div>
            </div>
            <div class="main_bd">
                <div class="highlight_box"><p class="desc">
                    为保障用户体验，微信公众平台严禁恶意营销以及诱导分享朋友圈，严禁发布色情低俗、暴力血腥、政治谣言等各类违反法律法规及相关政策规定的信息。一旦发现，我们将严厉打击和处理。 </p></div>
                <div class="grid_line send_filter">
                    <div class="grid_item target"><label class="label" >群发对象</label>
                        <div class="filter_content">
                            <div id="js_sendObj" class="dropdown_menu">
                                <a id="selectUser" href="javascript:;" class="btn dropdown_switch jsDropdownBt">
                                    <label group_id="-1" class="jsBtLabel" id="userLabel">全部用户</label><i class="arrow"></i></a>
                                <div id="allUserDiv" class="dropdown_data_container jsDropdownList" style="display: none">
                                    <ul class="dropdown_data_list">
                                        <li id="allUser" class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="-1" data-index="0" data-name="全部用户">全部用户</a>
                                        </li>
                                        <li id="tag" class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="group" data-index="1" data-name="按用户分组选择">按用户分组选择</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div id="js_group" class="dropdown_menu" style="display: none;">
                                <a id="selectTag" href="javascript:;" class="btn dropdown_switch jsDropdownBt">
                                    <label class="jsBtLabel" id="tagLabel">未分组</label><i class="arrow"></i></a>
                                <div id="allTag" class="dropdown_data_container jsDropdownList" style="display: none;">
                                    <ul class="dropdown_data_list" id="userGroups">

                                    </ul>
                                </div>
                            </div>
                            <div id="js_card_group" style="display: none;"></div>
                        </div>
                    </div>
               <%--     <div class="grid_item sex">
                        <label class="label">性别</label>
                        <div class="filter_content">
                            <div id="js_sex" class="dropdown_menu">
                                <a id="selectSex" href="javascript:;" class="btn dropdown_switch jsDropdownBt">
                                    <label  id="sexLabel" class="jsBtLabel" sex="-1">全部</label><i class="arrow"></i></a>
                                <div id="sexDiv" class="dropdown_data_container jsDropdownList" style="display: none;">
                                    <ul class="dropdown_data_list">
                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="-1" data-index="0" data-name="全部">全部</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1" data-index="1" data-name="男">男</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="2" data-index="2" data-name="女">女</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="grid_item area">
                        <label class="label" >群发地区</label>
                        <div id="js_region" class="filter_content">
                            <div id="js_country" style="" class="dropdown_menu open">
                                <a id="selectCountry" href="javascript:;" class="btn dropdown_switch jsDropdownBt">
                                    <label id="countryLabel" class="jsBtLabel" country="-1">国家</label><i class="arrow"></i></a>
                                <div id="countryDiv" class="dropdown_data_container jsDropdownList" style="display: none;">
                                    <ul class="dropdown_data_list">
                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="-1" data-index="0" data-name="全部">全部</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1017" data-index="1" data-name="中国">中国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1012" data-index="2" data-name="不丹">不丹</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1080" data-index="3" data-name="中国台湾">中国台湾</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1054" data-index="4" data-name="中国澳门">中国澳门</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1031" data-index="5" data-name="中国香港">中国香港</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1098" data-index="6" data-name="中非共和国">中非共和国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1107" data-index="7" data-name="丹麦">丹麦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1081" data-index="8" data-name="乌克兰">乌克兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1165" data-index="9" data-name="乌兹别克斯坦">乌兹别克斯坦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1163" data-index="10" data-name="乌干达">乌干达</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1164" data-index="11" data-name="乌拉圭">乌拉圭</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1025" data-index="12" data-name="乔治亚">乔治亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1167" data-index="13" data-name="也门">也门</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1089" data-index="14" data-name="亚美尼亚">亚美尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1119" data-index="15" data-name="以色列">以色列</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1037" data-index="16" data-name="伊拉克">伊拉克</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1038" data-index="17" data-name="伊朗">伊朗</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1070" data-index="18" data-name="俄罗斯">俄罗斯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1093" data-index="19" data-name="保加利亚">保加利亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1118" data-index="20" data-name="克罗地亚">克罗地亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1030" data-index="21" data-name="关岛">关岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1029" data-index="22" data-name="冈比亚">冈比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1039" data-index="23" data-name="冰岛">冰岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1048" data-index="24" data-name="列支敦士登">列支敦士登</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1015" data-index="25" data-name="刚果民主共和国">刚果民主共和国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1049" data-index="26" data-name="利比亚">利比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1130" data-index="27" data-name="利比里亚">利比里亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1014" data-index="28" data-name="加拿大">加拿大</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1114" data-index="29" data-name="加纳">加纳</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1033" data-index="30" data-name="匈牙利">匈牙利</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1055" data-index="31" data-name="北马里亚纳群岛">北马里亚纳群岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1086" data-index="32" data-name="南非">南非</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1013" data-index="33" data-name="博茨瓦纳">博茨瓦纳</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1150" data-index="34" data-name="卡塔尔">卡塔尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1152" data-index="35" data-name="卢旺达">卢旺达</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1132" data-index="36" data-name="卢森堡">卢森堡</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1036" data-index="37" data-name="印度">印度</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1034" data-index="38" data-name="印度尼西亚">印度尼西亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1116" data-index="39" data-name="危地马拉">危地马拉</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1109" data-index="40" data-name="厄瓜多尔">厄瓜多尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1111" data-index="41" data-name="厄立特里亚">厄立特里亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1104" data-index="42" data-name="古巴">古巴</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1123" data-index="43" data-name="吉尔吉斯斯坦">吉尔吉斯斯坦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1106" data-index="44" data-name="吉布提">吉布提</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1126" data-index="45" data-name="哈萨克斯坦">哈萨克斯坦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1101" data-index="46" data-name="哥伦比亚">哥伦比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1102" data-index="47" data-name="哥斯达黎加">哥斯达黎加</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1100" data-index="48" data-name="喀麦隆">喀麦隆</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1078" data-index="49" data-name="土耳其">土耳其</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1044" data-index="50" data-name="圣基茨和尼维斯">圣基茨和尼维斯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1075" data-index="51" data-name="圣马力诺">圣马力诺</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1162" data-index="52" data-name="坦桑尼亚">坦桑尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1110" data-index="53" data-name="埃及">埃及</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1112" data-index="54" data-name="埃塞俄比亚">埃塞俄比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1124" data-index="55" data-name="基里巴斯">基里巴斯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1160" data-index="56" data-name="塔吉克斯坦">塔吉克斯坦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1158" data-index="57" data-name="塞内加尔">塞内加尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1103" data-index="58" data-name="塞尔维亚,黑山">塞尔维亚,黑山</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1157" data-index="59" data-name="塞拉利昂">塞拉利昂</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1072" data-index="60" data-name="塞舌尔">塞舌尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1058" data-index="61" data-name="墨西哥">墨西哥</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1019" data-index="62" data-name="多米尼加共和国">多米尼加共和国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1004" data-index="63" data-name="奥地利">奥地利</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1083" data-index="64" data-name="委内瑞拉">委内瑞拉</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1091" data-index="65" data-name="孟加拉">孟加拉</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1002" data-index="66" data-name="安哥拉">安哥拉</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1001" data-index="67" data-name="安提瓜岛和巴布达">安提瓜岛和巴布达</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1087" data-index="68" data-name="安道尔">安道尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1142" data-index="69" data-name="尼加拉瓜">尼加拉瓜</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1141" data-index="70" data-name="尼日利亚">尼日利亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1144" data-index="71" data-name="尼泊尔">尼泊尔</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1011" data-index="72" data-name="巴哈马">巴哈马</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1148" data-index="73" data-name="巴基斯坦">巴基斯坦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1007" data-index="74" data-name="巴巴多斯岛">巴巴多斯岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1147" data-index="75" data-name="巴布亚新几内亚">巴布亚新几内亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1064" data-index="76" data-name="巴拿马">巴拿马</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1094" data-index="77" data-name="巴林">巴林</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1010" data-index="78" data-name="巴西">巴西</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1095" data-index="79" data-name="布隆迪">布隆迪</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1115" data-index="80" data-name="希腊">希腊</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1068" data-index="81" data-name="帕劳群岛">帕劳群岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1047" data-index="82" data-name="开曼群岛">开曼群岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1018" data-index="83" data-name="德国">德国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1040" data-index="84" data-name="意大利">意大利</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1153" data-index="85" data-name="所罗门群岛">所罗门群岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1133" data-index="86" data-name="拉脱维亚">拉脱维亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1143" data-index="87" data-name="挪威">挪威</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1105" data-index="88" data-name="捷克共和国">捷克共和国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1051" data-index="89" data-name="摩尔多瓦">摩尔多瓦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1134" data-index="90" data-name="摩洛哥">摩洛哥</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1050" data-index="91" data-name="摩纳哥">摩纳哥</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1009" data-index="92" data-name="文莱">文莱</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1021" data-index="93" data-name="斐济">斐济</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1076" data-index="94" data-name="斯威士兰">斯威士兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1156" data-index="95" data-name="斯洛伐克">斯洛伐克</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1155" data-index="96" data-name="斯洛文尼亚">斯洛文尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1129" data-index="97" data-name="斯里兰卡">斯里兰卡</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1074" data-index="98" data-name="新加坡">新加坡</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1061" data-index="99" data-name="新喀里多尼亚">新喀里多尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1063" data-index="100" data-name="新西兰">新西兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1042" data-index="101" data-name="日本">日本</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1099" data-index="102" data-name="智利">智利</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1125" data-index="103" data-name="朝鲜">朝鲜</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1043" data-index="104" data-name="柬埔寨">柬埔寨</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1026" data-index="105" data-name="格恩西岛">格恩西岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1024" data-index="106" data-name="格林纳达">格林纳达</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1028" data-index="107" data-name="格陵兰">格陵兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1092" data-index="108" data-name="比利时">比利时</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1138" data-index="109" data-name="毛里塔尼亚">毛里塔尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1056" data-index="110" data-name="毛里求斯">毛里求斯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1161" data-index="111" data-name="汤加">汤加</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1071" data-index="112" data-name="沙特阿拉伯">沙特阿拉伯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1022" data-index="113" data-name="法国">法国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1149" data-index="114" data-name="波兰">波兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1066" data-index="115" data-name="波多黎各">波多黎各</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1077" data-index="116" data-name="泰国">泰国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1041" data-index="117" data-name="泽西岛">泽西岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1117" data-index="118" data-name="洪都拉斯">洪都拉斯</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1032" data-index="119" data-name="海地">海地</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1005" data-index="120" data-name="澳大利亚">澳大利亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1035" data-index="121" data-name="爱尔兰">爱尔兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1120" data-index="122" data-name="牙买加">牙买加</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1079" data-index="123" data-name="特立尼达和多巴哥">特立尼达和多巴哥</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1097" data-index="124" data-name="玻利维亚">玻利维亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1073" data-index="125" data-name="瑞典">瑞典</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1016" data-index="126" data-name="瑞士">瑞士</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1166" data-index="127" data-name="瓦努阿图">瓦努阿图</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1069" data-index="128" data-name="留尼旺岛">留尼旺岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1008" data-index="129" data-name="百慕大">百慕大</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1027" data-index="130" data-name="直布罗陀">直布罗陀</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1046" data-index="131" data-name="科威特">科威特</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1146" data-index="132" data-name="秘鲁">秘鲁</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1121" data-index="133" data-name="约旦">约旦</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1140" data-index="134" data-name="纳米比亚">纳米比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1136" data-index="135" data-name="缅甸">缅甸</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1151" data-index="136" data-name="罗马尼亚">罗马尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1082" data-index="137" data-name="美国">美国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1127" data-index="138" data-name="老挝">老挝</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1122" data-index="139" data-name="肯尼亚">肯尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1113" data-index="140" data-name="芬兰">芬兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1154" data-index="141" data-name="苏丹">苏丹</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1159" data-index="142" data-name="苏里南">苏里南</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1023" data-index="143" data-name="英国">英国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1062" data-index="144" data-name="荷兰">荷兰</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1060" data-index="145" data-name="莫桑比克">莫桑比克</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1131" data-index="146" data-name="莱索托">莱索托</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1065" data-index="147" data-name="菲律宾">菲律宾</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1085" data-index="148" data-name="萨摩亚">萨摩亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1067" data-index="149" data-name="葡萄牙">葡萄牙</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1137" data-index="150" data-name="蒙古">蒙古</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1020" data-index="151" data-name="西班牙">西班牙</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1096" data-index="152" data-name="贝宁">贝宁</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1168" data-index="153" data-name="赞比亚">赞比亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1084" data-index="154" data-name="越南">越南</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1090" data-index="155" data-name="阿塞拜疆">阿塞拜疆</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1108" data-index="156" data-name="阿尔及利亚">阿尔及利亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1088" data-index="157" data-name="阿尔巴尼亚">阿尔巴尼亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1000" data-index="158" data-name="阿拉伯联合酋长国">阿拉伯联合酋长国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1145" data-index="159" data-name="阿曼">阿曼</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1003" data-index="160" data-name="阿根廷">阿根廷</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1006" data-index="161" data-name="阿鲁巴">阿鲁巴</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1045" data-index="162" data-name="韩国">韩国</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1053" data-index="163" data-name="马其顿">马其顿</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1057" data-index="164" data-name="马尔代夫">马尔代夫</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1139" data-index="165" data-name="马拉维">马拉维</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1059" data-index="166" data-name="马来西亚">马来西亚</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1052" data-index="167" data-name="马绍尔群岛">马绍尔群岛</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1135" data-index="168" data-name="马达加斯加">马达加斯加</a>
                                        </li>

                                        <li class="dropdown_data_item ">
                                            <a onclick="return false;" href="javascript:;" class="jsDropdownItem"
                                               data-value="1128" data-index="169" data-name="黎巴嫩">黎巴嫩</a>
                                        </li>


                                    </ul>
                                </div>
                            </div>
                            <div id="js_province1814" style="display:none"></div>
                            <div id="js_city144" style="display:none"></div>
                        </div>
                    </div>--%>
                </div>
                <div id="js_msgSender" class="msg_sender">

                </div>
                <div class="tool_area"><p class="profile_link_msg_global send_msg mini_tips warn js_warn dn"
                                          style="display: none;"> 请勿添加其他公众号的主页链接 </p>
                    <ul class="mass_send_function"></ul>
                </div>
                <div class="tool_bar">
                    <div id="verifycode"></div>
                    <span id="js_submit" class="btn btn_input btn_primary"><button type="button">群发</button></span>
                   <%-- <div class="bubble_tips bubble_left warn">
                        <div class="bubble_tips_inner"><p class="mass_send_tips" id="js_masssend_tips">你今天还能群发 <em
                                id="leftNum" class="send_num">1</em> 条消息</p></div>
                        <i class="bubble_tips_arrow out"></i> <i class="bubble_tips_arrow in"></i></div>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="selectNewDiv" class="dialog_wrp media align_edge ui-draggable" style="z-index: 9999;width: 960px;height: 500px; display: none;">
    <div class="dialog">
        <div class="dialog_hd">
            <h3>选择素材</h3>
            <a href="javascript:;" onclick="$('#selectNewDiv').hide();$('#mask').hide();" class="icon16_opr closed pop_closed" >关闭</a>
        </div>
        <div class="dialog_bd" id="loadNews">
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
</html>