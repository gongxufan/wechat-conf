<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="msg_tab">
    <div class="tab_navs_panel">
        <span class="tab_navs_switch_wrp switch_prev js_switch_prev">
            <span class="tab_navs_switch"></span>
        </span>
        <span class="tab_navs_switch_wrp switch_next js_switch_next">
            <span class="tab_navs_switch"></span>
        </span>
        <div class="tab_navs_wrp">
            <ul class="tab_navs js_tab_navs" style="margin-left:0;">

                <li id="imgtextTab" class="tab_nav tab_appmsg width5 selected" data-type="10"
                    data-tab=".js_appmsgArea" data-tooltip="图文消息">
                    <a href="javascript:void(0);" onclick="return false;">&nbsp;<i
                            class="icon_msg_sender"></i><span class="msg_tab_title">图文消息</span></a>
                </li>

                <li id="textTab" class="tab_nav tab_text width5" data-type="1" data-tab=".js_textArea"
                    data-tooltip="文字">
                    <a href="javascript:void(0);" onclick="return false;">&nbsp;<i
                            class="icon_msg_sender"></i><span class="msg_tab_title">文字</span></a>
                </li>

                <li id="imgTab" class="tab_nav tab_img width5" data-type="2" data-tab=".js_imgArea"
                    data-tooltip="图片">
                    <a href="javascript:void(0);" onclick="return false;">&nbsp;<i
                            class="icon_msg_sender"></i><span class="msg_tab_title">图片</span></a>
                </li>

                <%--  <li class="tab_nav tab_audio width5" data-type="3" data-tab=".js_audioArea"
                      data-tooltip="语音">
                      <a href="javascript:void(0);" onclick="return false;">&nbsp;<i
                              class="icon_msg_sender"></i><span class="msg_tab_title">语音</span></a>
                  </li>

                  <li class="tab_nav tab_video width5 no_extra" data-type="15"
                      data-tab=".js_videoArea" data-tooltip="视频">
                      <a href="javascript:void(0);" onclick="return false;">&nbsp;<i
                              class="icon_msg_sender"></i><span class="msg_tab_title">视频</span></a>
                  </li>--%>

            </ul>
        </div>
    </div>
    <div class="tab_panel">

        <div class="tab_content" id="sendImgtext">
            <div class="js_appmsgArea inner ">
                <!--type 10图文 2图片  3语音 15视频 11商品消息-->

                <div class="tab_cont_cover jsMsgSendTab" id="selectNewsArea">
                    <div class="media_cover">
                                        <span class="create_access">
                                            <a id="selectNews" class="add_gray_wrp jsMsgSenderPopBt" href="javascript:;"
                                               data-type="10"
                                               data-index="0">
                                                <i class="icon36_common add_gray"></i>
                                                <strong>从素材库中选择</strong>
                                            </a>
                                        </span>
                    </div>
                    <div class="media_cover">
                                        <span class="create_access">
                                            <a target="_blank" class="add_gray_wrp"
                                               href="javascript:;" onclick="createNewMessage()">
                                                <i class="icon36_common add_gray"></i>
                                                <strong>新建图文消息</strong>
                                            </a>
                                        </span>
                    </div>
                </div>
                <div id="selectedNews">

                </div>
            </div>
        </div>

        <div class="tab_content" style="display: none;" id="sendText">
            <div class="js_textArea inner no_extra">
                <div class="emotion_editor">
                    <div class="edit_area js_editorArea" contenteditable="true"
                         style="overflow-y: auto; overflow-x: hidden;"></div>
                    <div class="editor_toolbar">

                        <a href="javascript:void(0);" class="icon_emotion emotion_switch js_switch">表情</a>


                        <p class="editor_tip opr_tips">，按下Shift+Enter键换行</p>
                        <p class="editor_tip js_editorTip" id="editTips">还可以输入<em id="leftNums">600</em>字</p>
                        <div class="emotion_wrp js_emotionArea"><span class="hook">
	<span class="hook_dec hook_top"></span>
	<span class="hook_dec hook_btm"></span>
