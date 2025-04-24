// app.js
App({
  onLaunch() {
    this.checkUserLogin();
  },
  globalData: {
    userLogin: false,
    userToken: null
  },
  /**
   * 检查用户登录
   */
  checkUserLogin() {
    const userToken = wx.getStorageSync('userToken');
    console.log("===" + userToken);
    if (!userToken) {
      //未登录
     
    } else {
      this.globalData.userLogin = true;
      this.globalData.userToken = userToken;
    }
  }
})