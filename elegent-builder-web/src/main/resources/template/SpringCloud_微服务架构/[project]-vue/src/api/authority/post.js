import request from '@/utils/request'

export function fetchList(data) {
  console.log('查询职位列表参数：' + data)
  return request({
    url: 'auth/post/page/'+data.pageNum+"/"+data.pageSize,
    method: 'post',
    data,
  })
}

export function createInfo(data) {
  console.log('保存职位：' + data)
  return request({
    url: 'auth/post',
    method: 'put',
    data,
  })
}

export function updateInfo(data) {
  console.log('修改职位：' + data)
  return request({
    url: 'auth/post',
    method: 'patch',
    data,
  })
}

export function createPostNo(data) {
  return request({
    url: 'auth/post/create-post-no/'+data,
    method: 'post',
    data,
  })
}

export function changePosts(data) {
  console.log('保存职位：' + data)
  return request({
    url: 'auth/post/list',
    method: 'post',
    data,
  })
}


