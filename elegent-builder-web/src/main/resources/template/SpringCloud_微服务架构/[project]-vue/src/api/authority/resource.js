import request from '@/utils/request'

export function fetchList(data) {
  console.log('查询资源列表参数：' + data)
  return request({
    url: 'resource/page/'+data.pageNum+"/"+data.pageSize,
    method: 'post',
    data,
  })
}

export function createResourceNo(data) {
  return request({
    url: 'resource/create-resource-no/'+data,
    method: 'post',
    data,
  })
}

export function initTree(data) {
  return request({
    url: 'resource/tree',
    method: 'post',
    data,
  })
}

export function createInfo(data) {
  console.log('保存信息：' + data)
  return request({
    url: 'resource',
    method: 'put',
    data,
  })
}

export function updateInfo(data) {
  console.log('修改信息：' + data)
  return request({
    url: 'resource',
    method: 'patch',
    data,
  })
}


