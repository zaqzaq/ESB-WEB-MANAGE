<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="nav nav-tabs">
  <li class="active"><a href="#home" data-toggle="tab">详情</a></li>
  <li><a href="#profile" data-toggle="tab">XML内容</a></li>
</ul>

<div class="row">
  <div class="col-md-12">
    <br>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active in" id="home">
      <form id="tab">
	        <div class="form-group">
	        <label>名称</label>
	        <input type="text" value="${name }" class="form-control">
	        </div>
	        <div class="form-group">
	        <label>功能</label>
	        <input type="text" value="${flowFuns }" class="form-control">
	        </div>
	        <div class="form-group">
	        <label>备注</label>
	        <input type="text" value="${remark }" class="form-control">
	        </div>
      </form>
      </div>

      <div class="tab-pane fade" id="profile">

        <form id="tab2">
          <div class="form-group">
            <textarea class="form-control" rows="10" >${xml }</textarea>
          </div>
        </form>
      </div>
    </div>

    <div class="btn-toolbar list-toolbar">
      <button class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
    </div>
  </div>
</div>
