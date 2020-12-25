import request from '@/utils/request'

const headers = { 'Content-type': 'application/json;charset=utf-8'};

// 登录
export const login = params => { return request({ url: '/api/admin/login', method: 'post', data: params, headers: headers}) }
// 退出登录
export const loginout = params => { return request({ url: '/HotelManagement/json/login/out', method: 'get', params: params }) }
// 获取当前登录用户的信息
export const getLoginEmployee = params => { return request({ url: '/api/admin/adminInfo', method: 'get', params: params }) }
