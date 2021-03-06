<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="resources/js/AppXml.js" type="text/javascript"></script>	
<ul class="nav nav-tabs">
  <li class="active"><a href="#info" data-toggle="tab">详情</a></li>
  <li><a href="#xmlContent" data-toggle="tab">XML内容</a></li>
</ul>

<div class="row">
    <br>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active fade in col-md-5" id="info">
		        <div class="form-group">
		       	<input type="hidden" id="xmlId" name="xmlId" value="${id }" class="form-control">
		        <label>名称</label>
		        <input type="text" id="xmlName" name="xmlName" value="${name }" class="form-control">
		        </div>
		        <div class="form-group">
		        <label>功能</label>
     		    <textarea class="form-control" id="xmlFlowFuns" name="xmlFlowFuns"  rows="3" >${flowFuns }</textarea>
		        </div>
		        <div class="form-group">
		        <label>备注</label>
		        <textarea class="form-control" id="xmlRemark" name="xmlRemark"  rows="3" >${remark }</textarea>
		        </div>
		        <div class="btn-toolbar list-toolbar">
			      <button class="btn btn-primary" id="saveXmlInfo"><i class="fa fa-save"></i> 保存</button>
			    </div>
      </div>

      <div class="tab-pane col-md-12" id="xmlContent">
          <div class="form-group">
            <textarea class="form-control" id="xmlXML" name="xmlXML"  rows="15" >${xml }</textarea>
          </div>
           <div class="btn-toolbar list-toolbar">
		      <button class="btn btn-success" id="deployXml"><i class="glyphicon glyphicon-heart"></i> 部署</button>
		    </div>
      </div>
    </div>

</div>
