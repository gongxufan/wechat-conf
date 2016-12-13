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
          href="<%=request.getContextPath()%>/resources/material/mass_list2f613f.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/emoji218878.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
</head>
<body class="zh_CN screen_small">
<div class="col_main">
    <div class="main_hd">
        <div class="title_tab" id="topTab">
            <ul class="tab_navs title_tab" data-index="0">

                <li data-index="0" class="tab_nav first js_top " data-id="send"><a
                        href="<%=request.getContextPath()%>/wx/groupMessage/">新建群发消息</a>
                </li>


                <li data-index="1" class="tab_nav  js_top selected" data-id="list"><a
                        href="<%=request.getContextPath()%>/wx/groupMessage/initMessageSent">已发送</a>
                </li>


            </ul>
        </div>
        <div class="extra_info mini_tips icon_after"><a href="http://kf.qq.com/faq/120911VrYVrA131025QniAfu.html"
                                                        target="_blank">群发消息规则说明<i
                class="icon_mini_tips document_link"></i></a></div>
    </div>
    <div class="main_bd">
        <div class="table_wrp mass_history">
            <table class="table" cellspacing="0">
                <tbody class="tbody" id="masslist">
                <tr class="mass_item">
                    <td class="table_cell tl">消息类型</td>
                    <td class="table_cell tl">发送状态</td>
                    <td class="table_cell mass_info last_child">发送设置</td>
                </tr>
                <tr class="mass_item" id="massItem1000000006">
                    <td class="table_cell tl mass_content">

                        <p class="mass_multi_appmsg_num_wrp">
                            多图文(<span>2</span>)
                        </p>

                        <div id="file1000000006" data-id="1000000006" class="mass_wrp  "><div class="multiple_appmsg_wrp">

                            <div class="appmsgSendedItem multiple_appmsg">

                                <a class="title_wrp" href="http://mp.weixin.qq.com/s?__biz=MzIzMjUxOTkwOA==&amp;mid=2247483686&amp;idx=1&amp;sn=3390bdd2a6985149cf340179c24d3025&amp;chksm=e892e0addfe569bbaf10564c8088d135d4c3323fba9c677e27770f7152455e5eec961da48f2a#rd" target="_blank">

                                    <span class="icon icon_lh cover" style="background-image:url(https://mmbiz.qlogo.cn/mmbiz_png/hianb7pP580e4BPgtriagrEBB9U1iaCe6lO0n1iclQHMQfZrZ53gBIdaeJAuKaFtydB40AvgxCsljOzROKBeP1qGng/0?wx_fmt=png);"></span>
                                    <span class="title">[图文1]多图文消息</span>
                                </a>



                                <div class="desc">
                                    <div>
                                        <span>阅读 0</span>
                                        <span>点赞 0</span>
                                    </div>

                                    <div>



                                    </div>

                                    <div>



                                    </div>
                                </div>
                            </div>

                            <div class="appmsgSendedItem multiple_appmsg">

                                <a class="title_wrp" href="http://mp.weixin.qq.com/s?__biz=MzIzMjUxOTkwOA==&amp;mid=2247483686&amp;idx=2&amp;sn=74d98a77401d1217d7f915fb427c9275&amp;chksm=e892e0addfe569bb87c8bf4077f918a18a2674a6b7af0309b998d78ae6542236e185c201cd54#rd" target="_blank">

                                    <span class="icon icon_lh cover" style="background-image:url(https://mmbiz.qlogo.cn/mmbiz_jpg/hianb7pP580e4BPgtriagrEBB9U1iaCe6lOWjh0PayxPKzb4nAqUaibfJhbA69q3kYKEia50TN4OUqKaIEs4icAxIxZw/0?wx_fmt=jpeg);"></span>
                                    <span class="title">[图文2]子图文消息</span>
                                </a>



                                <div class="desc">
                                    <div>
                                        <span>阅读 0</span>
                                        <span>点赞 0</span>
                                    </div>

                                    <div>



                                    </div>

                                    <div>



                                    </div>
                                </div>
                            </div>

                        </div></div>
                    </td>
                    <td class="table_cell tl mass_opr" data-status="102" data-type="9">
                        <span class="js_status mass_opr_meta status js_result " data-id="1000000006">发送完毕<i class="icon_arrow_down"></i></span>
                        <span class="js_desc mass_opr_meta desc"></span>


                    </td>
                    <td class="table_cell mass_info last_child">
                        <p class="mass_time">09:45</p>


                        <p><span class="js_group_label">标签</span>：new</p>







                    </td>
                </tr>
                <tr class="mass_item" id="massItem1000000004">
                    <td class="table_cell tl mass_content">

                        <div id="file1000000004" data-id="1000000004" class="mass_wrp  text"><!--群发消息-已发送页面文字模板-->
                            <div class="appmsgSendedItem textmsg">
                                <div class="title_wrp">
                                    <span class="icon"></span>
                                    <span class="title">[文字]1111
