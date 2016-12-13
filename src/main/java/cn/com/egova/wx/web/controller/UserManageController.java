package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.web.entity.User;
import cn.com.egova.wx.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gongxufan on 2016/9/12.
 */
@Controller
@RequestMapping("/wx/userManage")
public class UserManageController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String userManage(HttpServletRequest request,@RequestParam("pageNow") int pageNow,@RequestParam("count") int count
            ,@RequestParam(value = "groupid",required = false) String  groupid) {
        int offset = (pageNow - 1) * count;
        try {
            List<User> userList = userService.fetchUserList(String.valueOf(count),String.valueOf(offset),groupid,"groupid",groupid,null,null);
            request.setAttribute("userList",userList);
            request.setAttribute("pageNow",pageNow);
            int total = userService.getTotalUsers(groupid);
            request.setAttribute("total_count", total);
            int allPages = total % count == 0 ?total/count:total/count+1;
            request.setAttribute("pages", allPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "userManage";
    }
}
