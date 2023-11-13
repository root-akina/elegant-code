import request from '@/utils/request'

export function fetchList(data) {
  
  return request({
    url: '${table.varName}/search?pageIndex='+data.pageNum+"&pageSize="+data.pageSize,
    method: 'post',
    data,
  })
}


export function createInfo(data) {
 
  return request({
    url: '${table.varName}',
    method: 'post',
    data,
  })
}

export function updateInfo(data,id) {
 
  return request({
    url: '${table.varName}/'+id,
    method: 'put',
    data,
  })
}



