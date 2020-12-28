import request from '@/utils/request'

export const getBedTypeList = () => { return request({ url: '/api/bedType/allType', method: 'get' }) }
