import moment from "moment";

/**
 * 时间戳格式化
 * @param row
 * @param obj
 * @param data 数据
 * @returns {string}
 */
const datetimeFormat = (row, obj, data) => {
  let date = data;
  if (date === undefined || date == null) {
    return "";
  }
  if (date.toString().length === 10) {
    date = date * 1000;
  }
  return moment(date).utc(true).format("YYYY-MM-DD HH:mm:ss");
};

/**
 *
 * @param row
 * @param obj
 * @param data
 * @returns {string}
 */
const duration = (row, obj, data) => {
  let date = data;
  if (date === undefined || date == null) {
    return "";
  }
  const durationTemp = moment.duration(date);
  const day = durationTemp.days();
  const hours = durationTemp.hours();
  const minutes = durationTemp.minutes();

  return `${day}天${hours}小时${minutes}分钟`;
};

const moneyFormat = (row, obj, data) => {
  return (Math.floor(data * 100) / 100).toFixed(2);
};

/**
 * boolean格式化
 * @param row
 * @param obj
 * @param data
 * @returns {string}
 */
const booleanFormat = (row, obj, data) => {
  return data ? "是" : "否";
};

/**
 * 深度拷贝
 * @param data 数据
 * @returns {any}
 */
const depthCopy = (data) => {
  return JSON.parse(JSON.stringify(data));
};

const createSearchModel = (searchItem, limit, page, sort, dir) => {
  const searchModel = {
    limit: limit || 20,
    page: typeof page === "number" ? page : 1,
    data: {},
    sort: sort,
    dir: dir !== null ? dir === "descending" ? "DESC" : "ASC" : "",
  };
  searchItem.value.forEach(item => {
    if (item.value !== undefined) {
      const value = typeof item.value === "string" ? `"${item.value}"` : item.value;
      const text = `{"${item.key}":${value}}`;
      searchModel.data = JSON.parse(text);
    }
  });
  return searchModel;
};

export default {};

export { duration, datetimeFormat, moneyFormat, booleanFormat, createSearchModel, depthCopy };
