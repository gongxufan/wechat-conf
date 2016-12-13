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
          href="<%=request.getContextPath()%>/resources/material/page_user2f2f7f.css">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/material/pagination218878.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/userManage.js"></script>
</head>
<body class="zh_CN screen_small">
<div id="js_container_box" class="container_box cell_layout side_l">
<div class="col_main">
   <%-- <div class="main_hd">
        <div class="title_tab">
            <ul class="tab_navs title_tab">
                <li class="tab_nav first js_top selected"><a href="javascript:;" id="reloadTop">已关注</a></li>
                <li class="tab_nav js_top"><a href="javascript:;" id="blackTop">黑名单</a></li>
            </ul>
        </div>
    </div>--%>
    <div class="main_bd" style="padding: 30px">
        <div class="global_mod user_global_opr float_layout">
            <div class="global_info">
                <div class="search_bar" id="searchBar"><span class="frm_input_box search with_del append ">
    <a class="del_btn jsSearchInputClose" href="javascript:" style="display:none">
        <i class="icon_search_del"></i>&nbsp;
    </a>
    <a href="javascript:" id="searchUser" class="frm_input_append jsSearchInputBt">
    	<i class="icon16_common search_gray">搜索</i>&nbsp;
    </a>
    <input type="text" id="nickname" class="frm_input jsSearchInput" placeholder="用户昵称">
