(function() {
  "use strict";
  this.userApp.header = {
    data : {
      "id" : "",
      "className" : "",
      "theme" : "",
      "options" : {
        "fixed" : false
      },
      "content" : {
        "left" : [{
              "link" : "javascript:App.loadJsp(__ctx + '/jsp/indexContent.jsp')",
              "title" : "",
              "icon" : "home",
              "customIcon" : "",
              "className" : ""
            }],
        "title" : "朗捷通"
      }
    },
    init : function() {
      var $tpl = $('#amz-tpl-header');
      var source = $tpl.text();
      var template = Handlebars.compile(source);
      var html = template(this.data);
      $tpl.before(html);
    }
  }
}).call(this);
(function() {
  "use strict";
  this.userApp.menu = {
    data : {
      "options" : {
        "cols" : 1
      },
      "content" : [{
            "link" : "javascript:App.loadJsp('jsp/indexContent.jsp')",
            "title" : "设备",
            "subCols" : 2,
            "channelLink" : "进入栏目 »",
            "subMenu" : []
          }, {
            "link" : "login",
            "title" : "登出"
          }],
      "theme" : "offcanvas1"
    },
    init : function() {
      var thiz = this;
      var $tpl = $('#amz-tpl-menu');
      var source = $tpl.text();
      var template = Handlebars.compile(source);
      $.ajax({
            url : 'listDevice.do',
            success : function(data, textStauts, jqXHR) {
              App.ArrayPushAll(thiz.data.content[0].subMenu, data)
              var html = template(thiz.data);
              $tpl.before(html);
              var module = $.AMUI['menu'];
              module && module.init && module.init();
              //close amaze menu
              App.amazeMenuClose();
            }
          });

    }
  }
}).call(this);
$(function() {
  "use strict";
  userApp.header.init();
  userApp.menu.init();
});