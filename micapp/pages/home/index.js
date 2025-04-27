import {
  sendGet
} from "../../utils/request";
import {checkUserLoginStatus} from "../../utils/util"

// pages/home/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    orderType: [
      { name: '智能排序', value: 0 },
    ],
    localcationOpt: [
      { name: '全城', value: 'a' },
    ],
    locaclcationLabel:"全城",
    orderTypeLabel:"智能排序",
    patientList: [], // 病人列表数据
    hospitals: [],
    loading: true,
    loginState: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {},

  // 获取病人列表
  getPatientList() {
    sendGet({
        url: "/medical/front/patient/list",
        data: {},
      })
      .then((res) => {
        this.setData({
          patientList: res,
          loading: false
        });
      })
      .catch((err) => {
        console.error("获取病人列表失败:", err);
      });
  },

  // 加载医院列表
  loadHospitals() {
    sendGet({
      url: "/medical/front/hospital/list",
    }).then((res) => {
      this.setData({
        hospitals: res,
        loading: false
      });
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getTabBar().init();
    const userLoginStatus = checkUserLoginStatus();
    this.setData({
      loginState: userLoginStatus
    });
    if (userLoginStatus) {
      this.getPatientList();
    }
    this.loadHospitals();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {},

  gotoAddPatient() {
    wx.navigateTo({
      url: "/pages/my/patientAdd",
    });
  },
  gotoUserLogin(){
wx.navigateTo({
  url: '/pages/my/login',
})
  },

  gotoPartners() {
    wx.navigateTo({
      url: "/pages/home/partners",
    });
  },
});