</span></div>
            </div>
            <div class="global_extra"><a href="javascript:;" data-y="3" class="btn btn_primary btn_add"
                                         id="addGroupBtn"><i class="icon14_common add_white"></i>新建分组</a></div>
        </div>
        <div class="inner_container_box side_r cell_layout">
            <div class="inner_main">
                <div class="bd">
                    <div class="global_mod user_group_opr"><span class="group_name" id="js_groupName">${groupName}</span>
                        <span class="js_groupCommand" <c:if test="${groupid == null || groupid == 0  || groupid == 1 || groupid == 2 }">style="display:none;"</c:if>>
                            <a groupid="${groupid}" groupname="${groupName}" id="renameGroupBtn" href="javascript:;" class="mod_link js_tag_edit_btn">重命名</a>
                            <a groupid="${groupid}" id="delGroupBtn" class="mod_link js_tag_del_btn" href="javascript:;">删除</a>
                        </span>
                    </div>
                    <div class="table_wrp user_list">
                        <table class="table" cellspacing="0">
                            <thead class="thead">
                            <tr>
                                <th class="table_cell user no_extra" colspan="2">
                                    <div class="group_select"><label blacklist="false" for="selectAll"
                                                                     class="group_select_label frm_checkbox_label"> <i
                                            class="icon_checkbox" for="selectAll"></i> <input onclick="return false;" type="checkbox" class="frm_checkbox"
                                                                              id="selectAll"> 全选 </label>&nbsp;
                                        <c:if test="${groupid != 1}">
                                        <a
                                            class="btn btn_default btn_disabled js_tag_putOn_btn" href="javascript:;">移动分组</a>
                                        <a class="btn btn_default btn_disabled js_tag_toBanList_btn"
                                           href="javascript:;" id="addBlacklist">加入黑名单</a>
                                        </c:if>
                                        <c:if test="${groupid == 1}">
                                            <a class="btn btn_default btn_disabled js_tag_toBanList_btn"
                                               href="javascript:;" id="unBlacklist">移出黑名单</a>
                                        </c:if>
                                    </div>

                                </th>
                            </tr>
                            </thead>
                            <tbody class="tbody" id="userGroups">
                            <c:forEach items="${userList}" var="user">
                                <tr>
                                    <td class="table_cell user">
                                        <div class="user_info">  <!--个人信息区-->
                                            <a target="_blank"
                                               href="javascript:;"
                                               class="remark_name" data-fakeid="o5FaswN50glzX2mpzonKcgDykY74">
                                                <c:if test="${user.remark != null}">${user.remark}(</c:if>
                                               ${user.nickname}
                                                <c:if test="${user.remark != null}">)</c:if>
                                            </a>
                                            <span class="nick_name" data-fakeid="o5FaswN50glzX2mpzonKcgDykY74"></span>

                                            <a target="_blank"
                                               href="javascript:;"
                                               class="avatar">
                                                <img src="${user.headimgurl}"
                                                     data-id="o5FaswN50glzX2mpzonKcgDykY74" class="js_msgSenderAvatar">
                                            </a>
                                            <label openid="${user.openid}" <c:if test="${user.groupid != 1 || groupid==1}">blacklist="false"</c:if> for="checkOne" class="frm_checkbox_label"><i
                                                    class="icon_checkbox" ></i><input onclick="return false" class="frm_checkbox js_select"
                                                                                     type="checkbox"
                                                                                     value="o5FaswN50glzX2mpzonKcgDykY74"
                                                                                     id="checko5FaswN50glzX2mpzonKcgDykY74"></label>

                                            <div class="js_tags user_tag_area">

                                                <span class="js_tags_list user_tag_list">
                                                    <c:forEach items="${groupList}" var="group">
                                                        <c:if test="${user.groupid == group.id}">
                                                            <!--未分组-->
                                                            <c:if test="${group.id == 0}">
                                                                ${group.name}
                                                            </c:if>
                                                            <c:if test="${group.id != 0}">
                                                            <a groupid="${group.id}" href="javascript:;" class="js_user_tags user_tag <c:if test="${group.id == 1}">black</c:if> ">${group.name}</a>
                                                            </c:if>
                                                            </c:if>
                                                    </c:forEach>
                                                </span>
                                                <span class="js_tags_btn dropdown_switch_area dropdown_closed">
                    <span class="icon_dropdown_switch">
                        <i class="arrow arrow_up"></i>
                        <i class="arrow arrow_down"></i>
                    </span>
                </span>

                                            </div>
                                        </div>
                                    </td>
                                    <td class="table_cell user_opr tr">

                                        <div id="selectAreao5FaswN50glzX2mpzonKcgDykY74" class="js_selectArea"
                                             data-gid=""
                                             data-fid="o5FaswN50glzX2mpzonKcgDykY74"></div>
                                        <a class="btn remark js_msgSenderRemark"
                                           data-fakeid="o5FaswN50glzX2mpzonKcgDykY74">修改备注</a>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                           <p class="no_result"  <c:if test="${fn:length(userList) > 0}">style="display: none;"</c:if>>无结果,请重新搜索或查看
                               <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow=1&count=10&groupName=全部用户" id="js_reload">全部用户
                               </a>
                           </p>

                    </div>
                    <div class="tool_area" <c:if test="${fn:length(userList) == 0}"> style="display:none"</c:if>>
                        <div class="pagination_wrp js_pageNavigator">

                            <div class="pagination" id="wxPagebar_1472199068822">
    <span class="page_nav_area">
        <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
         <c:if test="${pageNow <= pages && pageNow > 1}">
             <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow=${pageNow -1}&count=10&groupName=${groupName}" class="btn page_prev"><i class="arrow"></i></a>
         </c:if>
            <span class="page_num">
                <label>${pageNow}</label>
                <span class="num_gap">/</span>
                <label>${pages}</label>
            </span>
        <c:if test="${pageNow < pages}">
            <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow=${pageNow +1}&count=10&groupName=${groupName}" class="btn page_next"><i class="arrow"></i></a>
        </c:if>

        <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>
    </span>

                                <span class="goto_area">
        <input type="text" id="goPage">
        <a href="javascript:void(0);" class="btn page_go" onclick="javascript:if(!document.getElementById('goPage').value || document.getElementById('goPage').value < 1 || document.getElementById('goPage').value >${pages})return;document.location.href='<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow='+document.getElementById('goPage').value+'&count=10&groupName=${groupName}';">跳转</a>
    </span>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="inner_side">
                <div class="bd">
                    <div class="group_list">
                        <div class="inner_menu_box" id="groupsList">
                            <dl class="inner_menu">
                                <dt class="inner_menu_item <c:if test="${groupid == null}">selected</c:if>">
                                    <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?pageNow=1&count=10&groupName=全部用户" class="inner_menu_link js_group_link"
                                       title="全部用户">
                                        <strong>全部用户</strong><em class="num">(${totalGroups})</em>
                                    </a>
                                </dt>
                                <c:forEach items="${groupList}" var="group">
                                   <c:if test="${group.id != 1}">
                                       <dd class="inner_menu_item <c:if test="${groupid == group.id}">selected</c:if>" id="${group.id}">

                                           <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?groupid=${group.id}&pageNow=1&count=10&groupName=${group.name}" class="inner_menu_link js_group_link"
                                              <c:if test="${group.id ==2}">title="加入该分组中的用户仅作为更重要的用户归类标识"</c:if>>
                                               <strong>${group.name}</strong><em class="num">(${group.count})</em>
                                           </a>
                                       </dd>
                                   </c:if>
                                </c:forEach>
                            </dl>
                            <dl class="inner_menu no_extra">
                                <c:forEach items="${groupList}" var="group">
                                    <c:if test="${group.id == 1}">
                                        <dt class="inner_menu_item  <c:if test="${groupid == group.id}">selected</c:if>">
                                            <a href="<%=request.getContextPath()%>/weixin/api/fetchUserList?groupid=${group.id}&pageNow=1&count=10&groupName=${group.name}" class="inner_menu_link js_group_link" data-id="-1"
                                               title="加入该分组中的用户将无法接收到该公众号发送的消息以及自动回复。公众号也无法向该用户发送消息。">
                                                <strong>${group.name}</strong><em class="num">(${group.count})</em>
                                            </a>
                                        </dt>
                                    </c:if>
                                </c:forEach>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!--用户详情-->
