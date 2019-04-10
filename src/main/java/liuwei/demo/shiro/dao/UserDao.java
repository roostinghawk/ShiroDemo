package liuwei.demo.shiro.dao;


import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;

import java.util.List;

public interface UserDao {

    User findUserByLoginName(String loginName);

    List<Role> findRoles(int uid);

    List<Permission> findPermissionIdByRoleId(int roleId);

}