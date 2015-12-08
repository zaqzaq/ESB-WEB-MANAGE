<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/x-handlebars-template" id="amz-tpl-listNews">
    {{>list_news}}
</script>
<button type="button" id="electricOn_off" class="am-btn am-btn-primary am-round">启动</button>
<input type="checkbox" name="my-checkbox" checked>
<script type="text/javascript" src="${ctx}/resources/js/electricCup.js"></script>
<script>
$("[name='my-checkbox']").bootstrapSwitch('state',true).bootstrapSwitch('onColor','success').bootstrapSwitch('offColor','danger');
</script>

