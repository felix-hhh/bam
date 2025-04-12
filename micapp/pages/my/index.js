// pages/my/index.js
import {
  sendGet
} from "../../utils/request"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    loginState: true,
    userInfo: null,
    patientList: [] // 添加病人列表数据
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
    this.getTabBar().init();
    this.getPatientList();
    this.getUserInfo();
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

  gotoAddPatient() {
    wx.navigateTo({
      url: '/pages/my/patient'
    });
  },

  // 获取用户信息
  getUserInfo() {
    const app = getApp();
    const userInfo = app.globalData.userInfo;
    console.log("用户信息",userInfo)
    if (userInfo) {
      this.setData({
        userInfo: userInfo
      });
    }
  },

  // 获取病人列表
  getPatientList() {
    sendGet({
      url: '/medical/front/patient/list',
      data: {}
    }).then(res => {
      this.setData({
        patientList: res
      });
    }).catch(err => {
      console.error('获取病人列表失败:', err);
    });
  },

  // 添加工具区域的跳转功能
  gotoRecord() {
    wx.navigateTo({
      url: '/pages/record/index'
    });
  },

  gotoReport() {
    wx.navigateTo({
      url: '/pages/report/index'
    });
  },

  gotoVideo() {
    wx.navigateTo({
      url: '/pages/video/index'
    });
  }
})