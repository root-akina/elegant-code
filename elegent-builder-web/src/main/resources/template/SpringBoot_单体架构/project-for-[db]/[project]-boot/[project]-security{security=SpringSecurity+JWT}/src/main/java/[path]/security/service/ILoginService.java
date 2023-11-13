package ${base.basePackage}.security.service;

import ${base.basePackage}.common.vo.UserVo;

/**
 * 登录接口
 */
public interface ILoginService {

    /**
     * 登录
     * @param userVo
     * @return
     */
    public UserVo login(UserVo userVo);

    /**
     * 退出登录
     * @return
     */
    public Boolean logout();

}
