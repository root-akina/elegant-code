import request from '@/utils/request'

export function fetchList(data) {
  console.log('查询资源列表参数：' + data);
  return request({
    url: 'role/page/'+data.pageNum+"/"+data.pageSize,
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
export function initDeptTree(data) {
  return request({
    url: 'dept/tree',
    method: 'post',
    data,
  })
}



export function createInfo(data) {
  console.log('保存信息：' + data);
  return request({
    url: 'role',
    method: 'put',
    data,
  })
}

export function updateInfo(data) {
  console.log('修改信息：' + data);
  return request({
    url: 'role',
    method: 'patch',
    data,
  })
}



