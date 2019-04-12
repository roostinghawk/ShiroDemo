package liuwei.demo.shiro.service;


import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

      /**
       * 根据登录名查找用户
       * @param loginName
       * @return
       */
      User findUserByLoginName(String loginName);

      /**
       * 根据用户ID查找角色
       * @param uid
       * @return
       */
      List<Role> findRolesByUid(int uid);

      /**
       * 根据角色ID查找权限
       * @param roleId
       * @return
       */
      List<Permission> findPermissionsByRoleId(int roleId);


      void addUser(User user);

      List<User> findUsers();

}
