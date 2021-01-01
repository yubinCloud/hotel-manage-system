import request from '@/utils/request'

export const backupDb = () => { return request({ url: 'api/admin/backup', method: 'post' }) }

export const recoverDb = () => { return request({ url: 'api/admin/recover', method: 'post' }) }
