import request from '@/utils/request'
import global from '@/api/global'

// 查询所有菜单信息
export function getMenusInfo() {
  let url = "resource/menus/"+global.systemCode;
  return request({
    url: url,
    method: 'get'
  })
}
