/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.api;

import cn.com.egova.wx.sdk.kit.ParaMap;
import cn.com.egova.wx.sdk.util.HttpUtils;
import cn.com.egova.wx.sdk.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 API
 * <pre>
 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&amp;openid=OPENID&amp;lang=zh_CN
 * </pre>
 */
public class UserApi {

    private static String getUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
    private static String getFollowers = "https://api.weixin.qq.com/cgi-bin/user/get";
    private static String batchGetUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";
    private static String batchblacklist = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=";
    private static String batchunblacklist = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=";

    /**
     * 批量拉黑用户，一次拉黑最多允许20个
     * @param jsonStr
     * @return
     */
    public static ApiResult batchBlacklist(String jsonStr){
        String jsonResult = HttpUtils.post(batchblacklist + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    /**
     * 批量取消拉黑用户.一次取消拉黑最多允许20个
     * @param jsonStr
     * @return
     */
    public static ApiResult batchUnBlacklist(String jsonStr){
        String jsonResult = HttpUtils.post(batchblacklist + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }
    /**
     * 获取用户基本信息（包括UnionID机制）
     * @param openId 普通用户的标识，对当前公众号唯一
     * @return ApiResult
     */
    public static ApiResult getUserInfo(String openId) {
        ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr()).put("openid", openId).put("lang", "zh_CN");
        return new ApiResult(HttpUtils.get(getUserInfo, pm.getData()));
    }

    /**
     * 获取用户列表
     * @param nextOpenid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return ApiResult
     */
    public static ApiResult getFollowers(String nextOpenid) {
        ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr());
        if (nextOpenid != null)
            pm.put("next_openid", nextOpenid);
        return new ApiResult(HttpUtils.get(getFollowers, pm.getData()));
    }

    /**
     * 获取用户列表
     * @return ApiResult
     */
    public static ApiResult getFollows() {
        return getFollowers(null);
    }

    /**
     * 批量获取用户基本信息, by Unas
     * @param jsonStr json字符串
     * @return ApiResult
     */
    public static ApiResult batchGetUserInfo(String jsonStr) {
        String jsonResult = HttpUtils.post(batchGetUserInfo + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    /**
     * 批量获取用户基本信息
     * @param openIdList openid列表
     * @return ApiResult
     */
    public static ApiResult batchGetUserInfo(List<String> openIdList) {
        Map<String, List<Map<String, Object>>> userListMap = new HashMap<String, List<Map<String, Object>>>();

        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
        for (String openId : openIdList) {
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("openid", openId);
            mapData.put("lang", "zh_CN");
            userList.add(mapData);
        }
        userListMap.put("user_list", userList);

        return batchGetUserInfo(JsonUtils.toJson(userListMap));
    }

    private static String updateRemarkUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";

    /**
     * 设置备注名
     * @param openid 用户标识
     * @param remark 新的备注名，长度必须小于30字符
     * @return {ApiResult}
     */
    public static ApiResult updateRemark(String openid, String remark) {
        String url = updateRemarkUrl + AccessTokenApi.getAccessTokenStr();

        Map<String, String> mapData = new HashMap<String, String>();
        mapData.put("openid", openid);
        mapData.put("remark", remark);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(mapData));

        return new ApiResult(jsonResult);
    }
}