22222
/::&lt;</span>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td class="table_cell tl mass_opr" data-status="2" data-type="1">
                        <span class="js_status mass_opr_meta status js_result " data-id="1000000004">发送完毕<i
                                class="icon_arrow_down"></i></span>
                        <span class="js_desc mass_opr_meta desc"></span>


                        <p class="mass_opr_del"><a class="js_del" data-id="1000000004" data-index="0" data-type="1"
                                                   href="javascript:void(0);">删除</a></p>


                    </td>
                    <td class="table_cell mass_info last_child">
                        <p class="mass_time">09:35</p>


                        <p>全部用户</p>


                    </td>
                </tr>

                <tr class="mass_item" id="massItem1000000002">
                    <td class="table_cell tl mass_content">

                        <div id="file1000000002" data-id="1000000002" class="mass_wrp  ">
                            <div class="multiple_appmsg_wrp">

                                <div class="appmsgSendedItem multiple_appmsg">

                                    <a class="title_wrp"
                                       href="http://mp.weixin.qq.com/s?__biz=MzIzMjUxOTkwOA==&amp;mid=2247483670&amp;idx=1&amp;sn=a4103f635595d5de42206c5d53c904d6#rd"
                                       target="_blank">

                                        <span class="icon icon_lh cover"
                                              style="background-image:url(https://mmbiz.qlogo.cn/mmbiz_jpg/hianb7pP580eFMZTvZf1PMrxKcnqM9XO1HaGbIc7UIfWNErx48HKibRhSngARntDAtu3wgXFzlgUm4ZdOnDHPRgw/0?wx_fmt=jpeg);"></span>
                                        <span class="title">[图文1]看这里还有这里</span>
                                    </a>


                                    <div class="desc">
                                        <div>
                                            <span>阅读 1</span>
                                            <span>点赞 0</span>
                                        </div>

                                        <div>


                                        </div>

                                        <div>


                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </td>
                    <td class="table_cell tl mass_opr" data-status="2" data-type="9">
                        <span class="js_status mass_opr_meta status js_result " data-id="1000000002">发送完毕<i
                                class="icon_arrow_down"></i></span>
                        <span class="js_desc mass_opr_meta desc"></span>


                        <p class="mass_opr_del"><a class="js_del" data-id="1000000002" data-index="1" data-type="9"
                                                   href="javascript:void(0);">删除</a></p>


                    </td>
                    <td class="table_cell mass_info last_child">
                        <p class="mass_time">08月17日</p>


                        <p>性别：女</p>


                    </td>
                </tr>

                <tr class="mass_item" id="massItem1000000001">
                    <td class="table_cell tl mass_content">

                        <div id="file1000000001" data-id="1000000001" class="mass_wrp  ">
                            <div class="multiple_appmsg_wrp">

                                <div class="appmsgSendedItem multiple_appmsg">

                                    <a class="title_wrp"
                                       href="http://mp.weixin.qq.com/s?__biz=MzIzMjUxOTkwOA==&amp;mid=2247483652&amp;idx=1&amp;sn=1d2e79467f8ce593d3750bb04489e14a#rd"
                                       target="_blank">

                                        <span class="icon icon_lh cover"
                                              style="background-image:url(https://mmbiz.qlogo.cn/mmbiz/hianb7pP580dZTro1G17icNOKdibNicibGyFCRWicg547SCKRXSLPNicwgAt8ngpPFdicyl3Oa7ZC6EXRsnrr31hZicec5w/0?wx_fmt=jpeg);"></span>
                                        <span class="title">[图文1]Mysql Access denied for user root@localhost的简单处理方法</span>
                                    </a>


                                    <div class="desc">
                                        <div>
                                            <span>阅读 2</span>
                                            <span>点赞 0</span>
                                        </div>

                                        <div>


                                        </div>

                                        <div>


                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </td>
                    <td class="table_cell tl mass_opr" data-status="2" data-type="9">
                        <span class="js_status mass_opr_meta status js_result " data-id="1000000001">发送完毕<i
                                class="icon_arrow_down"></i></span>
                        <span class="js_desc mass_opr_meta desc"></span>


                        <p class="mass_opr_del"><a class="js_del" data-id="1000000001" data-index="2" data-type="9"
                                                   href="javascript:void(0);">删除</a></p>


                    </td>
                    <td class="table_cell mass_info last_child">
                        <p class="mass_time">08月01日</p>


                        <p>全部用户</p>


                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="tool_area">
            <div class="pagination_wrp pageNavigator" style="display: none;">
                <div class="pagination" id="wxPagebar_1473663627872">
    <span class="page_nav_area">
        <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
        <a href="javascript:void(0);" class="btn page_prev" style="display: none;"><i class="arrow"></i></a>

            <span class="page_num">
                <label>1</label>
                <span class="num_gap">/</span>
                <label>1</label>
            </span>

        <a href="javascript:void(0);" class="btn page_next" style="display: none;"><i class="arrow"></i></a>
        <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>
    </span>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>