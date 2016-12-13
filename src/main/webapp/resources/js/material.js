(function ($,window) {
    var material = window.material = {};

    //ui组件
    function popover(ele,e) {
        var pop = $("#popoverDel");
        var popWidth = pop.width();
        pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
    }

    $(function () {
        //新建素材
        $("#addNews").click(function () {
            top.window.addTab("新建素材",serverContext+"/weixin/api/initEditor");
        });
        //编辑素材
        $("li[name='editNews']").click(function () {
            var _self = $(this);
            var media_id = _self.attr("media_id");
            //打开新的tab页
            top.window.addTab("素材编辑--" + $("#"+media_id).attr("title"),serverContext+'/weixin/api/initEditor?media_id=' + media_id);
        });
        //删除素材
        $("li[name='deleteNews']").click(function (e) {
            var _self = $(this);
            var media_id = _self.attr("media_id");
            //保存待删除图文ID
            $("#materialId").val(media_id);
            popover(_self,e);
        });
        //取消删除素材
        $("#cancleDel").click(function () {
            $("#popoverDel").hide();
        });
        //确定删除素材
        $("#confirmDel").click(function () {
            var media_id = $("#materialId").val();
            delMaterial(media_id,function () {
                $("#popoverDel").hide();
                $("#" + media_id).remove();
             });
        });
    });
})(jQuery,window);