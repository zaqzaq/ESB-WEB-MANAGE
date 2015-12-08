(function() {
  "use strict";
  this.userApp.accordion = {
    data : {
        "id": "",
        "className": "",
        "theme": "default",
        "content": [
          {
            "title": "就这样恣意的活着",
            "content": "置身人群中<br/>你只需要被淹没 享受 沉默<br/>退到人群后<br/>你只需给予双手 微笑 等候",
            "active": true
          },
          {
            "title": "让生命去等候，去等候，去等候，去等候",
            "content": "走在忠孝东路<br/>徘徊在茫然中<br/>在我的人生旅途<br/>选择了多少错误"
          },
          {
            "title": "我就这样告别山下的家",
            "content": "我就这样告别山下的家，我实在不愿轻易让眼泪留下。我以为我并不差不会害怕，我就这样自己照顾自己长大。我不想因为现实把头低下，我以为我并不差能学会虚假。怎样才能够看穿面具里的谎话？别让我的真心散的像沙。如果有一天我变得更复杂，还能不能唱出歌声里的那幅画？"
          }
        ]
      },
    init : function() {
      var $tpl = $('#amz-tpl-accordion');
      var source = $tpl.text();
      var template = Handlebars.compile(source);
      var html = template(this.data);
      $tpl.before(html);
      var module = $.AMUI['accordion'];
      module && module.init && module.init();
    }
  }
}).call(this);
$(function() {
  "use strict";
  userApp.accordion.init();
  var ctx = document.getElementById("myChart").getContext("2d");
  var data = [{
        value : 30,
        color : "#F7464A",
        label : '健康生活'
      }, {
        value : 150,
        color : "#E2EAE9",
        label : '工作效率'
      }, {
        value : 50,
        color : "#D4CCC5",
        label : '身体指数'
      }, {
        value : 40,
        color : "#949FB1",
        label : '制定目标'
      }];
  var chart = new Chart(ctx).Doughnut(data, {
        percentageInnerCutout : 80,
        segmentStrokeWidth : 4,
        centerTitleFontStyle : "normal bold",
        centerTitleFontSize : 18,
        animation : false,
        centerTitle : '朗捷通智能家居'
      });
});