(function() {
  "use strict";
  this.userApp.listNews = {
     data : {
      "id" : "",
      "className" : "",
      "theme" : "default",
      "options" : {
        // normal、thumb
        "type" : "thumb",
        // 当type为 thumb 时必传
        "thumbPosition" : "bottom-left"
        // top-一般用于全宽模式
        // bottom - bottom-left | bottom-right
        // left - right
      },
      // 数据传递
      "content" : {
        "header" : {
          "title" : "保温茶垫飞天猫", // 栏目标题
          "link" : "#",
          "className" : "",
          "moreText" : "", // 更多链接显示文字
          "morePosition" : "" // 【更多】链接位置，top、bottom，如果不设置则不显示更多链接
        },
        "main" : [{
          "title" : "保温茶垫-设备添加日期 <2014-12-12>", // 新闻标题
          "link" : "#", // 新闻链接
          "desc" : "囧人囧事囧照，人在囧途，越囧越萌。标记《带你出发，陪我回家》",
          "img" : "resources/img/electricCup.jpg" // 图片链接
        }]
      }
    },
    init : function() {
      var thiz = this;
      var $tpl = $('#amz-tpl-listNews');
      var source = $tpl.text();
      var template = Handlebars.compile(source);
      $.ajax({
            url : 'listDevice.do',
            success : function(data, textStauts, jqXHR) {
              var html = template(thiz.data);
              $tpl.before(html);
              var module = $.AMUI['list_news'];
              module && module.init && module.init();
            }
          });

    }
  }
  this.electricCup = {
  	on_off : function(){
  		var flag = false;
  		var electricOn_off = $("#electricOn_off");
  		if(electricOn_off.text() == "停止"){
  		  flag = true;
  		}
  		if(flag){
            electricOn_off.removeClass("am-btn-default");
  		    electricOn_off.addClass("am-btn-primary");
            electricOn_off.text("运行");
  		}else{
      		electricOn_off.removeClass("am-btn-primary");
      		electricOn_off.addClass("am-btn-default");
            electricOn_off.text("停止");
  		}
  	}
  }
}).call(this);
$(function() {
      "use strict";
      userApp.listNews.init();
      
      $("#electricOn_off").click(function(){
        electricCup.on_off()
      });
    });