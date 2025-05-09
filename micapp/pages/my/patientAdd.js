// pages/my/patient.js
import {
  sendPost
} from "../../utils/request"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    genderColumns: [{
        name: '男',
        value: 1
      },
      {
        name: '女',
        value: 0
      }
    ],
    relationColumns: [{
        name: '本人',
        value: 'M_P_R_SELF'
      },
      {
        name: '父母',
        value: 'M_P_R_PARENT'
      },
      {
        name: '子女',
        value: 'M_P_R_CHILD'
      },
      {
        name: '朋友',
        value: 'M_P_R_FRIEND'
      },
      {
        name: '亲属',
        value: 'M_P_R_RELATIVE'
      },
      {
        name: '其他',
        value: 'M_P_R_OTHER'
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
    genderShow: false,
    relationShow: false,
    minDate: new Date(1940, 0, 1).getTime(),
    maxDate: new Date(1990, 11, 31).getTime(),
    currentDate: new Date(1990, 0, 1).getTime(),
    checked: false,
    formData: {
      areaLabel: "中国大陆+86",
      areaCode: "+86",
      phone: '',
      relationLabel: '',
      relationValue: '',
      idCard: '',
      genderLabel: '',
      genderValue: null,
      name: '',
      age: null,
      defaultPatient: false,
      agree: false
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
    console.log("close")
    this.setData({
      genderShow: false,
      relationShow: false,
      areaShow: false,
      birthdayShow: false
    })
  },

  showGenderPopup() {
    this.setData({
      genderShow: true
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
      formData: formData
    });
  },
  onSelectRelation(event) {
    const formData = this.data.formData;
    formData.relationLabel = event.detail.name;
    formData.relationValue = event.detail.value;

    this.setData({
      formData: formData
    });
  },
  onSelectGender(event) {
    const formData = this.data.formData;
    formData.genderLabel = event.detail.name;
    formData.genderValue = event.detail.value;
    this.setData({
      formData: formData
    });
  },
  onDefaultPatient(event) {
    const formData = this.data.formData;
    formData.defaultPatient = event.detail;
    this.setData({
      formData: formData
    });
  },
  onAgree(event) {
    const formData = this.data.formData;
    formData.agree = event.detail;
    this.setData({
      formData: formData
    });
  },
  onInputName(event) {
    const formData = this.data.formData;
    formData.name = event.detail;
    this.setData({
      formData: formData
    });
  },
  onInputIdCard(event) {
    const formData = this.data.formData;
    formData.idCard = event.detail;
    this.setData({
      formData: formData
    });
  },
  onInputAge(event) {
    const formData = this.data.formData;
    formData.age = event.detail;
    this.setData({
      formData: formData
    });
  },
  onInputPhone(event) {
    const formData = this.data.formData;
    formData.phone = event.detail;
    this.setData({
      formData: formData
    });
  },
  onChange(event) {
    this.setData({
      checked: event.detail
    });
  },
  onBirthdayConfirm(event) {
    const date = new Date(event.detail);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const birthday = `${year}-${month}-${day}`;

    const formData = this.data.formData;
    formData.birthday = birthday;
    this.setData({
      formData: formData,
      birthdayShow: false
    });
  },
  validateForm() {
    const formData = this.data.formData;
    console.log(formData);
    if (!formData.name) {
      wx.showToast({
        title: '请输入姓名',
        icon: 'none'
      });
      return false;
    }
    if (!formData.genderValue) {
      wx.showToast({
        title: '请选择性别',
        icon: 'none'
      });
      return false;
    }
    if (!formData.idCard) {
      wx.showToast({
        title: '请输入身份证号',
        icon: 'none'
      });
      return false;
    }
    if (!formData.relationValue) {
      wx.showToast({
        title: '请选择关系',
        icon: 'none'
      });
      return false;
    }
    if (!formData.phone) {
      wx.showToast({
        title: '请输入手机号',
        icon: 'none'
      });
      return false;
    }
    if (!formData.age) {
      wx.showToast({
        title: '请输入年龄',
        icon: 'none'
      });
      return false;
    }
    if (!formData.agree) {
      wx.showToast({
        title: '请同意授权书',
        icon: 'none'
      });
      return false;
    }
    return true;
  },
  submitForm() {
    if (!this.validateForm()) {
      return;
    }

    const formData = this.data.formData;
    const submitData = {
      name: formData.name,
      gender: formData.genderValue,
      idCard: formData.idCard,
      relation: formData.relationValue,
      phone: formData.areaCode + formData.phone,
      age: formData.age,
      defaultPatient: formData.defaultPatient
    };

    console.log("submitData",submitData);

    sendPost({
      url: '/medical/front/patient/add',
      data: submitData
    }).then(res => {
      wx.showToast({
        title: '添加成功',
        icon: 'success'
      });
      setTimeout(() => {
        wx.navigateBack();
      }, 1500);
    }).catch(err => {
      wx.showToast({
        title: '添加失败',
        icon: 'error'
      });
    });
  }
})