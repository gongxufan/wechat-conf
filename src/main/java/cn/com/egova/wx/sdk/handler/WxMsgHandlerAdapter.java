/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.handler;

import cn.com.egova.wx.sdk.msg.in.InImageMsg;
import cn.com.egova.wx.sdk.msg.in.InLinkMsg;
import cn.com.egova.wx.sdk.msg.in.InLocationMsg;
import cn.com.egova.wx.sdk.msg.in.InNotDefinedMsg;
import cn.com.egova.wx.sdk.msg.in.InShortVideoMsg;
import cn.com.egova.wx.sdk.msg.in.InTextMsg;
import cn.com.egova.wx.sdk.msg.in.InVideoMsg;
import cn.com.egova.wx.sdk.msg.in.InVoiceMsg;
import cn.com.egova.wx.sdk.msg.in.event.InCustomEvent;
import cn.com.egova.wx.sdk.msg.in.event.InFollowEvent;
import cn.com.egova.wx.sdk.msg.in.event.InLocationEvent;
import cn.com.egova.wx.sdk.msg.in.event.InMassEvent;
import cn.com.egova.wx.sdk.msg.in.event.InMenuEvent;
import cn.com.egova.wx.sdk.msg.in.event.InMerChantOrderEvent;
import cn.com.egova.wx.sdk.msg.in.event.InNotDefinedEvent;
import cn.com.egova.wx.sdk.msg.in.event.InPoiCheckNotifyEvent;
import cn.com.egova.wx.sdk.msg.in.event.InQrCodeEvent;
import cn.com.egova.wx.sdk.msg.in.event.InShakearoundUserShakeEvent;
import cn.com.egova.wx.sdk.msg.in.event.InSubmitMemberCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InTemplateMsgEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUpdateMemberCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUserPayFromCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUserViewCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InVerifyFailEvent;
import cn.com.egova.wx.sdk.msg.in.event.InVerifySuccessEvent;
import cn.com.egova.wx.sdk.msg.in.event.InWifiEvent;
import cn.com.egova.wx.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;

/**
 * WxMsgHandlerAdapter 对 WxMsgHandler 部分方法提供了默认实现，
 * 以便开发者不去关注 WxMsgHandler 中不需要处理的抽象方法，节省代码量
 */
public abstract class WxMsgHandlerAdapter extends WxMsgHandler {
    //  关注/取消关注事件
    protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);

    // 接收文本消息事件
    protected abstract void processInTextMsg(InTextMsg inTextMsg);

    // 自定义菜单事件
    protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);

    // 接收图片消息事件
    protected void processInImageMsg(InImageMsg inImageMsg) {
    }

    // 接收语音消息事件
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
    }

    // 接收视频消息事件
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {
    }

    // 接收地理位置消息事件
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {
    }

    // 接收链接消息事件
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {
    }

    // 扫描带参数二维码事件
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
    }

    // 上报地理位置事件
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
    }

    // 接收语音识别结果，与 InVoiceMsg 唯一的不同是多了一个 Recognition 标记
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
    }

    // 在模版消息发送任务完成后事件
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
    }

    // 群发完成事件
    protected void processInMassEvent(InMassEvent inMassEvent) {
    }

    // 接收小视频消息
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
    }

    // 接客服入会话事件
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {
    }

    // 用户进入摇一摇界面，在“周边”页卡下摇一摇时事件
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
    }

    // 资质认证事件
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
    }

    // 资质认证失败事件
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
    }

    // 门店在审核通过后下发消息事件
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {
    }

    // WIFI连网后下发消息 by unas at 2016-1-29
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {
    }

    // 微信会员卡二维码扫描领取事件
    @Override
    protected void processInUserViewCardEvent(InUserViewCardEvent msg) {
    }

    // 微信会员卡激活事件
    @Override
    protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent msg) {
    }

    // 微信会员卡积分变更事件
    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent msg) {
    }

    // 微信会员卡快速买单事件
    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent msg) {
    }

    // 微信小店订单支付成功接口事件
    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {
    }

    // 没有找到对应的事件消息
    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {
    }

    // 没有找到对应的消息
    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {
    }
}
