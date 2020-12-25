import axios from 'axios';

// const service = axios.create({
//     // process.env.NODE_ENV === 'development' 来判断是否开发环境
//     // easy-mock服务挂了，暂时不使用了
//     // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
//     baseURL: 'http://120.24.186.190:8080',
//     timeout: 5000
// });
axios.withCredentials = true
const service = axios.create({
  baseURL: '',
  // baseURL: process.env.VUE_APP_BASE_URL,
  withCredentials: true,
  timeout: 5000,
  headers: {
    'Access-Control-Allow-Origin': 'http://127.0.0.1:8085',
    'Access-Control-Allow-Credentials': true,
    'Access-Control-Allow-Headers': 'X-Requested-With,Content-Type',
    'Access-Control-Allow-Methods': 'PUT,POST,GET,DELETE,OPTIONS',
    'Content-Type': 'application/json'
  }
})

// service.interceptors.request.use(
//     config => {
//         return config;
//     },
//     error => {
//         console.log(error);
//         return Promise.reject();
//     }
// );

// service.interceptors.response.use(
//     response => {
//         if (response.status === 200) {
//             return response.data;
//         } else {
//             Promise.reject();
//         }
//     },
//     error => {
//         console.log(error);
//         return Promise.reject();
//     }
// );

export default service;
