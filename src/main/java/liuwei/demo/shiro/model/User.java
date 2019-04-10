package liuwei.demo.shiro.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 * @author liuwei
 */
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 4798316249512579846L;

    private String uid;
    /**
     * 账号
     */
    private String loginName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密用盐
     */
    private String salt;

    /**
     * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
     */
    private String state;

    private List<Role> roleList;


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}