// pages/my/patient.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sexColumns: [{
        name: '男',
        value:1
      },
      {
        name: '女',
        value:0
      }
    ],
    relationColumns: [{
        name: '本人',
      },
      {
        name: '父母',
      },
      {
        name: '子女',
      },
      {
        name: '朋友',
      },
      {
        name: '亲属',
      },
      {
        name: '其他',
      }
    ],
    areaColumns: [{
        name: '中国大陆+86',
        value: '+86'
      },
      {
        name: '中国澳门+853',
        value: '+853'
      },
      {
        name: '中国香港+852',
        value: '+852'
      }
    ],
    areaShow: false,
    sexShow: false,
    relationShow: false,
    formData:{
      areaLabel: "中国大陆+86",
      areaCode: "+86",
      phone:'',
      relationLabel:'',
      relationValue:'',
      idCard:'',
      sexLabel:'',
      sexValue:0,
      name:''
    }
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

  closePopup() {
    this.setData({
      sexShow: false,
      relationShow: false,
      areaShow: false
    })
  },

  showSexPopup() {
    this.setData({
      sexShow: true
    })
  },

  showRelation() {
    this.setData({
      relationShow: true
    })
  },

  showArea() {
    this.setData({
      areaShow: true,
    })
  },
  onSelectArea(event) {
    const formData = this.data.formData;
    formData.areaLabel = event.detail.name;
    formData.areaCode = event.detail.value;

    this.setData({
      formData :formData
    });
  },
  onSelectRelation(event){
    const formData = this.data.formData;
    formData.relationLabel = event.detail.name;
    
    this.setData({
      formData :formData
    });
  },
  onSelectSex(event){
    const formData = this.data.formData;
    formData.sexLabel = event.detail.name;
    formData.sexValue = event.detail.value;
    this.setData({
      formData :formData
    });
  }


  
})