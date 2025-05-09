import {
  sendGet
} from "../../utils/request";
import {
  formatDatetime
} from "../../utils/util"

// pages/report/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    reportId: null,
    reportDetail: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const reportId = options.id;
    this.setData({
      reportId: reportId
    })
    this.getReportDetail(reportId);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {},

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
  callPhone() {
    wx.makePhoneCall({
      phoneNumber: this.data.reportDetail.hospitalPhone,
    })
  },
  getReportDetail(id) {
    sendGet({
      url: "/medical/front/queue/get/" + id
    }).then(req => {
      if (req.diagnosticTime !== null)
        req.diagnosticTime = formatDatetime(req.diagnosticTime);
      this.setData({
        reportDetail: req
      });
      this.changeNavColor(req.status);
    })
  },
  changeNavColor(status) {
    let backgroundColor = "#ffffff";
    switch (status) {
      case "M_Q_S_COMPLETED":
        backgroundColor = "#edfaf5";
        break;
      case "M_Q_S_IN_PROGRESS":
        backgroundColor = "#FFF3E1";
        break;
      case "M_Q_S_QUEUING":
        backgroundColor = "#FFF3E1";
        break;
        case "M_Q_S_EXPIRED":
        backgroundColor = "#EFEFEF";
        break;
        case "M_Q_S_CANCELLED":
        backgroundColor = "#EFEFEF";
        break;
    }
    wx.setNavigationBarColor({
      backgroundColor: backgroundColor,
      frontColor: '#000000',
    })
  }

})