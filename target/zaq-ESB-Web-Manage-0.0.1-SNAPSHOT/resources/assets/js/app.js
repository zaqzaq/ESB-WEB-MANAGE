(function($) {
  'use strict';
  $(function() {
        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function() {
              $.AMUI.fullscreen.toggle();
            });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
          $.AMUI.fullscreen.isFullscreen ? $fullText.text('关闭全屏') : $fullText
              .text('开启全屏');
        });
      });
})(jQuery);
var __ctx = '';
(function() {
  "use strict";
  this.userApp = {};
}).call(this);
App = {
  ArrayPushAll : function(target, from) {
    if (this.isArray(target) && this.isArray(from)) {
      Array.prototype.push.apply(target, from);
    } else {
      console.warn("target or from is not an Array object")
    }
  },
  /**
   * 赋值操作 如果有值才覆盖
   * 
   * @param {}
   *          object 目标对象
   * @param {}
   *          config 赋值
   * @return {} AppUtil.apply(obj,config)
   */
  apply : function(object, config, defaults) {
    if (defaults) {
      apply(object, defaults);
    }
    if (object && config && typeof config === 'object') {
      var i;
      for (i in config) {// 复制所有属性
        console.info(i)
        object[i] = config[i];
      }
    }
    return object;
  },
  applyIf : function(object, config) {
    var property;
    if (object) {
      for (property in config) {
        if (object[property] === undefined) {// 如果object中不含有这个属性，复制属性，否则不复制
          object[property] = config[property];
        }
      }
    }
    return object;
  },
  isEmpty : function(value, allowEmptyString) {
    return (value === null) || (value === undefined)
        || (!allowEmptyString ? value === '' : false)
        || (this.isArray(value) && value.length === 0);
  },
  isArray : ('isArray' in Array) ? Array.isArray : function(value) {
    return toString.call(value) === '[object Array]';
  },
  dynamicLoadJsp : function(url, context, data) {
    $.ajax({
          url : __ctxPath + url,
          type : "Post",
          data : data == null ? {} : data,
          dataType : 'html',
          success : function(html) {
            $(context).html(html);
          },
          error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('加载页面出错');
          }
        });
  },
  loadJsp : function(url) {
    $('#page-wrapper').load(url);
  },
  amazeMenuClose:function(){
  	var $menus = $('[data-am-widget="menu"]');
  	var $nav = $menus.find('.am-offcanvas');
    $nav.find('.am-menu-nav .am-parent ul.am-menu-sub li a').on('click', function() {        
        $nav.offCanvas('close');
      })
  }
};
