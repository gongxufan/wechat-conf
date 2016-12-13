<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>系统配置</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/layout_head2880f5.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/base2fde18.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/material/lib2f613f.css">
    <style>
        html {
            overflow-x: hidden
        }

        .page_advanced_interface .agreement_box .tool_area {
            padding: .5em 0 1em
        }

        .page_advanced_interface .agreement_box .tool_bar {
            text-align: center;
            padding-left: 0
        }

        .page_advanced_interface .agreement_box .tool_bar .btn {
            margin: 0
        }

        .page_advanced_interface .frm_label {
            width: 115px
        }

        .page_advanced_interface .main_bd {
            padding: 15px 30px
        }

        .page_advanced_interface .page_nav {
            margin-bottom: 0
        }

        .page_advanced_interface .frm_desc {
            padding-bottom: 40px
        }

        .page_advanced_interface .frm_input_box {
            width: 530px
        }

        .page_advanced_interface .key_input_box {
            width: 350px
        }

        .page_advanced_interface .frm_tips {
            width: auto
        }

        .page_advanced_interface .msg_encrypt_type {
            padding-top: .3em;
            overflow: hidden
        }

        .page_advanced_interface .msg_encrypt_type dd {
            margin-top: 5px
        }

        .page_advanced_interface .msg_encrypt_type .frm_tips {
            margin-left: 19px
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sysconfig.js"></script>
</head>
<body>
<div id="body" class="body page_advanced_interface" style="width: 100%;">
    <div id="js_container_box" class="container_box cell_layout side_l">
        <div class="col_main">
            <div class="main_bd">
                <form action="" id="form" class="form" novalidate="novalidate" onsubmit="return false">
                    <fieldset class="frm_fieldset">
                        <p class="frm_desc">
                            请按要求填写公众号配置信息，请阅读<a
                            href="https://mp.weixin.qq.com/wiki" target="_blank">接入指南</a>。
                        </p>
                        <div class="frm_control_group"><label for="appId" class="frm_label">appId</label>
                            <div class="frm_controls"><span class="frm_input_box">
                                <input type="text" id="appId" name="appId" class="frm_input interface" value="${appId}"></span>
                                <p  id="appTips" class="frm_msg fail" style="display: none;">
                                    <span for="js_key" class="frm_msg_content" style="display: inline;">appId不能为空</span></p>
                                <p class="frm_tips"> 填写有效的公众号appId。<br></p></div>
                        </div>
                        <div class="frm_control_group"><label for="appSecret" class="frm_label">appSecret</label>
                            <div class="frm_controls"><span class="frm_input_box">
                                <input type="text" id="appSecret" name="appSecret" class="frm_input interface" value="${appSecret}"></span>
                                <p  id="secretTips" class="frm_msg fail" style="display: none;">
                                    <span for="js_key" class="frm_msg_content" style="display: inline;">appSecret不能为空</span></p>
                                <p class="frm_tips"> 填写有效的公众号appSecret。<br></p></div>
                        </div>
                        <div class="frm_control_group"><label for="token" class="frm_label">Token</label>
                            <div class="frm_controls"><span class="frm_input_box">
                                <input type="text" id="token" name="token" class="frm_input interface" value="${token}"></span>
                                <p  id="tokenTips" class="frm_msg fail" style="display: none;">
                                    <span for="token" class="frm_msg_content" style="display: inline;">请输入正确的Token号码</span>
                                </p>
                                <p class="frm_tips"> 必须为英文或数字，长度为3-32字符。<br>
                                    <a href="_('https://mp.weixin.qq.com/wiki)" target="_blank">什么是Token？</a></p>
                            </div>
                        </div>
                        <div class="frm_control_group"><label for="" class="frm_label">EncodingAESKey</label>
                            <div class="frm_controls"><span
                                    class="frm_input_box vcode with_counter counter_in append key_input_box">                            <input
                                    type="text" class="frm_input interface" value="${encodingAesKey}" id="js_key" name="js_key" maxlength="43">                            <em
                                    class="frm_input_append frm_counter" id="js_key_len_tips">${aesSize}&nbsp;<em>/</em>&nbsp;43</em>                        </span>
                                <a href="javascript:;" class="btn btn_default btn_vcode" id="js_random">随机生成</a>
                                <p  id="keyTips" class="frm_msg fail" style="display: none;">
                                    <span for="js_key" class="frm_msg_content" style="display: inline;">请输入正确的EncodeingAESKey</span></p>
                                <p class="frm_tips"> 消息加密密钥由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。<br><a
                                        href="https://mp.weixin.qq.com/wiki" target="_blank">什么是EncodingAESKey？</a></p>
                            </div>
                        </div>
                        <div class="frm_control_group"><label for="" class="frm_label">消息加解密方式</label>
                            <div class="frm_controls" id="js_encrypt_type">
                                <dl class="msg_encrypt_type">
                                    <dt>请根据业务需要，选择消息加解密类型，启用后将立即生效</dt>
                                    <dd><label class="frm_radio_label <c:if test="${encryptMessage == '0'}">selected</c:if>" for="checkbox1"><input type="radio"
                                                                                                       name="hello"
                                                                                                       value="0"
                                                                                                       data-label="明文模式"
                                                                                                       checked="checked"
                                                                                                       class="frm_radio"
                                                                                                       id="checkbox1"><i
                                            class="icon_radio"></i><span class="lbl_content">明文模式</span></label>
                                        <p class="frm_tips">明文模式下，不使用消息体加解密功能，安全系数较低</p></dd>
                                    <dd><label class="frm_radio_label  <c:if test="${encryptMessage == '1'}">selected</c:if>" for="checkbox2"><input type="radio" name="hello"
                                                                                              value="1"
                                                                                              data-label="兼容模式"
                                                                                              class="frm_radio"
                                                                                              id="checkbox2"><i
                                            class="icon_radio"></i><span class="lbl_content">兼容模式</span></label>
                                        <p class="frm_tips">兼容模式下，明文、密文将共存，方便开发者调试和维护</p></dd>
                                    <dd><label class="frm_radio_label  <c:if test="${encryptMessage == '2'}">selected</c:if>" for="checkbox3"><input type="radio" name="hello"
                                                                                              value="2"
                                                                                              data-label="安全模式（推荐）"
                                                                                              class="frm_radio"
                                                                                              id="checkbox3"><i
                                            class="icon_radio"></i><span class="lbl_content">安全模式（推荐）</span></label>
                                        <p class="frm_tips">安全模式下，消息包为纯密文，需要开发者加密和解密，安全系数高</p></dd>
                                </dl>
                            </div>
                        </div>
                    </fieldset>
                    <div class="tool_bar border tc with_form"><span class="btn btn_input btn_primary">  <button
                            class="submit" type="button" id="commit">提交</button>  </span></div>
                </form>
            </div>
        </div>
    </div>
</div>
<div  class="JS_TIPS page_tips error" id="wxTips" style="display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>
</body>
</html>