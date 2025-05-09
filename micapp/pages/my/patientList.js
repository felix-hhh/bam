const {
  sendGet
} = require("../../utils/request")

// pages/my/patientList.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    patientList: []
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
    this.getPatientList();
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

  getPatientList() {
    sendGet({
      url: "/medical/front/patient/list"
    }).then(req => {
      this.setData({
        patientList: req
      })
    })
  },

  gotoPatientEdit(event) {
    const id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/my/patientAdd?id=${id}`,
    });
  }
})