import request from '@/utils/request'

// 财务核算
export const getfinList = params => { return request({ url: 'api/order/turnover', method: 'get', params: params }) }
