package cn.com.egova.wx.web.handler;

import cn.com.egova.wx.editor.ueditor.ActionEnter;
import cn.com.egova.wx.editor.ueditor.PathFormat;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.FileType;
import cn.com.egova.wx.editor.ueditor.define.State;
import cn.com.egova.wx.sdk.api.ApiConfig;
import cn.com.egova.wx.sdk.api.ApiResult;
import cn.com.egova.wx.sdk.api.CustomServiceApi;
import cn.com.egova.wx.sdk.api.GroupsApi;
import cn.com.egova.wx.sdk.api.MediaApi;
import cn.com.egova.wx.sdk.api.MediaArticles;
import cn.com.egova.wx.sdk.api.MenuApi;
import cn.com.egova.wx.sdk.api.MessageApi;
import cn.com.egova.wx.sdk.api.ReturnCode;
import cn.com.egova.wx.sdk.api.TagApi;
import cn.com.egova.wx.sdk.api.TemplateMsgApi;
import cn.com.egova.wx.sdk.api.UserApi;
import cn.com.egova.wx.sdk.handler.WxApiHandler;
import cn.com.egova.wx.sdk.util.IOUtils;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.util.ListUtil;
import cn.com.egova.wx.util.WeChatUtil;
import cn.com.egova.wx.web.bean.SpringUtil;
import cn.com.egova.wx.web.controller.HomeController;
import cn.com.egova.wx.web.entity.Article;
import cn.com.egova.wx.web.entity.ArticlesWrapper;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.entity.ImageGroup;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.MoveGroupEntity;
import cn.com.egova.wx.web.entity.Node;
import cn.com.egova.wx.web.entity.User;
import cn.com.egova.wx.web.entity.UserGroup;
import cn.com.egova.wx.web.entity.UserGroups;
import cn.com.egova.wx.web.service.IMaterialService;
import cn.com.egova.wx.web.service.IMenuService;
import cn.com.egova.wx.web.service.IUserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 直接调用微信接口逻辑，属于主动响微信服务器拉取信息
 */
public class WeixinApiInvokeHandler extends WxApiHandler {

    private final static Logger logger = Logger.getLogger(WeixinApiInvokeHandler.class);

    /**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
     * <p>
     * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
     */
    @Override
    public ApiConfig getApiConfig() {
        return WeChatUtil.getApiConfig();
    }

