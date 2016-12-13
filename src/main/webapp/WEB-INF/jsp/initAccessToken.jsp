<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-panel" title="自动获取access_token" fit="true">
    <form  style="padding: 10px" id="ff" action="<%=request.getContextPath()%>/wx/getAccessToken" method="post">
        <table>
            <tr>
                <td>APPID:</td>
                <td><input name="appId" class="f1 easyui-textbox"></input></td>
            </tr>
            <tr>
                <td>APPSECRET:</td>
                <td><input name="appSecret" class="f1 easyui-textbox"></input></td>
            </tr>
            <tr>

                <td colspan="2" >
                    ACCESS_TOKEN:<span  class="f1"></span>
                </td>
            </tr>
            <tr>

                <td colspan="2" align="right"><input type="submit" value="提交"></input></td>
            </tr>
        </table>
    </form>
</div>
<style scoped>
    .f1{
        width:200px;
    }
</style>
<script type="text/javascript">
    $(function(){
        $('#ff').form({
            success:function(data){
                $.messager.alert('access_token', $.parseJSON(data).appId, 'info');
            }
        });
    });
</script>