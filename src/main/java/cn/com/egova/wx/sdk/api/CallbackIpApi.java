/**
 * Copyright (c) 2011-2015, Unas 小强哥 (unas@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.api;

import cn.com.egova.wx.sdk.util.HttpUtils;

/**
 * 获取微信服务器IP地址
 *
 * 如果公众号基于安全等考虑，需要获知微信服务器的IP地址列表，以便进行相关限制，可以通过该接口获得微信服务器IP地址列表。
 */
public class CallbackIpApi {
    private static String apiUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=";

    /**
     * 获取微信服务器IP地址
     * @return {ApiResult}
     */
    public static ApiResult getCallbackIp() {
        String jsonResult = HttpUtils.get(apiUrl + AccessTokenApi.getAccessTokenStr());
        return new ApiResult(jsonResult);
    }
}
