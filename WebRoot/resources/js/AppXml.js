 $(document).ready(function(){
  			$("#deployXml").click(
  				function(thiz){
  					var xmlXML=$('#xmlXML').val();
  					showMsg("xxxxxxxx")
  					//alert(xmlXML)
  			});
  			
 			$("#saveXmlInfo").click(
  				function(thiz){
	  				var xmlName=$('#xmlName').val();
	  				var xmlFlowFuns=$('#xmlFlowFuns').val();
	  				var xmlRemark=$('#xmlRemark').val();
	  				
  					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :{ name :xmlName,flowFuns:xmlFlowFuns,remark:xmlRemark},
						url : "admin/app/xml/saveInfo",// 请求的action路径
						error : function() {// 请求失败处理函数
							showError("保存失败")
						},
						success : function(data) {
							if (data=='true') {
								showMsg("保存成功")
							}
						}
					});
	  				
  			});
 })
 
