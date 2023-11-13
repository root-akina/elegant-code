import request from '@/utils/request'
import global from '@/api/global'

export function login(code,username,password,mobile,authCode,loginType) {
  console.log("=====登录信息：====="+username,password,mobile,authCode,loginType);
  const data = {
    "username":username,
    "password":password
  };
  return request({
    url: 'login',
    method: 'post',
    headers:{
      'Content-Type':"application/json"
    },
    data: data
  })
}

export function logout() {
  return request({
    url: 'security/logout',
    method: 'post'
  })
}

export function getInfo() {
  return request({
    url: 'user/current-user',
    method: 'get'
  })
}

export function initRoles(data) {
  return request({
    url: 'role/list',
    method: 'post',
    data,
  })
}

export function fetchList(data) {
  console.log('查询用户列表参数：' + data);
  return request({
    url: 'user/page/'+data.pageNum+"/"+data.pageSize,
    method: 'post',
    data,
  })
}

export function createInfo(data) {
  return request({
    url: 'user',
    method: 'put',
    data,
  })
}

export function resetPasswords(data) {
  return request({
    url: 'user/reset-passwords/'+data,
    method: 'post',
  })
}


export function updateInfo(data) {
  console.log('修改用户信息：' + data);
  return request({
    url: 'user',
    method: 'patch',
    data,
  })
}






