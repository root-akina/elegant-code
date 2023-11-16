package ${base.basePackage}.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_user")
public class User extends BaseEntity {

    private String username;

    private String openId;

    private String password;

    private String userType;

    private String avatar;

    private String nickName;

    private String email;

    private String realName;

    private String mobile;

    private String sex;

    private String dataState;

    private static final long serialVersionUID = 1L;

    @Builder
    public User(Long id, String dataState, String username, String openId, String password, String userType, String avatar, String nickName, String email, String realName, String mobile, String sex, String dataState1) {
        super(id, dataState);
        this.username = username;
        this.openId = openId;
        this.password = password;
        this.userType = userType;
        this.avatar = avatar;
        this.nickName = nickName;
        this.email = email;
        this.realName = realName;
        this.mobile = mobile;
        this.sex = sex;
        this.dataState = dataState1;
    }
}