<div class="rich_buddy popover" style="display: none">
    <div class="popover_inner">
        <div class="popover_content">
            <div class="loadingArea rich_buddy_loading" style="display: none;"><span class="vm_box"></span><i class="icon_loading_small white"></i></div>
            <div class="rich_buddy_bd buddyRichContent" style="display: block;"><img class="rich_user_avatar" src="http://wx.qlogo.cn/mmopen/Q3auHgzwzM7PYmF760fibicOf4bEQoNJdhOjALkOxDFxgGdSJyKG7ecXcMfs3tmlVCEZELnYKsWgcIU6IkBElDG8e59z4MBhvTjLxMH4jI1CM/0" alt="">
                <div class="rich_user_info">
                    <div class="rich_user_info_inner">
                        <div class="global_mod float_layout gap_top">
                            <strong class="global_info gap_top_item">
                                肖新平
                                <span class="icon_rich_user_sex icon18_common man_blue"></span>
                            </strong>
                            <div class="global_extra">
                                <a class="btn btn_default  js_popAddToBlackList" data-id="o5FaswGPdk9BbDbL8HDSqULcCJko" href="javascript:;">加入黑名单</a>
                            </div>
                        </div>


                        <div class="frm_control_group remark">
                            <label class="frm_label">备注</label>
                            <div class="frm_controls">  <!--在卡片中，修改备注不再弹窗，input默认隐藏-->
                                <span class="js_remarkName remark_name"></span>
                                <a title="修改备注" class="icon14_common edit_gray js_changeRemark" href="javascript:;">修改备注</a>
                                <span class="remark_input frm_input_box with_counter counter_in append js_remarkNameBox" style="display:none;">
                    <input type="text" class="frm_input js_remarkName_input" value="">
                    <em class="frm_input_append frm_counter">0/30</em>
                </span>
                            </div>
                        </div>

                        <div class="frm_control_group location">
                            <label class="frm_label">地区</label>
                            <div class="frm_controls">
                                中国 湖南 娄底
                            </div>
                        </div>
                        <!--
                        <div class="frm_control_group sign">
                            <label class="frm_label">签名</label>
                            <div class="frm_controls frm_vertical_pt">

                            </div>
                        </div>
                        -->

                        <div class="frm_control_group grouping tag_group js_group_container">
                            <label class="frm_label">分组</label>
                            <div class="frm_controls">
                                <div class="js_tags user_tag_area">

                                    <span class="user_tag_list"></span>
                                    <span class="js_buddy_tags_btn dropdown_switch_area dropdown_closed" data-id="o5FaswGPdk9BbDbL8HDSqULcCJko">
                        无分组
                        <span class="icon_dropdown_switch">
                            <i class="arrow arrow_up"></i>
                            <i class="arrow arrow_down"></i>
                        </span>
                    </span>

                                </div>
                            </div>
                        </div>

                        <div class="frm_control_group">
                            <label class="frm_label">互动</label>
                            <div class="frm_controls">
                                <div class="rich_data_meta">消息0</div>


                            </div>
                        </div>
                        <div class="rich_data_bar">
                            <div class="rich_data_meta extra">

                                2016-08-01关注

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="popover  pos_center" style="display: none;" id="moveGroup" name="popOver">
    <input type="hidden" id="moveGroupId" value="${group_id}"/>
    <input type="hidden" id="clickPlace" value="${group_id}"/>
    <input type="hidden" id="openid" value="${group_id}"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">
            <div class="frm_control group_select" style="float: none">
                <c:forEach items="${groupList}" var="group">
                    <c:if test="${group.id != 0 && group.id !=1}">
                        <label class="frm_radio_label <c:if test="${groupid == group.id}">selected</c:if>"><input type="radio" class="frm_radio" value="${group.id}"><i class="icon_radio"></i><span class="lbl_content">${group.name}</span></label>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="popover_bar"><a id="confirmMove" href="javascript:;" class="btn btn_primary jsPopoverBt">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleMove">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none" id="editRemark">
    <input type="hidden" id="openid4remark"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">备注名称
            <span class="frm_input_box with_counter counter_in append count" id="remarkSpan">
        <input type="text" class="frm_input js_remarkName_input" value="" id="remark">
        <em class="frm_input_append frm_counter" id="leftRemark">0/30</em>
    </span>
        </div>
        <div class="popover_bar"><a href="javascript:;" class="btn btn_primary jsPopoverBt" id="confirmRemark">确定</a>&nbsp;
            <a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleRemark">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none;" id="add2Blacklist">
    <input type="hidden" id="blacklistType"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent" id="blacklistInfo"></div>
        <div class="popover_bar"><a href="javascript:;" class="btn btn_primary jsPopoverBt" id="confirmBlacklist">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleBlacklist">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>

