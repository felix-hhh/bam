const {
  sendPost
} = require("../../utils/request")
const {
  base64Decode
} = require("../../utils/util")
// pages/my/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    agree: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 
   * @param {获取手机号码} event 
   */
  userLogin(event) {
    const code = event.detail.code;
    console.log("code:",code);
    if(code === undefined){
      wx.exitMiniProgram();
      return;
    }
    const app = getApp();

    sendPost({
      url: "/user/front/info/login",
      data: {
        "loginType": "WXM_PHONE",
        "code": code,
        "deviceInfo": {}
      }
    }).then(res => {
          app.globalData.userLogin = true;
          const dataArray = res.split(".");
          app.globalData.userToken = dataArray[2];
          //获取用户基础信息，并存储在公共数据中。
          const userInfoData = JSON.parse(base64Decode(dataArray[1]));
          const userData = JSON.parse(userInfoData.userData);
          app.globalData.userInfo = userData;
          console.log(userData)
          wx.setStorageSync('userToken', dataArray[2]);
          wx.setStorageSync('userInfo', userInfoData.userData);
          
          wx.navigateBack();
    })
  },
  changeAgree(event) {
    this.setData({
      agree:event.detail
    })
  }
})