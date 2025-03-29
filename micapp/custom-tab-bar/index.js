// custom-tab-bar/index.js
Component({

  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    active: 0,
    list: [{
        "text": "首页",
        "icon": "wap-home-o",
        "activeIcon": "wap-home",
        "pagePath": "/pages/home/index"
      },
      {
        "text": "记录",
        "icon": "completed-o",
        "activeIcon": "completed",
        "pagePath": "/pages/report/index"
      },
      {
        "text": "我的",
        "icon": "user-o",
        "activeIcon": "user",
        "pagePath": "/pages/my/index"
      }
    ]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    init() {
      let _this = this;
      const page = getCurrentPages().pop();
      _this.setData({
        active: _this.data.list.findIndex(item => item.pagePath === `/${page.route}`)
      });
    },
    onChange(e) {
      let _this = this;
      wx.switchTab({
        url: _this.data.list[e.detail].pagePath
      })
    }
  }
})