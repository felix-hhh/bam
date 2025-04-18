import {
  sendGet,
  sendPost
} from "../../utils/request";
import {
  formatDatetime
} from "../../utils/util";
// pages/report/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    recordList: [],
    reportList:[]
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
    this.getRecord();
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
   * 取回就诊记录
   */
  getRecord() {
    sendGet({
      url: "/medical/front/queue/list"
    }).then(req => {
      const reqData = req;
      reqData.forEach(item => {
        if (item.diagnosticTime !== null) {
          item.diagnosticTime = formatDatetime(item.diagnosticTime);
        }
      });

      this.setData({
        recordList: reqData
      });
    });
  },
  getReport() {
    sendGet({
      url: `/medical/front/queue/list/completed`
    }).then(req => {
      const reqData = req;
      reqData.forEach(item => {
          item.diagnosticTime = formatDatetime(item.diagnosticTime);
      });
      this.setData({
        reportList: reqData
      })
    })
  },
  /**
   * 就诊记录详情
   */
  gotoDetail(event) {
    const id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/report/detail?id=${id}`,
    });
  },
  tabChange(event) {
    const tabName = event.detail.name;

    switch (tabName) {
      case 0:
        this.getRecord();
        break;
      case 1:
        this.getReport();
        break;
      case 2:
        getVideos();
        break;
    }
  },
})