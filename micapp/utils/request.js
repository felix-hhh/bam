const baseUrl = "http://192.168.0.4:10000";
// const baseUrl = "https://api.yidingjiaoyu.net";
const authHeader = {};

// 处理401未授权的情况
const handleUnauthorized = () => {
  const app = getApp();
  // 清除用户token
  app.globalData.userToken = '';
  // 显示提示信息
  console.log("登录提示")
  wx.showToast({
    title: '登录已过期，请重新登录',
    icon: 'none',
    duration: 2000
  });
  // 跳转到登录页
  setTimeout(() => {
    wx.navigateTo({
      url: '../pages/login/index'
    });
  }, 2000);
};

/**
 * 检查状态码
 * @param statusCode 状态码 
 */
const checkStatusCode = (statusCode,repData,resolve, reject) =>{
  switch (statusCode) {
    case 200:
      if (repData.success) {
        resolve(repData.data);
      } else {
        wx.showToast({
          icon: "error",
          title: repData.errMsg
        });
        reject(repData);
      }
      break;
    case 401:
      handleUnauthorized();
      break;
    default:
      wx.showToast({
        title: "请求失败",
        icon: "error"
      });
      reject(repData);
  }
}

const sendPost = (parmas) => {
  const app = getApp();
  authHeader["Authorization"] = "bearer " + app.globalData.userToken;
  console.log(authHeader);
  return new Promise((resolve, reject) => {
    wx.request({
      url: baseUrl + parmas.url,
      data: parmas.data,
      method: "POST",
      header: {
        'content-type': 'application/json',
        ...authHeader
      },
      success: (res) => {
        const statusCode = res.statusCode;
        const repData = res.data;
        checkStatusCode(statusCode, repData,resolve, reject);
      },
      fail: (err) => {
        reject(err);
      },
    })
  })
}

const sendGet = (parmas) => {
  const app = getApp();
  authHeader["Authorization"] = "bearer " + app.globalData.userToken;
  return new Promise((resolve, reject) => {
    wx.request({
      url: baseUrl + parmas.url,
      data: parmas.data,
      method: "GET",
      header: {
        'content-type': 'application/json',
        ...authHeader
      },
      success: (res) => {
        const statusCode = res.statusCode;
        const repData = res.data;

        checkStatusCode(statusCode, repData, resolve, reject);
      },
      fail: (err) => {
        console.log("======");
        console.log(err);
        wx.showToast({
          title: "网络繁忙",
          icon: "error"
        });
        reject(err);
      },
    })
  })
}

module.exports = {
  sendPost,
  sendGet
}