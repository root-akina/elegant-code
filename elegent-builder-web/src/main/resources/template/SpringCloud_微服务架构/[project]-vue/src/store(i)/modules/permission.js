import { asyncRoutes, constantRoutes } from '@/router'
import { getMenusInfo } from '@/api/menus'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    console.log('目标角色：' + route.meta.roles)
    console.log('校验角色：' + roles)
    console.log('校验结果：' + roles.some(role => route.meta.roles.includes(role)))
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = [];

  routes.forEach(route => {
    const tmp = { ...route };
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  });

  return res
}

/**
 * 后台查询的菜单数据拼装成路由格式的数据
 * @param routes
 */
export function generaMenu(routes, data) {  

  data.forEach(item => {
    const component = r => require.ensure([], () => r(require('@/views/' + item.component)));
    const menu = {
      path: item.path,
      component: item.component === 'Layout' ? Layout : component,
      hidden: item.hidden,
      // alwaysShow: true, // will always show the root menu
      name: item.name,
      meta: {
        title: item.meta.title,
        icon: item.meta.icon,
        roles: item.meta.roles
      },
      children: []
    };
    if (item.children) {
      generaMenu(menu.children, item.children)
    }
    routes.push(menu)
  });

}

const state = {
  routes: [],
  addRoutes: []
};

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = constantRoutes.concat(routes)
  }
};
// 装载资源信息
const actions = {
  
  generateRoutes({ commit }, roles) {

    asyncRoutes.length = 0;
    return new Promise((resolve, reject) => {
      let accessedRoutes;
      getMenusInfo().then(response => {
        const { data } = response;
        //构建菜单       
       
        generaMenu(asyncRoutes, data);
        if (roles.includes('admin')) {
          // 超级管理员有所有权限
          accessedRoutes = asyncRoutes || []
        } else {
          // 对应角色过滤
          accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
        }
        
        commit('SET_ROUTES', accessedRoutes);
        resolve(accessedRoutes)
      }).catch(error => {
        reject(error)
      })
    })
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