</span>
                            <ul class="emotions" onselectstart="return false;">

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/0.gif"
                                       data-title="微笑" style="background-position:0px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/1.gif"
                                       data-title="撇嘴" style="background-position:-24px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/2.gif"
                                       data-title="色" style="background-position:-48px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/3.gif"
                                       data-title="发呆" style="background-position:-72px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/4.gif"
                                       data-title="得意" style="background-position:-96px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/5.gif"
                                       data-title="流泪" style="background-position:-120px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/6.gif"
                                       data-title="害羞" style="background-position:-144px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/7.gif"
                                       data-title="闭嘴" style="background-position:-168px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/8.gif"
                                       data-title="睡" style="background-position:-192px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/9.gif"
                                       data-title="大哭" style="background-position:-216px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/10.gif"
                                       data-title="尴尬" style="background-position:-240px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/11.gif"
                                       data-title="发怒" style="background-position:-264px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/12.gif"
                                       data-title="调皮" style="background-position:-288px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/13.gif"
                                       data-title="呲牙" style="background-position:-312px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/14.gif"
                                       data-title="惊讶" style="background-position:-336px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/15.gif"
                                       data-title="难过" style="background-position:-360px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/16.gif"
                                       data-title="酷" style="background-position:-384px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/17.gif"
                                       data-title="冷汗" style="background-position:-408px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/18.gif"
                                       data-title="抓狂" style="background-position:-432px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/19.gif"
                                       data-title="吐" style="background-position:-456px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/20.gif"
                                       data-title="偷笑" style="background-position:-480px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/21.gif"
                                       data-title="可爱" style="background-position:-504px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/22.gif"
                                       data-title="白眼" style="background-position:-528px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/23.gif"
                                       data-title="傲慢" style="background-position:-552px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/24.gif"
                                       data-title="饥饿" style="background-position:-576px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/25.gif"
                                       data-title="困" style="background-position:-600px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/26.gif"
                                       data-title="惊恐" style="background-position:-624px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/27.gif"
                                       data-title="流汗" style="background-position:-648px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/28.gif"
                                       data-title="憨笑" style="background-position:-672px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/29.gif"
                                       data-title="大兵" style="background-position:-696px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/30.gif"
                                       data-title="奋斗" style="background-position:-720px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/31.gif"
                                       data-title="咒骂" style="background-position:-744px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/32.gif"
                                       data-title="疑问" style="background-position:-768px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/33.gif"
                                       data-title="嘘" style="background-position:-792px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/34.gif"
                                       data-title="晕" style="background-position:-816px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/35.gif"
                                       data-title="折磨" style="background-position:-840px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/36.gif"
                                       data-title="衰" style="background-position:-864px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/37.gif"
                                       data-title="骷髅" style="background-position:-888px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/38.gif"
                                       data-title="敲打" style="background-position:-912px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/39.gif"
                                       data-title="再见" style="background-position:-936px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/40.gif"
                                       data-title="擦汗" style="background-position:-960px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/41.gif"
                                       data-title="抠鼻" style="background-position:-984px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/42.gif"
                                       data-title="鼓掌" style="background-position:-1008px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/43.gif"
                                       data-title="糗大了" style="background-position:-1032px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/44.gif"
                                       data-title="坏笑" style="background-position:-1056px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/45.gif"
                                       data-title="左哼哼" style="background-position:-1080px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/46.gif"
                                       data-title="右哼哼" style="background-position:-1104px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/47.gif"
                                       data-title="哈欠" style="background-position:-1128px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/48.gif"
                                       data-title="鄙视" style="background-position:-1152px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/49.gif"
                                       data-title="委屈" style="background-position:-1176px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/50.gif"
                                       data-title="快哭了" style="background-position:-1200px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/51.gif"
                                       data-title="阴险" style="background-position:-1224px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/52.gif"
                                       data-title="亲亲" style="background-position:-1248px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/53.gif"
                                       data-title="吓" style="background-position:-1272px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/54.gif"
                                       data-title="可怜" style="background-position:-1296px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/55.gif"
                                       data-title="菜刀" style="background-position:-1320px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/56.gif"
                                       data-title="西瓜" style="background-position:-1344px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/57.gif"
                                       data-title="啤酒" style="background-position:-1368px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/58.gif"
                                       data-title="篮球" style="background-position:-1392px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/59.gif"
                                       data-title="乒乓" style="background-position:-1416px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/60.gif"
                                       data-title="咖啡" style="background-position:-1440px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/61.gif"
                                       data-title="饭" style="background-position:-1464px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/62.gif"
                                       data-title="猪头" style="background-position:-1488px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/63.gif"
                                       data-title="玫瑰" style="background-position:-1512px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/64.gif"
                                       data-title="凋谢" style="background-position:-1536px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/65.gif"
                                       data-title="示爱" style="background-position:-1560px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/66.gif"
                                       data-title="爱心" style="background-position:-1584px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/67.gif"
                                       data-title="心碎" style="background-position:-1608px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/68.gif"
                                       data-title="蛋糕" style="background-position:-1632px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/69.gif"
                                       data-title="闪电" style="background-position:-1656px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/70.gif"
                                       data-title="炸弹" style="background-position:-1680px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/71.gif"
                                       data-title="刀" style="background-position:-1704px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/72.gif"
                                       data-title="足球" style="background-position:-1728px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/73.gif"
                                       data-title="瓢虫" style="background-position:-1752px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/74.gif"
                                       data-title="便便" style="background-position:-1776px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/75.gif"
                                       data-title="月亮" style="background-position:-1800px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/76.gif"
                                       data-title="太阳" style="background-position:-1824px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/77.gif"
                                       data-title="礼物" style="background-position:-1848px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/78.gif"
                                       data-title="拥抱" style="background-position:-1872px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/79.gif"
                                       data-title="强" style="background-position:-1896px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/80.gif"
                                       data-title="弱" style="background-position:-1920px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/81.gif"
                                       data-title="握手" style="background-position:-1944px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/82.gif"
                                       data-title="胜利" style="background-position:-1968px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/83.gif"
                                       data-title="抱拳" style="background-position:-1992px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/84.gif"
                                       data-title="勾引" style="background-position:-2016px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/85.gif"
                                       data-title="拳头" style="background-position:-2040px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/86.gif"
                                       data-title="差劲" style="background-position:-2064px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/87.gif"
                                       data-title="爱你" style="background-position:-2088px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/88.gif"
                                       data-title="NO" style="background-position:-2112px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/89.gif"
                                       data-title="OK" style="background-position:-2136px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/90.gif"
                                       data-title="爱情" style="background-position:-2160px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/91.gif"
                                       data-title="飞吻" style="background-position:-2184px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/92.gif"
                                       data-title="跳跳" style="background-position:-2208px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/93.gif"
                                       data-title="发抖" style="background-position:-2232px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/94.gif"
                                       data-title="怄火" style="background-position:-2256px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/95.gif"
                                       data-title="转圈" style="background-position:-2280px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/96.gif"
                                       data-title="磕头" style="background-position:-2304px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/97.gif"
                                       data-title="回头" style="background-position:-2328px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/98.gif"
                                       data-title="跳绳" style="background-position:-2352px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/99.gif"
                                       data-title="挥手" style="background-position:-2376px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/100.gif"
                                       data-title="激动" style="background-position:-2400px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/101.gif"
                                       data-title="街舞" style="background-position:-2424px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/102.gif"
                                       data-title="献吻" style="background-position:-2448px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/103.gif"
                                       data-title="左太极" style="background-position:-2472px 0;">
                                    </i>
                                </li>

                                <li class="emotions_item">
                                    <i class="js_emotion_i"
                                       data-gifurl="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/104.gif"
                                       data-title="右太极" style="background-position:-2496px 0;">
                                    </i>
                                </li>

                            </ul>
                            <span class="emotions_preview js_emotionPreviewArea"></span>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <div class="tab_content" style="display: none;" id="sendImage">
            <div class="js_imgArea inner ">
                <!--type 10图文 2图片  3语音 15视频 11商品消息-->

                <div class="tab_cont_cover jsMsgSendTab" id="selectImg" style="display: block;">
                    <div class="media_cover" id="selectImage">
			            <span class="create_access">
			                <a class="add_gray_wrp jsMsgSenderPopBt" href="javascript:;" data-type="2" data-index="2">
                                <i class="icon36_common add_gray"></i>
                                <strong>从素材库中选择</strong>
                            </a>
			            </span>
                    </div>
                    <div class="media_cover" id="uploadImage">
			            <span class="create_access webuploader-container">
			                <a id="uploadImgBt" class="add_gray_wrp webuploader-pick"  data-type="2"
                               href="javascript:;">
                                <i  class="icon36_common add_gray"></i>
                                <strong>上传图片</strong>
                            </a>
			            <ul class="upload_file_box" style="display: none;"></ul>
                            <div id="rt_rt_1as4595slb55vkp1kd75v6lhu1"
                                 style="position: absolute; top: 82px; left: 182.875px; width: 56px; height: 57px; overflow: hidden; bottom: auto; right: auto;">
                                <input onchange="msgSender.uploadImg()" id="fileInput" type="file" accept="image/*"
                                       style="display: none;">
                                <label id="msgSendImgUploadBt" style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);">
                                </label>
                            </div>
                        </span>
                    </div>
                </div>
                <div id="showImage" style="display: none">
                    <div class="appmsgSendedItem simple_img">
                        <a class="title_wrp"
                           href="javascript:;"
                           target="_blank">
                            <img id="imageMsg" class="js_media_img icon cover">
                            </img>
                            <span class="title">[图片]</span>
                        </a>
                    </div>
                    <a href="javascript:;" class="jsmsgSenderDelBt link_dele" id="delImage">删除</a>
                </div>

            </div>
        </div>

        <div class="tab_content" style="display: none;">
            <div class="js_audioArea inner ">
                <!--type 10图文 2图片  3语音 15视频 11商品消息-->

                <div class="tab_cont_cover jsMsgSendTab" data-index="3" data-type="3">
                    <div class="media_cover">
			            <span class="create_access">
			                <a class="add_gray_wrp jsMsgSenderPopBt" href="javascript:;" data-type="3" data-index="3">
                                <i class="icon36_common add_gray"></i>
                                <strong>从素材库中选择</strong>
                            </a>
			            </span>
                    </div>
                    <div class="media_cover">
			            <span class="create_access">
			                <a class="add_gray_wrp " id="msgSendAudioUploadBt" href="javascript:;">
                                <i class="icon36_common add_gray"></i>
                                <strong>新建语音</strong>
                            </a>
			            </span>
                    </div>
                </div>

            </div>
        </div>

        <div class="tab_content" style="display: none;">
            <div class="js_videoArea inner ">
                <!--type 10图文 2图片  3语音 15视频 11商品消息-->

                <div class="tab_cont_cover jsMsgSendTab" data-index="4">
                    <div class="media_cover">
			            <span class="create_access">
			                <a class="add_gray_wrp jsMsgSenderPopBt" href="javascript:;" data-type="15" data-index="4">
                                <i class="icon36_common add_gray"></i>
                                <strong>从素材库中选择</strong>
                            </a>
			            </span>
                    </div>
                    <div class="media_cover">
			            <span class="create_access">
			                <a target="_blank" class="add_gray_wrp"
                               href="https://mp.weixin.qq.com/cgi-bin/appmsg?t=media/videomsg_edit&amp;action=video_edit&amp;type=15&amp;lang=zh_CN&amp;token=1355973110">
                                <i class="icon36_common add_gray"></i>
                                <strong>新建视频</strong>
                            </a>
			            </span>
                    </div>
                </div>

            </div>
        </div>

    </div>
</div>
