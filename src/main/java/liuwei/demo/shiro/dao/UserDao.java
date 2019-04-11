package liuwei.demo.shiro.dao;


import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userDao")
public interface UserDao {

    User findUserByLoginName(String loginName);

    List<Role> findRoles(int uid);

    List<Permission> findPermissionIdByRoleId(int roleId);

}