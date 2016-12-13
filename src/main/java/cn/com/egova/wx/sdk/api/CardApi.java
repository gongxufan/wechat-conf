package cn.com.egova.wx.sdk.api;

import cn.com.egova.wx.sdk.util.HttpUtils;

/**
 * 会员卡相关接口
 * Created by L.cm on 2016/6/16.
 */
public class CardApi {
    private static String cardCreateUrl = "https://api.weixin.qq.com/card/create?access_token=";

    /**
     * 创建会员卡接口
     *
     * @param jsonStr JSON数据
     * @return {ApiResult}
     */
    public static ApiResult create(String jsonStr) {
        String jsonResult = HttpUtils.post(cardCreateUrl + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    // https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN
}
