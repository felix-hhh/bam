import axios, { AxiosResponse, HttpStatusCode } from "axios";
import { ElMessage } from "element-plus";
import router from "@/router";
import useStore from "@/stores";
import { ResponseData } from "#/entity.ts";

const useAxios = () => {
  axios.defaults.baseURL = import.meta.env.VITE_API_URL;

  const instance = axios.create({
    timeout: 1000 * 60 * 2,
    headers: {
      "Content-Type": "application/json",
    },
  });

  instance.interceptors.request.use(
    (config) => {
      const store = useStore();
      const bearer = store.getToken();
      const token = bearer !== undefined ? `bearer ${bearer}` : "";
      if (token !== "" && token !== undefined) {
        config.headers.authorization = token;
      }
      return config;
    },
    (error) => {
      console.error(error);
      return Promise.reject(error);
    },
  );

  instance.interceptors.response.use(
    (config) => {
      if (!config.data.success) {
        ElMessage.error(config.data.errMsg);
        throw Error(config.data.errMsg);
      }
      return config;
    },
    (error) => {
      const response = error.response;
      if (response !== undefined && response.status === HttpStatusCode.Unauthorized) {
        router.push("/");
        return;
      }
      if (response) {
        if (response.data) {
          const errMsg = response.data.errMsg || error.message;
          ElMessage.error(errMsg);
          throw Error(errMsg);
        } else {
          ElMessage.error("服务器繁忙");
          throw Error("服务器繁忙");
        }
      } else {
        ElMessage.error("服务器繁忙");
        throw Error("服务器繁忙");
      }
    },
  );

  /**
   * 取回请求url
   * @returns {string}
   */
  const getBasePath = (): string | undefined => {
    return axios.defaults.baseURL;
  };

  /**
   * 发送post请求
   * @param url 请求地址
   * @param params 请求参数
   * @returns
   */
  const sendPost = (url: string, params?: object) => {
    if (params == null) {
      params = {};
    }
    return instance.post(url, params).then((res: AxiosResponse) => res.data.data);
  };

  /**
   * 发送get请求
   * @param url 请求地址
   * @param params 请求参数
   */
  const sendPut = (url: string, params?: object) => {
    if (params == null) {
      params = {};
    }
    return instance.put(url, params).then((res) => res.data.data);
  };

  /**
   * 发送del请求
   * @param url 请求地址
   * @param params 请求参数
   */
  const sendDel = (url: string, params: object | undefined) => {
    if (params == null) {
      params = {};
    }
    return instance.delete(url, {
      data: params,
    }).then((res) => res.data.data);
  };

  /**
   * 发送 Get 请求
   * @param url 请求地址
   * @param params 请求参数
   */
  const sendGet = <T = any>(url: string, params?: object): Promise<T | undefined> => {
    if (params == null) {
      params = {};
    }
    return instance.get<T, AxiosResponse<ResponseData<T>>>(url, params).then((res) => res.data.data);
  };

  /**
   * 发送post请求，病返回错误消息
   * @param url 请求地址
   * @param params 请求参数
   * @param errHandler 错误处理器
   */
  const sendPostShowMsg = (url: string, params?: object, errHandler?: Function) => {
    if (params == null) {
      params = {};
    }
    return instance.post(url, params).then((res) => {
      const data = res.data;
      if (data.errMsg) {
        if (errHandler) {
          errHandler();
        }
        return;
      } else {
        if (data.success) {
          const result = data.data;
          if (result === undefined || result === null) {
            return data;
          }
          return result;
        }
        return data;
      }
    });
  };

  return { getBasePath, sendPost, sendPut, sendDel, sendGet, sendPostShowMsg };
};

export default useAxios;

