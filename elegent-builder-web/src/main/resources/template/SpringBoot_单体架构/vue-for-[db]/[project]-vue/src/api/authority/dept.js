import request from '@/utils/request'

export function fetchList(data) {
  console.log('查询部门列表参数：' + data)
  return request({
    url: 'dept/page/'+data.pageNum+"/"+data.pageSize,
    method: 'post',
    data,
  })
}

export function initUser(data) {
  console.log('查询用户列表参数：' + data);
  return request({
    url: 'user/list',
    method: 'post',
    data,
  })
}
export function createDeptNo(data) {
  return request({
    url: 'dept/create-dept-no/'+data,
    method: 'post',
    data,
  })
}

export function initTree(data) {
  return request({
    url: 'dept/tree',
    method: 'post',
    data,
  })
}

export function createInfo(data) {
  console.log('保存部门：' + data)
  return request({
    url: 'dept',
    method: 'put',
    data,
  })
}

export function updateInfo(data) {
  console.log('修改部门：' + data)
  return request({
    url: 'dept',
    method: 'patch',
    data,
  })
}


