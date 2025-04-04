const baseUrl = "http://localhost:10000";
// const baseUrl = "https://api.yidingjiaoyu.net";
const authHeader = {};

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
        const repData = res.data;
        if (repData.success) {
          resolve(repData.data);
        } else {
          wx.showToast({
            icon: "error",
            title: repData.errMsg
          })
        }

      },
      fail: (err) => {
        reject(err)
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
        const errMsg = repData.errMsg;
        switch (statusCode) {
          case 200:
            if (repData.success) {
              resolve(repData.data);
            } else {
              wx.showToast({
                icon: "error",
                title: errMsg
              })
            }
            break;
          case 401:

            break;
        }


      },
      fail: (err) => {
        console.log("======");
        console.log(err);
        wx.showToast({
          title: "网络繁忙",
          icon: "error"
        })
        // reject(err);
      },
    })
  })
}

module.exports = {
  sendPost,
  sendGet
}