package liuwei.demo.shiro.service.impl;

import liuwei.demo.shiro.dao.UserDao;
import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;
import liuwei.demo.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author liuwei
 * @date 2019/4/7
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByLoginName(String loginName) {
        return userDao.findUserByLoginName(loginName);
    }

    @Override
    public List<Role> findRolesByUid(int uid) {
        return userDao.findRoles(uid);
    }

    @Override
    public List<Permission> findPermissionsByRoleId(int roleId) {
        return userDao.findPermissionIdByRoleId(roleId);
    }

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }

    @Override
    public List<User> findUsers() {
        return userDao.findUsers();
    }
}
