/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.api;

import cn.com.egova.wx.sdk.util.HttpUtils;

/**
 * 模板消息 API
 * 文档地址：http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 */
public class TemplateMsgApi {

    private static String sendApiUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 发送模板消息
     * @param jsonStr json字符串
     * @return {ApiResult}
     */
    public static ApiResult send(String jsonStr) {
        String jsonResult = HttpUtils.post(sendApiUrl + AccessTokenApi.getAccessToken().getAccessToken(), jsonStr);
        return new ApiResult(jsonResult);
    }
}


