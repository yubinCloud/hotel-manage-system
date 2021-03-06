import request from '@/utils/request'
// 查询客房类型列表
export const getRoomTypeList = params => { return request({ url: '/api/roomType/selectRoomType', method: 'get', params: params }) }

// 新增客房类型列表
export const addRoomType = params => { return request({ url: '/HotelManagement/json/roomType/add', method: 'post', params: params }) }

// 根据ID查询客房类型列表
export const getRoomTypeById = roomTypeId => { return request({ url: '/HotelManagement/json/roomType/getRoomTypeById?roomTypeId=' + roomTypeId, method: 'get', params: roomTypeId }) }


// 修改客房类型列表
export const updateRoomType = params => { return request({ url: '/HotelManagement/json/roomType/update', method: 'put', params: params }) }

// 删除客房类型列表
export const delRoomType = roomTypeId => { return request({ url: '/HotelManagement/json/roomType/delete?roomTypeId=' + roomTypeId, method: 'delete', params: roomTypeId }) }