<div class="popover  pos_center" style="display: none;" id="addGroup">
    <input type="hidden" id="groupAction"/>
    <input type="hidden" id="groupId"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent"><h4 class="popover_title">分组名称</h4>
            <span class="frm_input_box with_counter counter_in append count">
        <input type="text" class="frm_input js_name" id="groupName"/>
        <em class="frm_input_append frm_counter js_counter" style="display: none;">0/6</em>
    </span>
            <span class="js_tips frm_msg fail" style="display:none;"></span></div>
        <div class="popover_bar"><a href="javascript:;" class="btn btn_primary jsPopoverBt" id="confirmAddGroup">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleAddGroup">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>
<div class="popover  pos_center" style="display: none;" id="delGroup">
    <input type="hidden" id="delGroupId"/>
    <div class="popover_inner">
        <div class="popover_content jsPopOverContent">删除分组其下的用户会进入默认的分组，确定要删除该分组吗？</div>
        <div class="popover_bar"><a href="javascript:;" class="btn btn_primary jsPopoverBt" id="confirmDel">确定</a>&nbsp;<a href="javascript:;" class="btn btn_default jsPopoverBt" id="cancleDel">取消</a></div>
    </div>
    <i class="popover_arrow popover_arrow_out"></i>
    <i class="popover_arrow popover_arrow_in"></i>
</div>
<div  class="JS_TIPS page_tips error" id="wxTips" style="opacity: 0.414035;display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>

</body>
</html>