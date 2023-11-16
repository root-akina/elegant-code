package ${base.basePackage}.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @Description：用户表
 */
@Data
@NoArgsConstructor
public class UserVo extends BaseVo {

    @Builder
    public UserVo(Long id, String dataState, String username, String password, String userType, String nickName, String email, String realName, String mobile, String sex, String remark, String[] checkedIds, String openId, Set<String> roleVoIds, Set<DeptPostUserVo> deptPostUserVoSet, Set<String> roleLabels, Set<String> resourceRequestPaths, String deptNo, String postNo, Long roleId, String userToken) {
        super(id, dataState);
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.nickName = nickName;
        this.email = email;
        this.realName = realName;
        this.mobile = mobile;
        this.sex = sex;
        this.remark = remark;
        this.checkedIds = checkedIds;
        this.openId = openId;
        this.roleVoIds = roleVoIds;
        this.deptPostUserVoSet = deptPostUserVoSet;
        this.roleLabels = roleLabels;
        this.resourceRequestPaths = resourceRequestPaths;
        this.deptNo = deptNo;
        this.postNo = postNo;
        this.roleId = roleId;
        this.userToken = userToken;
    }


    private String username;


    private String password;


    private String userType;


    private String nickName;


    private String email;


    private String realName;


    private String mobile;


    private String sex;


    private String remark;


    private String[] checkedIds;


    private String openId;


    private Set<String>  roleVoIds;


    private Set<DeptPostUserVo>  deptPostUserVoSet;


    private Set<String> roleLabels;


    private Set<String> resourceRequestPaths;


    private String deptNo;


    private String postNo;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;


    private String userToken;


    private DataSecurity dataSecurity;

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getUsername(), getPassword(), getUserType(), getNickName(), getEmail(), getRealName(), getMobile(), getSex(), getRemark(), getOpenId(), getRoleVoIds(), getDeptPostUserVoSet(), getRoleLabels(), getResourceRequestPaths(), getDeptNo(), getPostNo(), getRoleId(), getUserToken());
        result = 31 * result + Arrays.hashCode(getCheckedIds());
        return result;
    }
}
