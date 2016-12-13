/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.msg;

import cn.com.egova.wx.sdk.msg.in.InImageMsg;
import cn.com.egova.wx.sdk.msg.in.InLinkMsg;
import cn.com.egova.wx.sdk.msg.in.InLocationMsg;
import cn.com.egova.wx.sdk.msg.in.InMsg;
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
import cn.com.egova.wx.sdk.msg.in.event.InShakearoundUserShakeEvent.AroundBeacon;
import cn.com.egova.wx.sdk.msg.in.event.InSubmitMemberCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InTemplateMsgEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUpdateMemberCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUserPayFromCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InUserViewCardEvent;
import cn.com.egova.wx.sdk.msg.in.event.InVerifyFailEvent;
import cn.com.egova.wx.sdk.msg.in.event.InVerifySuccessEvent;
import cn.com.egova.wx.sdk.msg.in.event.InWifiEvent;
import cn.com.egova.wx.sdk.msg.in.event.ScanCodeInfo;
import cn.com.egova.wx.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import cn.com.egova.wx.sdk.util.XmlHelper;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class InMsgParser {
    private InMsgParser() {
    }

    /**
     * 从 xml 中解析出各类消息与事件
     * @param xml xml字符串
     * @return {InMsg}
     */
    public static InMsg parse(String xml) {
        XmlHelper xmlHelper = XmlHelper.of(xml);
        return doParse(xmlHelper);
    }

    /**
     * 消息类型
     * 1：text 文本消息
     * 2：image 图片消息
     * 3：voice 语音消息
     * 4：video 视频消息
     *	shortvideo 小视频消息
     * 5：location 地址位置消息
     * 6：link 链接消息
     * 7：event 事件
     */
    private static InMsg doParse(XmlHelper xmlHelper) {
        String toUserName = xmlHelper.getString("//ToUserName");
        String fromUserName = xmlHelper.getString("//FromUserName");
        Integer createTime = xmlHelper.getNumber("//CreateTime").intValue();
        String msgType = xmlHelper.getString("//MsgType");
        if ("text".equals(msgType))
            return parseInTextMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("image".equals(msgType))
            return parseInImageMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("voice".equals(msgType))
            return parseInVoiceMsgAndInSpeechRecognitionResults(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("video".equals(msgType))
            return parseInVideoMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("shortvideo".equals(msgType))     //支持小视频
            return parseInShortVideoMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("location".equals(msgType))
            return parseInLocationMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("link".equals(msgType))
            return parseInLinkMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        if ("event".equals(msgType))
            return parseInEvent(xmlHelper, toUserName, fromUserName, createTime, msgType);

        LogKit.error("无法识别的消息类型 " + msgType + "，请查阅微信公众平台开发文档");
        return parseInNotDefinedMsg(toUserName, fromUserName, createTime, msgType);
    }

    private static InMsg parseInNotDefinedMsg(String toUserName, String fromUserName, Integer createTime, String msgType) {
        InNotDefinedMsg msg = new InNotDefinedMsg(toUserName, fromUserName, createTime, msgType);
        return msg;
    }

    private static InMsg parseInTextMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InTextMsg msg = new InTextMsg(toUserName, fromUserName, createTime, msgType);
        msg.setContent(xmlHelper.getString("//Content"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    private static InMsg parseInImageMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InImageMsg msg = new InImageMsg(toUserName, fromUserName, createTime, msgType);
        msg.setPicUrl(xmlHelper.getString("//PicUrl"));
        msg.setMediaId(xmlHelper.getString("//MediaId"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    private static InMsg parseInVoiceMsgAndInSpeechRecognitionResults(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        String recognition = xmlHelper.getString("//Recognition");
        String mediaId = xmlHelper.getString("//MediaId");
        String format = xmlHelper.getString("//Format");
        String msgId = xmlHelper.getString("//MsgId");
        if (StrKit.isBlank(recognition)) {
            InVoiceMsg msg = new InVoiceMsg(toUserName, fromUserName, createTime, msgType);
            msg.setMediaId(mediaId);
            msg.setFormat(format);
            msg.setMsgId(msgId);
            return msg;
        } else {
            InSpeechRecognitionResults msg = new InSpeechRecognitionResults(toUserName, fromUserName, createTime, msgType);
            msg.setMediaId(mediaId);
            msg.setFormat(format);
            msg.setMsgId(msgId);
            // 与 InVoiceMsg 唯一的不同之处
            msg.setRecognition(recognition);
            return msg;
        }
    }

    private static InMsg parseInVideoMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InVideoMsg msg = new InVideoMsg(toUserName, fromUserName, createTime, msgType);
        msg.setMediaId(xmlHelper.getString("//MediaId"));
        msg.setThumbMediaId(xmlHelper.getString("//ThumbMediaId"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    private static InMsg parseInShortVideoMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InShortVideoMsg msg = new InShortVideoMsg(toUserName, fromUserName, createTime, msgType);
        msg.setMediaId(xmlHelper.getString("//MediaId"));
        msg.setThumbMediaId(xmlHelper.getString("//ThumbMediaId"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    private static InMsg parseInLocationMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InLocationMsg msg = new InLocationMsg(toUserName, fromUserName, createTime, msgType);
        msg.setLocation_X(xmlHelper.getString("//Location_X"));
        msg.setLocation_Y(xmlHelper.getString("//Location_Y"));
        msg.setScale(xmlHelper.getString("//Scale"));
        msg.setLabel(xmlHelper.getString("//Label"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    private static InMsg parseInLinkMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InLinkMsg msg = new InLinkMsg(toUserName, fromUserName, createTime, msgType);
        msg.setTitle(xmlHelper.getString("//Title"));
        msg.setDescription(xmlHelper.getString("//Description"));
        msg.setUrl(xmlHelper.getString("//Url"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }

    // 解析各种事件
    private static InMsg parseInEvent(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        String event = xmlHelper.getString("//Event");
        String eventKey = xmlHelper.getString("//EventKey");

        /**
         * 取消关注事件
         * 注意：由于微信平台出现bug， unsubscribe 事件在有些时候居然多出了 eventKey 值，
         * 多出来的eventKey值例如：last_trade_no_4003942001201604205023621558
         *     所以 unsubscribe 与 subscribe 判断进行了拆分，并且将 unsubscribe
         *     挪至最前面进行判断，以便消除微信平台 bug 的影响
         */
        if ("unsubscribe".equals(event)) {
            return new InFollowEvent(toUserName, fromUserName, createTime, msgType, event);
        }

        // 扫描带参数二维码事件之一		1: 用户未关注时，进行关注后的事件推送
        String ticket = xmlHelper.getString("//Ticket");
        if ("subscribe".equals(event) && StrKit.notBlank(eventKey) && eventKey.startsWith("qrscene_")) {
            InQrCodeEvent e = new InQrCodeEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            e.setTicket(ticket);
            return e;
        }
        // 扫描带参数二维码事件之二		2: 用户已关注时的事件推送
        if ("SCAN".equals(event)) {
            InQrCodeEvent e = new InQrCodeEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            e.setTicket(ticket);
            return e;
        }

        /**
         * 关注事件，包括二维码扫描关注。(二维码扫描关注事件与扫描带参数二维码事件是两回事，虽然事件类型同为 subscribe)
         * 注意：由于微信平台出现bug， subscribe/unsubscribe 事件在有些时候居然多出了 eventKey 值，
         *     所以注掉了对于 eventKey 的 isBlank 判断，并且将判断挪到了扫描带二维码事件之一的后面，以便
         *     消除微信平台 bug 的影响
         */
        if ("subscribe".equals(event) /*&& StrKit.isBlank(eventKey)*/) {
            return new InFollowEvent(toUserName, fromUserName, createTime, msgType, event);
        }

        // 上报地理位置事件
        if ("LOCATION".equals(event)) {
            InLocationEvent e = new InLocationEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setLatitude(xmlHelper.getString("//Latitude"));
            e.setLongitude(xmlHelper.getString("//Longitude"));
            e.setPrecision(xmlHelper.getString("//Precision"));
            return e;
        }
        // 自定义菜单事件之一			1：点击菜单拉取消息时的事件推送
        if ("CLICK".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // 自定义菜单事件之二			2：点击菜单跳转链接时的事件推送
        if ("VIEW".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // 扫码推事件 和 扫码推事件且弹出“消息接收中”提示框
        if ("scancode_push".equals(event) || "scancode_waitmsg".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            String scanType = xmlHelper.getString("//ScanCodeInfo/ScanType");
            String scanResult = xmlHelper.getString("//ScanCodeInfo/ScanResult");
            e.setScanCodeInfo(new ScanCodeInfo(scanType, scanResult));
            return e;
        }
        // 5. pic_sysphoto：弹出系统拍照发图，这个后台其实收不到该菜单的消息，点击它后，调用的是手机里面的照相机功能，而照相以后再发过来时，就收到的是一个图片消息了
        if ("pic_sysphoto".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // pic_photo_or_album：弹出拍照或者相册发图
        if ("pic_photo_or_album".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // pic_weixin：弹出微信相册发图器
        if ("pic_weixin".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // location_select：弹出地理位置选择器
        if ("location_select".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // media_id：下发消息（除文本消息）
        if ("media_id".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // view_limited：跳转图文消息URL
        if ("view_limited".equals(event)) {
            InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setEventKey(eventKey);
            return e;
        }
        // 模板消息是否送达成功通知事件
        if ("TEMPLATESENDJOBFINISH".equals(event)) {
            InTemplateMsgEvent e = new InTemplateMsgEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setMsgId(xmlHelper.getString("//MsgID"));
            e.setStatus(xmlHelper.getString("//Status"));
            return e;
        }
        // 群发任务结束时是否送达成功通知事件
        if ("MASSSENDJOBFINISH".equals(event)) {
            InMassEvent e = new InMassEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setMsgId(xmlHelper.getString("//MsgID"));
            e.setStatus(xmlHelper.getString("//Status"));
            e.setTotalCount(xmlHelper.getString("//TotalCount"));
            e.setFilterCount(xmlHelper.getString("//FilterCount"));
            e.setSentCount(xmlHelper.getString("//SentCount"));
            e.setErrorCount(xmlHelper.getString("//ErrorCount"));
            return e;
        }
        // 多客服接入会话事件
        if ("kf_create_session".equals(event)) {
            InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setKfAccount(xmlHelper.getString("//KfAccount"));
            return e;
        }
        // 多客服关闭会话事件
        if ("kf_close_session".equals(event)) {
            InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setKfAccount(xmlHelper.getString("//KfAccount"));
            return e;
        }
        // 多客服转接会话事件
        if ("kf_switch_session".equals(event)) {
            InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setKfAccount(xmlHelper.getString("//KfAccount"));
            e.setToKfAccount(xmlHelper.getString("//ToKfAccount"));
            return e;
        }
        // 微信摇一摇事件
        if ("ShakearoundUserShake".equals(event)) {
            InShakearoundUserShakeEvent e = new InShakearoundUserShakeEvent(toUserName, fromUserName, createTime, msgType);
            e.setEvent(event);
            e.setUuid(xmlHelper.getString("//ChosenBeacon/Uuid"));
            e.setMajor(xmlHelper.getNumber("//ChosenBeacon/Major").intValue());
            e.setMinor(xmlHelper.getNumber("//ChosenBeacon/Minor").intValue());
            e.setDistance(xmlHelper.getNumber("//ChosenBeacon/Distance").floatValue());

            NodeList nodeList = xmlHelper.getNodeList("//AroundBeacons/AroundBeacon");
            if (nodeList != null && nodeList.getLength() > 0) {
                AroundBeacon aroundBeacon = null;
                List<AroundBeacon> aroundBeacons = new ArrayList<AroundBeacon>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    aroundBeacon = new AroundBeacon();
                    aroundBeacon.setUuid(xmlHelper.getString(node, "Uuid"));
                    aroundBeacon.setMajor(xmlHelper.getNumber(node, "Major").intValue());
                    aroundBeacon.setMinor(xmlHelper.getNumber(node, "Minor").intValue());
                    aroundBeacon.setDistance(xmlHelper.getNumber(node, "Distance").floatValue());
                    aroundBeacons.add(aroundBeacon);
                }
                e.setAroundBeaconList(aroundBeacons);
            }
            return e;
        }

        // 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
        if ("qualification_verify_success".equals(event) || "naming_verify_success".equals(event)
                || "annual_renew".equals(event) || "verify_expired".equals(event)) {
            InVerifySuccessEvent e = new InVerifySuccessEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setExpiredTime(xmlHelper.getString("//expiredTime"));
            return e;
        }
        // 资质认证失败 || 名称认证失败
        if ("qualification_verify_fail".equals(event) || "naming_verify_fail".equals(event)) {
            InVerifyFailEvent e = new InVerifyFailEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setFailTime(xmlHelper.getString("//failTime"));
            e.setFailReason(xmlHelper.getString("//failReason"));
            return e;
        }
        // 门店在审核事件消息 , update by unas at 2016-1-29,add event param
        if ("poi_check_notify".equals(event)) {
            InPoiCheckNotifyEvent e = new InPoiCheckNotifyEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setUniqId(xmlHelper.getString("//UniqId"));
            e.setPoiId(xmlHelper.getString("//PoiId"));
            e.setResult(xmlHelper.getString("//Result"));
            e.setMsg(xmlHelper.getString("//Msg"));
            return e;
        }
        // WIFI连网后下发消息 by unas at 2016-1-29
        if ("WifiConnected".equals(event)) {
            InWifiEvent e = new InWifiEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setConnectTime(xmlHelper.getString("//ConnectTime"));
            e.setExpireTime(xmlHelper.getString("//ExpireTime"));
            e.setVendorId(xmlHelper.getString("//VendorId"));
            e.setDeviceNo(xmlHelper.getString("//DeviceNo"));
            e.setShopId(xmlHelper.getString("//ShopId"));
            return e;
        }
        if (InUserViewCardEvent.EVENT.equals(event)) {
            InUserViewCardEvent e = new InUserViewCardEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setCardId(xmlHelper.getString("//CardId"));
            e.setUserCardCode(xmlHelper.getString("//UserCardCode"));
            return e;
        }
        if (InSubmitMemberCardEvent.EVENT.equals(event)) {
            InSubmitMemberCardEvent e = new InSubmitMemberCardEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setCardId(xmlHelper.getString("//CardId"));
            e.setUserCardCode(xmlHelper.getString("//UserCardCode"));
            return e;
        }
        if (InUpdateMemberCardEvent.EVENT.equals(event)) {
            InUpdateMemberCardEvent e = new InUpdateMemberCardEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setCardId(xmlHelper.getString("//CardId"));
            e.setUserCardCode(xmlHelper.getString("//UserCardCode"));
            e.setModifyBonus(xmlHelper.getString("//ModifyBonus"));
            e.setModifyBalance(xmlHelper.getString("//ModifyBalance"));
            return e;
        }
        if (InUserPayFromCardEvent.EVENT.equals(event)) {
            InUserPayFromCardEvent e = new InUserPayFromCardEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setCardId(xmlHelper.getString("//CardId"));
            e.setUserCardCode(xmlHelper.getString("//UserCardCode"));
            e.setLocationId(xmlHelper.getString("//LocationId"));
            e.setTransId(xmlHelper.getString("//TransId"));
            e.setFee(xmlHelper.getString("//Fee"));
            e.setOriginalFee(xmlHelper.getString("//OriginalFee"));
            return e;
        }
        // 微信小店支付消息
        if (InMerChantOrderEvent.EVENT.equals(event)) {
            InMerChantOrderEvent e = new InMerChantOrderEvent(toUserName, fromUserName, createTime, msgType, event);
            e.setOrderId(xmlHelper.getString("//OrderId"));
            e.setOrderStatus(xmlHelper.getNumber("//OrderStatus").intValue());
            e.setProductId(xmlHelper.getString("//ProductId"));
            e.setSkuInfo(xmlHelper.getString("//SkuInfo"));
            return e;
        }

        LogKit.error("无法识别的事件类型" + event + "，请查阅微信公众平台开发文档");
        InNotDefinedEvent e = new InNotDefinedEvent(toUserName, fromUserName, createTime, msgType, event);
        return e;
    }

}