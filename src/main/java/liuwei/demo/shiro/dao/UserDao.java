package liuwei.demo.shiro.dao;


import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userDao")
public interface UserDao {

    @Select("select * from user where loginName = #{loginName}")
    User findUserByLoginName(String loginName);

    @Insert("insert into user(loginName,password,salt,name,state) VALUES(#{loginName}, #{password}, #{salt}, #{name}, #{state})")
    void insert(User user);

    @Select("select * from user where state=1")
    List<User> findUsers();

    @Select("select r.* from user_role ur inner join role r on ur.role_id=r.id where uid = #{uid}")
    List<Role> findRoles(int uid);

    @Select("select p.* from permission p inner join role_permission rp on  p.id=rp.permission_id where rp.role_id = #{roldId}")
    List<Permission> findPermissionsByRoleId(int roleId);
}