import request from '@/utils/request'

export function fetchList(data) {
  
  return request({
    url: '${db.serviceName}-service/${table.varName}/search?pageIndex='+data.pageNum+"&pageSize="+data.pageSize,
    method: 'post',
    data,
  })
}


export function createInfo(data) {
 
  return request({
    url: '${db.serviceName}-service/${table.varName}',
    method: 'post',
    data,
  })
}

export function updateInfo(data,id) {
 
  return request({
    url: '${db.serviceName}-service/${table.varName}/'+id,
    method: 'put',
    data,
  })
}



