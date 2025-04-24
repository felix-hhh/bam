// const baseUrl = "http://192.168.0.4:10000";
const baseUrl = "https://api.connectsir.com";
const authHeader = {};

// 处理401未授权的情况
const handleUnauthorized = () => {
  const app = getApp();
  const currentPages = getCurrentPages();
  console.log("currentPages", currentPages)
  // 清除用户token
  app.globalData.userToken = '';
  const currentPage = currentPages[currentPages.length - 1];
  console.log("currentPage", currentPage);
  console.log("pageName", currentPage.route);
  if (currentPage.route !== 'pages/my/login') {
    // 显示提示信息
    wx.showToast({
      title: '请登录使用',
      icon: 'none',
      duration: 2000
    });
    // 跳转到登录页
    setTimeout(() => {
      wx.navigateTo({
        url: '/pages/my/login'
      });
    }, 2000);
  }
};

const getToken = () => {
  const app = getApp();
  const token = app.globalData.userToken;
  console.log("token:", token);
  if (token === null) {
    return false;
  }
  authHeader["Authorization"] = `bearer ${token}`;
  return true;
}

/**
 * 检查状态码
 * @param statusCode 状态码 
 */
const checkStatusCode = (statusCode, repData, resolve, reject) => {
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
  return new Promise((resolve, reject) => {
    getToken();
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
        checkStatusCode(statusCode, repData, resolve, reject);
      },
      fail: (err) => {
        reject(err);
      },
    })
  });
}

const sendGet = (parmas) => {

  return new Promise((resolve, reject) => {
    getToken()
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


  });
}

module.exports = {
  sendPost,
  sendGet
}