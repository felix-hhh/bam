// pages/my/index.js
import {
  sendGet
} from "../../utils/request"
import {checkUserLoginStatus} from "../../utils/util"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading:true,
    loginState: false,
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
    const userLoginStatus = checkUserLoginStatus();
    this.setData({
      loginState:userLoginStatus
    })
    if(userLoginStatus){
      this.getPatientList();
      this.getProfile();
    }
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
      url: '/pages/my/patientAdd'
    });
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
    wx.switchTab({
      url: '/pages/report/index'
    });
  },

  gotoReport() {
    wx.switchTab({
      url: '/pages/report/index'
    });
  },

  gotoVideo() {
    wx.switchTab({
      url: '/pages/report/index'
    });
  },

  gotoPatientList(){
    wx.navigateTo({
      url: '/pages/report/index',
    });
  },

  gotoSetting(){
    wx.navigateTo({
      url: '/pages/my/setting',
    })
  },

  getProfile() {
    sendGet({
      url: "/user/front/info/my"
    }).then(req => {
      this.setData({
        userInfo: req
      })
    });
  },

  gotoLogin(){
    wx.navigateTo({
      url: '/pages/my/login',
    })
  }
})