    /**
     * 获取公众号菜单
     */
    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     */
    public void createMenu() {
        try {
            String str = getPara("menuJson");
            ApiResult apiResult = null;
            logger.debug(str);
            if ("{\"button\":[]}".equals(str)) {
                apiResult = MenuApi.deleteMenu();
            } else {
                apiResult = MenuApi.createMenu(str);
            }
            if (apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else{
                if(40054 == apiResult.getErrorCode()){
                    renderJson("errorInfo","菜单跳转页面地址必须以http或者https开头");
                }else{
                    renderJson("errorInfo",apiResult.getErrorMsg());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson("errorInfo",e.getMessage());
        }
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers() {
        try {
            ApiResult apiResult = UserApi.getFollows();
            if(apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void createUserGroup(){
        try {
            ApiResult apiResult = GroupsApi.create("新的分组");
            if(apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }
    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        try {
            ApiResult apiResult = UserApi.getUserInfo("ocMersxnqrEu42AP39gvIkv4enzg");
            if(apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void moveUserGroup(){
        try {
            String data = getPara("data");
            MoveGroupEntity  moveGroupEntity = new ObjectMapper().readValue(data, MoveGroupEntity.class);
            ApiResult apiResult = GroupsApi.membersBatchUpdate(moveGroupEntity.getOpenid_list(),Integer.valueOf(moveGroupEntity.getTo_groupid()));
            if(apiResult.isSucceed()){
                IUserService userService = (IUserService) SpringUtil.getBean("userService");
                userService.batchUpdateUser(moveGroupEntity.getOpenid_list(),moveGroupEntity.getTo_groupid());
                renderJson(apiResult.getJson());
            }
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }
    /**
     * 发送模板消息
     */
    public void sendMsg() {
        String str = " {\n" +
                "           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n" +
                "           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n" +
                "           \"url\":\"http://www.sina.com\",\n" +
                "           \"topcolor\":\"#FF0000\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\"1元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        ApiResult apiResult = TemplateMsgApi.send(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取永久素材列表
     */
    public void batchGetMaterial() {
        try {
            IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
            //素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
            String type = getPara("type");
            //返回数量1-20 表示pageSize
            int count = getParaToInt("count");
            //0从第一个开始返回,表示页码
            int pageNow = getParaToInt("pageNow");
            int offset = (pageNow - 1) * count;
            if ("NEWS".equals(type)) {
                String jsonNews = null;
                if(HomeController.isInvokeApi){
                    ApiResult apiResult = MediaApi.batchGetMaterial(MediaApi.MediaType.valueOf(type), offset, count);
                    if(!apiResult.isSucceed()) throw new RuntimeException(apiResult.getErrorMsg());
                    jsonNews = apiResult.getJson();
                }else{
                    jsonNews = "{\"item\":[{\"media_id\":\"5InMJLYSvF9dIlyp0PcayFLoFhkNWb4u5-Cd-UjrcE8\",\"content\":{\"news_item\":[{\"title\":\"天津市不动产登记预约服务平台使用说明\",\"author\":\"不动产登记预约\",\"digest\":\"更好的利用预约资源，提高登记效率，我们将天津市不动产登记预约服务系统使用说明及运行中发现的问题予以公示，请大家相互提醒，予以注意。\",\"content\":\"<section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0.5em; color: rgb(255, 255, 255); font-size: 18px; box-shadow: rgb(165, 165, 165) 0.2em 0.2em 0.1em; font-family: inherit; text-decoration: inherit; border-color: rgb(95, 156, 239); background-color: rgb(95, 156, 239);\\“><section>不动产登记网上预约使用说明<br  \\/><\\/section><\\/span><section><\\/section><\\/section><p><br  \\/><\\/p><section><section><span class=\\“\\“ style=\\“padding: 3px 8px; display: inline-block; border-radius: 4px; color: rgb(255, 255, 255); font-size: 1em; font-family: inherit; text-decoration: inherit; border-color: rgb(95, 156, 239); background-color: rgb(95, 156, 239);\\“><section>导语<img src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/thdIJRuQSg5qyFVKgY2fqxg3yibckyLKP0eJzPOVHJmGd7GOr3hWuRxnYNWDRIunaf08S2ZjEOOuuccrpud5N3g\\/0?wx_fmt=jpeg\\“ title=\\“806705029217323\\“  \\/><\\/section><\\/span><\\/section><section><section>为更好的利用预约资源，提高登记效率，我们将天津市不动产登记预约服务系统使用说明及运行中发现的问题予以公示，请大家相互提醒，予以注意。<\\/section><\\/section><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>1<\\/section><\\/span><section><\\/section><\\/section><section><section>选择不动产登记所在区<br  \\/><\\/section><\\/section><section><\\/section><section><section>通过点击微信公众号上的&quot;预约服务”项下的&quot;天津预约服务平台”，即可进入平台主页，选择区域，你需要选择不动产所在的区县办理业务。<\\/section><\\/section><\\/section><section><\\/section><\\/section><section><img class=\\“\\“ src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/fvljvCzgVv6BIPiaBBMSWpyPqqN2qUCevQW85DFYTOHxIn4NwtUBicaY41OicXaiaq4sicocQMZwk2JyAHdKyc34ibicA\\/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1\\“  \\/><section><\\/section><\\/section><section><section><section>注：蓝色显示的为已开通预约的区，灰色的区暂未开通网上预约，敬请期待。<\\/section><\\/section><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>2<\\/section><\\/span><section><\\/section><\\/section><section><section>浏览登记要件<\\/section><\\/section><section><\\/section><section><section>如果您需要办理某一种不动产登记，可以通过点击办件指引，了解登记所需的要件和各种注意事项。<\\/section><\\/section><\\/section><section><\\/section><\\/section><section><img class=\\“\\“ src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/fvljvCzgVv6BIPiaBBMSWpyPqqN2qUCevc4s33NVN5ibYoXxg3scvpbQmnIw4S28RylcUOiaBwBbJjwAKc6iaCEImA\\/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1\\“  \\/><section><\\/section><\\/section><section><img class=\\“\\“ src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/fvljvCzgVv6BIPiaBBMSWpyPqqN2qUCevqY5G7jDEF6PAMnibqyE5EOIcsEPZIUHwDriahTrw0PRNTTOVsscOk1HQ\\/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1\\“  \\/><section><\\/section><\\/section><section><section><section>注：预约之前了解相关政策非常重要，否则因为缺少要件导致无法受理就得不偿失了。<\\/section><\\/section><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>3<\\/section><\\/span><section><\\/section><\\/section><section><section>不动产登记预约<br  \\/><\\/section><\\/section><section><\\/section><section><section>您可以根据自己的时间预约需要办理的不动产登记业务，你需要阅读预约协议，同意后，就可以进入下一个页面，选择区域、登记类别、登记子类、日期、时段。<br  \\/><\\/section><\\/section><\\/section><section><\\/section><\\/section><section><section><\\/section><\\/section><section><section><section><section>特别提醒，重点关注<br  \\/><\\/section><\\/section><section><section><\\/section><section><\\/section><\\/section><\\/section><section><section><section>好了，这一部分需要您重点关注，这是网上预约中我们重点审查的内容。<br  \\/>姓名，身份证号，产权证号，坐落必须保证准确，与登记系统记载一致方可审核通过。<br  \\/><\\/section><\\/section><\\/section><section><\\/section><\\/section><section><section><\\/section><\\/section><section><section><section>注：产权证号和不动产权坐落一定得填写准确，否则，会造成预约失败。<\\/section><\\/section><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>注<\\/section><\\/span><section><\\/section><\\/section><section><section>产权证号的填写方法<\\/section><\\/section><section><\\/section><section><section>在填写预约信息时常常需要您填写产权证证号，证号一般在证书上方，常见证号格式有：<br  \\/>房屋所有权证：房权证津字第050505055号<br  \\/>房地产权证：房地证津字第101020203030号<br  \\/>不动产权证：不动产权第0001234号<br  \\/>您在填写时只需填入数字如050505055、101020203030、0001234即可！而证书后面下方的一段数字是该证书的流水证号，请您在申请预约时不要填写流水证号！<\\/section><\\/section><\\/section><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>4<\\/section><\\/span><section><\\/section><\\/section><section><section>预约信息提交<\\/section><\\/section><section><\\/section><section><section>当您确定您填写的信息准确无误，可以点击&quot;确定”按钮提交您的预约信息，我们将通过后台对其进行审核。<\\/section><\\/section><\\/section><section><\\/section><\\/section><section><img class=\\“\\“ src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/fvljvCzgVv6BIPiaBBMSWpyPqqN2qUCevJs57FXLib2xmhaRGIjnibhVKs6jciawuibx8h8kaKlibwCrFO0mJnJgquibw\\/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1\\“  \\/><section><\\/section><\\/section><section><section><section><span class=\\“\\“ style=\\“padding: 0.3em 0.5em; display: inline-block; border-radius: 0px 0.5em 0.5em 0px; font-size: 1em; font-family: inherit; background-color: rgb(95, 156, 239);\\“><section>5<\\/section><\\/span><section><\\/section><\\/section><section><section>预约提醒<\\/section><\\/section><section><\\/section><section><section>如果您的预约通过审核，我们会给您推送预约成功提醒，当然，如果您提交的信息与系统信息不符，我们会给您推送预约失败提醒，您需要再次核实信息，重新预约。<\\/section><\\/section><\\/section><section><\\/section><\\/section><section><img class=\\“\\“ src=\\“http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/fvljvCzgVv6BIPiaBBMSWpyPqqN2qUCevibyyuDHTJhyQp4qaaqUcN3MUW1o6HBDxTfwzQ4dzMVHXND7rSbzddEw\\/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1\\“  \\/><section><\\/section><\\/section><section><section><section>注：预约成功以后，您在预约日期的预约时段前到达办件大厅，刷身份证取号，就可以办理登记了。<\\/section><\\/section><\\/section><p><br  \\/><\\/p><p style=\\“text-align: center;\\“><br  \\/><\\/p>\",\"content_source_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA5NjYxMzU2Nw==&mid=504396437&idx=1&sn=6336bf7be9414e437022d0e31bac3327&scene=18#wechat_redirect\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayN4EZ-TXLcgkUK56zJaEGEQ\",\"show_cover_pic\":0,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000207&idx=1&sn=08ec872d4936decb47819188b217b1ad&chksm=1b9571d02ce2f8c619398bf0149d8963385618b70f2bf9ab30fcdea4a5d6026d4d6e4db62c3e#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/thdIJRuQSg5ym4HlF0qsibPKFkr1ULsgYQDvZeAML4iap9VwemEZSib7HJ8ic09icmpnPqFSqTzT42iajmWR81ttsQVA\\/0?wx_fmt=png\"},{\"title\":\"附加信息\",\"author\":\"附加\",\"digest\":\"111111111111111111\",\"content\":\"<p>附加<\\/p>\",\"content_source_url\":\"\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayBHcTx1jgOdOZV5PQsqtXss\",\"show_cover_pic\":1,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000207&idx=2&sn=7939f047a382555f54538f61a9d4eb53&chksm=1b9571d02ce2f8c64c214b4ab988ee64944aff3063a369be42706d4e927959e86ef27962f1e0#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/thdIJRuQSg6MdnML4lo6BRTESIvPibugcBcEMWYE5BEjc7rjjLGquceNbJ55VMOiav2brYfiaICR4BK64e0gI3HCw\\/0?wx_fmt=png\"}],\"create_time\":1479779972,\"update_time\":1479779972},\"update_time\":1479779972},{\"media_id\":\"5InMJLYSvF9dIlyp0PcayFNVoWpOelvn2bcV0Zmzf1c\",\"content\":{\"news_item\":[{\"title\":\"111\",\"author\":\"11111\",\"digest\":\"\",\"content\":\"<p>111111111111111<\\/p>\",\"content_source_url\":\"\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayF78lXkdmkdOioNTJj1rfO4\",\"show_cover_pic\":0,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000202&idx=1&sn=d55e0a28e96e3c2a9c153d16ed1f5337&chksm=1b9571d52ce2f8c3e324d189829dad858ba815c697d7f90fadd02a3c7fa17305d3150310c168#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/thdIJRuQSg53JZMAJ4zLcWiaEsD5icPlPa94aJAuwTicd8oppjXzrqhdpMQ7O4w3ZJmeSHVFYDQ7zyicqKicZks0bjA\\/0?wx_fmt=png\"}],\"create_time\":1479439751,\"update_time\":1479439751},\"update_time\":1479439751},{\"media_id\":\"5InMJLYSvF9dIlyp0PcayDRYWmsop0uFu9e_rbvbMQE\",\"content\":{\"news_item\":[{\"title\":\"测试文章\",\"author\":\"测试作者\",\"digest\":\"我是摘要\",\"content\":\"<p>测试正文,可以作为摘要。222<\\/p>\",\"content_source_url\":\"http:\\/\\/www.oschina.net\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayF3z7e3EUiZSCIIaM0U5YsA\",\"show_cover_pic\":1,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000199&idx=1&sn=a25393d3b439a326536231bbb10dc661&chksm=1b9571d82ce2f8cea3c6157a315eaec22f9fba45fd791daf215934bad72987fe56997b7dd72a#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/thdIJRuQSg5WMyq31MAZhzLAs0IBFWdYGZksyVg4YHBWE1qQtfOJRkibypxv6aBx97P7k5okwqUesOaZaiaXdEyw\\/0?wx_fmt=jpeg\"}],\"create_time\":1479439172,\"update_time\":1479439172},\"update_time\":1479439172},{\"media_id\":\"5InMJLYSvF9dIlyp0PcayNReoIA93xzS1_YjzgY0cJM\",\"content\":{\"news_item\":[{\"title\":\"测试文章\",\"author\":\"测试\",\"digest\":\"\",\"content\":\"<p>测试信息<\\/p>\",\"content_source_url\":\"\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayAB1exz7l3Xo6ufoBArdEhw\",\"show_cover_pic\":1,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000161&idx=1&sn=2eacc830e1a481e6f09900cb4fc17d72&chksm=1b9571be2ce2f8a88fd7a18830cb563c4b9ed35a6639c17a14764e3023dd4c95b41ba9120c8c#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/thdIJRuQSg5qyFVKgY2fqxg3yibckyLKP1gSo6Iu8NPhj2hDIkeDoRaQdujSulQHwqFWmnJGNXwwwvXcM62xLRA\\/0?wx_fmt=jpeg\"}],\"create_time\":1477377283,\"update_time\":1477377283},\"update_time\":1477377283}],\"total_count\":7,\"item_count\":4}\n";
                }
                jsonNews = WeChatUtil.jsonStringReplace(jsonNews,'“','"');
                JSONObject obj = JSONObject.fromObject(jsonNews);

                String total_count = obj.getString("total_count");
                //计算总页数
                int amount = Integer.valueOf(total_count);
                int pages = amount % count == 0 ? amount / count : amount / count + 1;
                setAttr("total_count", total_count);
                setAttr("pages", pages);
                setAttr("pageNow", pageNow);
                JSONArray jsonArray = obj.getJSONArray("item");
                List<List<Material>> lists = new ArrayList<List<Material>>();
                //逐条保存
                List<Material> materialList = new ArrayList<Material>();
                for (int m = 0; m < jsonArray.size(); m++) {
                    JSONObject jobj = (JSONObject) jsonArray.get(m);
                    String media_id = jobj.getString("media_id");
                    long update_time = jobj.getLong("update_time");
                    JSONArray ar = jobj.getJSONObject("content").getJSONArray("news_item");
                    List<Material> materials = new ArrayList<Material>();
                    for (int n = 0; n < ar.size(); n++) {
                        Material material = new Material();
                        JSONObject j = (JSONObject) ar.get(n);
                        String ut = materialService.getUpdateTimeViaMediaId(media_id);
                        material.setUpdate_time(Long.valueOf(ut == null?update_time:Long.valueOf(ut)));
                        material.setMedia_id(media_id);
                        material.setTitle(j.getString("title"));
                        material.setThumb_url(j.getString("thumb_url"));
                        material.setThumb_url_base64(material.getThumb_url());
                        material.setDigest(j.getString("digest"));
                        material.setUrl(j.getString("url"));
                        material.setContent_source_url(j.getString("content_source_url"));
                        material.setAuthor(j.getString("author"));
                        material.setShow_cover_pic(j.getInt("show_cover_pic"));
                        material.setThumb_media_id(j.getString("thumb_media_id"));
                        materials.add(material);
                        materialList.add(material);
                    }
                    //设置好图文对应的Json结构
                    lists.add(materials);
                }
                materialService.saveMaterial(materialList);
                //list分段
                int cols = getParaToInt("cols");
                List<List<List<Material>>> splitedList = ListUtil.splitList(lists, cols);
                if (splitedList != null && splitedList.size() > 0) {
                    for (int i = 0; i < splitedList.size(); i++) {
                        setAttr("col" + (i + 1), splitedList.get(i));
                    }
                }
                setAttr("lists", lists);
                //2段是菜单编辑选取图文消息处
                if (cols == 2){
                    //消息群发加载图文
                    if(getPara("isGroupMessage") != null)
                        render("/WEB-INF/jsp/new4GroupMessage.jsp");
                    else//自定菜单加载图文
                        render("/WEB-INF/jsp/news.jsp");
                }
                //3段位素材管理界面分组
                if (cols == 3) {
                    if ("card".equals(getPara("view")))
                        render("/WEB-INF/jsp/materialManage.jsp");
                    else
                        render("/WEB-INF/jsp/materialManageList.jsp");
                }

            } else if ("IMAGE".equals(type)) {
                //过滤指定分组图片
                String group_id = getPara("group_id");
                //默认设置为未分组
                if (group_id == null || "".equals(group_id.trim())) {
                    group_id = "1";
                }
                //读取本地图片列表
                List<Image> images = new ArrayList<Image>();
                images = materialService.getImages(String.valueOf(count),String.valueOf(offset),group_id);
                int total =materialService.getTotalImages(group_id);
                setAttr("total_count", total);
                int allPages = total % count == 0 ?total/count:total/count+1;
                setAttr("pages", allPages);
                setAttr("pageNow", pageNow);

                setAttr("imageList", images);
                setAttr("group_id", group_id);
                setAttr("groupName",materialService.getGroupName(group_id));
                //读取分组信息
                List<ImageGroup> imageGroups = new ArrayList<ImageGroup>();
                imageGroups = materialService.getGroups();
                setAttr("imageGroups", imageGroups);
                if(getPara("isGroupMessage") != null)
                    render("/WEB-INF/jsp/selectImages.jsp");
                else
                    render("/WEB-INF/jsp/imageManage.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderText(e.getMessage());
        }
    }

    /**
     * 素材编辑器图片和文件上传入口
     */
    public void dealEditor() {
        try {
            String rootPath = getRequest().getServletContext().getRealPath("/");
            renderJson(new ActionEnter(getRequest(), rootPath).invoke());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增图文素材
     */
    public void addNews() {
        List<MediaArticles> mediaArticles = new ArrayList<MediaArticles>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, MediaArticles.class);
            mediaArticles = (List<MediaArticles>) mapper.readValue(getPara("newsJson"), javaType);
            String action = getPara("action");
            ApiResult apiResult = null;
            if ("add".equals(action)) {
                apiResult = MediaApi.addNews(mediaArticles);
            } else if ("update".equals(action)) {
                String media_id = getPara("media_id");
                //先删除该素材，然后新增
                //远程素材删除，本地素材也需要同步删除
                apiResult= MediaApi.delMaterial(media_id);
                if(!apiResult.isSucceed()){
                    renderJson("errorInfo", "图文素材修改失败");
                    return;
                }

                apiResult = MediaApi.addNews(mediaArticles);
                if(!apiResult.isSucceed()){
                    renderJson("errorInfo", "图文素材修改失败");
                    return;
                }
                String newMedia_id = apiResult.getStr("media_id");
                //图文素材变更后，同步本地图文消息和菜单绑定信息
                //同步素材
                materialService.updateMaterial(media_id,newMedia_id);
            }
            if (apiResult.isSucceed()){
                String mediaId = apiResult.getStr("media_id");
                materialService.updateMaterialUpdateTime(mediaId,mediaArticles);
                renderJson(apiResult.getJson());
            }
            else
                renderJson("errorInfo", apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo", "图文素材添加失败");
        }
    }

    public void updateNews() {
        MediaArticles mediaArticles = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mediaArticles = mapper.readValue(getPara("news"), MediaArticles.class);
            ApiResult apiResult = MediaApi.updateNews(getPara("media_id"), getParaToInt("index"), null);
            if (apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else
                renderJson("errorInfo", apiResult.getErrorMsg());
        } catch (IOException e) {
            e.printStackTrace();
            renderJson("errorInfo", "图文素材修改失败");
        }
    }

    public void delMaterial() {
        try {
            String media_id = getPara("media_id");
            String[] ids = media_id.split(";");
            if(ids != null && ids.length > 0){
                for(int i = 0 ; i < ids.length ; i++){
                    ApiResult apiResult = MediaApi.delMaterial(ids[i]);
                    if (apiResult.isSucceed()) {
                        //删除本地图片记录
                        IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
                        materialService.deleteImg(ids[i]);
                        materialService.deleteMaterial(ids[i]);
                        renderJson(apiResult.getJson());
                    } else
                        renderJson("errorInfo", apiResult.getErrorMsg());
                }
            }
        } catch (Exception e) {
            renderJson("errorInfo", "删除素材失败");
        }
    }

    /**
     * 新增封面图片
     */
    public void uploadCoverPic() {
        State state = new BaseState(true);
        InputStream is = null;
        FileItemStream fileStream = null;
        boolean isAjaxUpload = getRequest().getHeader("X_Requested_With") != null;
        if (!ServletFileUpload.isMultipartContent(getRequest())) {
            state = new BaseState(false, 5);
        } else {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            if (isAjaxUpload) {
                upload.setHeaderEncoding("UTF-8");
            }
            try {
                for (FileItemIterator e = upload.getItemIterator(getRequest()); e.hasNext(); fileStream = null) {
                    fileStream = e.next();
      /*              if (!fileStream.isFormField()) {
                        break;
                    }*/
                    if (fileStream == null) {
                        state = new BaseState(false, 7);
                    } else {
                        is = fileStream.openStream();
                        String fileName = fileStream.getName();
                        ApiResult apiResult = MediaApi.addMaterial("thumb", is, fileStream.getName());
                        if (apiResult.isSucceed()) {
                            JSONObject jsonObject = JSONObject.fromObject(apiResult.getJson());
                            String url = jsonObject.getString("url");
                            state.putInfo("url", url);
                            state.putInfo("media_id", jsonObject.getString("media_id"));
                            //本地图片入库,默认进入“未分组"
                            Image image = new Image();
                            image.setMedia_id(jsonObject.getString("media_id"));
                            image.setUrl(jsonObject.getString("url"));
                            image.setName(fileName);
                            String group_id = getPara("group_id");
                            if(group_id == null || "".equals(group_id.trim()))
                                group_id = "1";
                            state.putInfo("group_id",group_id);
                            image.setGroup_id(group_id);
                            image.setUpdate_time(System.currentTimeMillis());
                            //本地添加图片
                            IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
                            materialService.addImage(image);
                            //更新菜单关联图文封面图片地址
                            String newsUrl = getPara("url");
                            if(newsUrl != null)
                                newsUrl = URLDecoder.decode(newsUrl,"UTF-8");
                            materialService.updateMaterialCover(getPara("media_id"),newsUrl,url);
                        } else {
                            state.putInfo("errorInfo",apiResult.getErrorMsg());
                        }
                    }
                }
            } catch (Exception e) {
                state.putInfo("errorInfo","图片上传异常");
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            renderJson(state.toJSONString());
        }
    }

    /**
     * 此处为编辑器初始化入口
     * 新增和编辑处理不同，编辑动作时需根据传入的media_id调用素材获取接口
     */
    public void initEditor() {
        String media_id = getPara("media_id");
        List<Material> materials = new ArrayList<Material>();
        try {
            //编辑图文消息
            if(media_id != null){
                String result = null;
                if(HomeController.isInvokeApi){
                    InputStream in = MediaApi.getMaterial(media_id);
                    result  = IOUtils.toString(in, Charset.forName("utf-8"));
                }else{
                    result = "{\"news_item\":[{\"title\":\"标题1\",\"author\":\"作者1\",\"digest\":\"摘要1\",\"content\":\"我是正文1<br  \\/>\",\"content_source_url\":\"\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayBXVQfiSKgOXlC7oGcMmz08\",\"show_cover_pic\":1,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000018&idx=1&sn=ce30447d0c451907052c7fda7e15cf78#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/thdIJRuQSg5mgia5UyQyAxIibib5t92fUIZoYnvaobw7je4X6pjYWUehico7et09Nmg1qJrkozcuiaiaxic7Jzh5dUhmg\\/0?wx_fmt=jpeg\"},{\"title\":\"标题2\",\"author\":\"22222\",\"digest\":\"摘要2\",\"content\":\"\"2222222\"\",\"content_source_url\":\"http:\\/\\/www.oschina.net\\/news\\/76470\\/mongoosejs-4-5-10\",\"thumb_media_id\":\"5InMJLYSvF9dIlyp0PcayKR7iP1KsSimR8DCGu0IO5A\",\"show_cover_pic\":1,\"url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAxNDM3OTM3Ng==&mid=100000018&idx=2&sn=86fa3aaea6335be6dec40b993bc9ad91#rd\",\"thumb_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/thdIJRuQSg5mgia5UyQyAxIibib5t92fUIZBf4CfYLRnsIOibHAhDop1iaibpKySGvl3QAb0CtWqFSEOK8Q1TekaaTeQ\\/0?wx_fmt=jpeg\"}],\"create_time\":1472024207,\"update_time\":12024207}";
                }
                //content里可能含有"，会造成解析错误,将之替换成中文引号
                result = WeChatUtil.jsonStringReplace(result,'“','"');
                JSONObject obj = JSONObject.fromObject(result);
                if(obj.get("errcode") != null){
                    String info = ReturnCode.get((Integer)obj.get("errcode"));
                    if(info != null)
                        renderText(info);
                    else
                        renderText("接口调用异常...");
                    return;
                }
                //接口调用是否成功
                JSONArray ary = obj.getJSONArray("news_item");
                if(ary != null && ary.size() >0){
                    for(int i = 0 ; i < ary.size() ; i++){
                        JSONObject o = (JSONObject)ary.get(i);
                        Material article = new Material();
                        article.setAuthor(o.getString("author"));
                        article.setTitle(o.getString("title"));
                        article.setDigest(o.getString("digest"));
                        String content = o.getString("content");
                        if(content != null && content.contains("“"));
                            article.setContent(content.replace("“","\""));
                        article.setContent_source_url(o.getString("content_source_url"));
                        article.setThumb_media_id(o.getString("thumb_media_id"));
                        article.setUrl(o.getString("url"));
                        article.setShow_cover_pic(o.getInt("show_cover_pic"));
                        article.setMedia_id(media_id);
                        String thumb_url = o.getString("thumb_url");
                        article.setThumb_url(thumb_url);
                        article.setThumb_url_base64(ImageUtil.encodeImgageToBase64(thumb_url));
                        materials.add(article);
                    }
                    IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
                    materialService.saveMaterial(materials);
                }
            }else{//新建图文消息
                Material article = new Material();
                article.setTitle("");
                article.setAuthor("");
                article.setShow_cover_pic(0);
                article.setContent("");
                materials.add(article);
            }
            setAttr("articles",materials);
            setAttr("media_id",media_id);
            render("/WEB-INF/jsp/editor.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的用户分组列表
     */
    public void getUserGroups(){
        try {
          /*  renderJson("{\"groups\":[{\"id\":0,\"name\":\"未分组\",\"count\":1}," +
                    "{\"id\":1,\"name\":\"黑名单\",\"count\":0}," +
                    "{\"id\":2,\"name\":\"星标组\",\"count\":0}," +
                    "{\"id\":100,\"name\":\"新的分组\",\"count\":0}]}\n");*/
            ApiResult result =  GroupsApi.get();
            if(result.isSucceed()){
                renderJson(result.getJson());
            }else {
                renderJson("errorInfo","获取用户分组信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("errorInfo","获取用户分组信息失败");
        }
    }

    //消息群发
    public void sendGroupMessage(){
        try {
            ApiResult apiResult = null;
            String message = getPara("message");
            message = message.replaceAll("<br/>","\n");
            //测试调用预览接口
            if(HomeController.test_weixin_hao != null && !"".equals(HomeController.test_weixin_hao.trim())){
                //添加微信号参数
                String json = message.substring(0,message.length()-1);
                json += ",\"towxname\":\"";
                json += HomeController.test_weixin_hao;
                json += "\"}";
                apiResult = MessageApi.preview(json);
            }
            else
                apiResult = MessageApi.sendAll(message);
            if(apiResult.isSucceed())
                renderJson(apiResult.getJson());
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    /**
     * 拉去用户列表
     */
    public void fetchUserList(){
        int pageNow = getParaToInt("pageNow"),count = getParaToInt("count");
        String groupid = getPara("groupid"),nickname=getPara("nickname");
        try {
            //读取本地用户数据
            int offset = (pageNow - 1) * count;
            IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
            List<User> userList = userService.fetchUserList(String.valueOf(count),String.valueOf(offset)
                    ,"subscribe_time","groupid",groupid,"nickname",nickname);
            setAttr("userList",userList);
            setAttr("pageNow",pageNow);
            int total = userService.getTotalUsers(groupid);
            setAttr("total_count", total);
            int allPages = total % count == 0 ?total/count:total/count+1;
            setAttr("pages", allPages);

            List<UserGroup> groupList = new ArrayList<UserGroup>();
            groupList = userService.getUserGroups();
            int sum = userService.getTotalUsers(null);
            setAttr("groupName",getPara("groupName"));
            setAttr("groupid",getPara("groupid"));
            setAttr("totalGroups",sum);
            setAttr("groupList",groupList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        render("/WEB-INF/jsp/userManage.jsp");
    }

    public  void updateUserRemark(){
        try {
            IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
            ApiResult apiResult = UserApi.updateRemark(getPara("openid"),getPara("remark"));
            if(apiResult.isSucceed()){
                userService.updateRemark(getPara("openid"),getPara("remark"));
                renderJson(apiResult.getJson());
            }
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void add2Blacklist(){
        try {
            String data = getPara("data");
            IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
            ApiResult apiResult = GroupsApi.membersBatchMove(data);
            if(apiResult.isSucceed()){
                MoveGroupEntity  moveGroupEntity = new ObjectMapper().readValue(data, MoveGroupEntity.class);
                //批量拉黑用户
                userService.batchUpdateUser(moveGroupEntity.getOpenid_list(),"1");
                renderJson(apiResult.getJson());
            }
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void unBlacklist(){
        try {
            IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
            ApiResult apiResult = GroupsApi.membersBatchMove(getPara("data"));
            if(apiResult.isSucceed()){
                MoveGroupEntity  moveGroupEntity = new ObjectMapper().readValue(getPara("data"), MoveGroupEntity.class);
                //批量拉黑用户,默认放到未分组
                userService.batchUpdateUser(moveGroupEntity.getOpenid_list(),"0");
                renderJson(apiResult.getJson());
            }
            else
                renderJson("errorInfo",apiResult.getErrorMsg());
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void addOrUpdateGroup(){
        try {
            IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
            String action = getPara("action"),groupName = getPara("groupName");
            ApiResult apiResult = GroupsApi.get();
            if(!apiResult.isSucceed())
                renderJson("errorInfo",apiResult.getErrorMsg());
            else{//校验分组名称是否存在
                if(apiResult.getJson().contains(groupName)){
                    renderJson("errorInfo","分组名称已经存在");
                    return;
                }
            }
            ApiResult result = null;
            if("add".equals(action)){//新增分组
                result = GroupsApi.create(groupName);
                if(result.isSucceed()){
                    String groupInfo = result.getJson();
                    JSONObject jsonObject = JSONObject.fromObject(groupInfo).getJSONObject("group");
                    UserGroup userGroup = new UserGroup();
                    userGroup.setId((Integer)jsonObject.get("id"));
                    userGroup.setName((String)jsonObject.get("name"));
                    userService.addUserGroup(userGroup);
                }
            }else{//重命名分组
                result = GroupsApi.update(getParaToInt("groupId"),groupName);
                if(result.isSucceed()){
                    userService.updateUserGroup(getParaToInt("groupId"),groupName);
                }
            }
            if(!result.isSucceed()){
                renderJson("errorInfo",result.getErrorMsg());
            }else{
                renderJson(result.getJson());
            }
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void delUserGroup(){
        try {
            ApiResult result = GroupsApi.delete(getParaToInt("groupId"));
            if(!result.isSucceed()){
                renderJson("errorInfo",result.getErrorMsg());
            }else{//删除用户分组后。默认进入未分组
                IUserService userService = ((IUserService)SpringUtil.getBean("userService"));
                userService.deleteGroup(getPara("groupId"));
                userService.batchUpdateUserViaGroupId(getPara("groupId"),"0");
                renderJson(result.getJson());
            }
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }

    public void replyUser(){
        try {
            String msgtype = getPara("msgtype");
            ApiResult result = null;
            //发送文字消息
            if("text".equals(msgtype))
                 result = CustomServiceApi.sendText(getPara("openId"),getPara("content").replaceAll("<br/>","\n"));
            //发送图片消息
            if("image".equals(msgtype))
                 result = CustomServiceApi.sendImage(getPara("openId"),getPara("media_id"));
            //发送图文消息
            if("mpnews".equals(msgtype))
                result = CustomServiceApi.sendMpNews(getPara("openId"),getPara("media_id"));
            if(!result.isSucceed()){
                renderJson("errorInfo",result.getErrorMsg());
            }else{
                renderJson(result.getJson());
            }
        } catch (Exception e) {
            renderJson("errorInfo",e.getMessage());
        }
    }
}
