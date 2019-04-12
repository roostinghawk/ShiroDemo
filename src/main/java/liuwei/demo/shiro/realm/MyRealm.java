package liuwei.demo.shiro.realm;

import liuwei.demo.shiro.model.Permission;
import liuwei.demo.shiro.model.Role;
import liuwei.demo.shiro.model.User;
import liuwei.demo.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuwei
 * @date 2019/3/30
 */
public class MyRealm extends AuthorizingRealm {

    @Resource(name = "userServiceImpl")
    private UserService userService;


    /**
     * 提供帐户信息，返回认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String)authenticationToken.getPrincipal();
        User user = userService.findUserByLoginName(loginName);
        if(user == null) {
            //用户不存在就抛出异常
            throw new UnknownAccountException();
        }

        //密码可以通过SimpleHash加密，然后保存进数据库。
        //此处是获取数据库内的账号、密码、盐值，保存到登陆信息info中
        return new SimpleAuthenticationInfo(
                loginName,
                user.getPassword(),
                ByteSource.Util.bytes(Hex.decode(user.getSalt())),
                getName());
    }


    /**
     * 提供用户信息，返回权限信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String loginName = (String) principals.getPrimaryPrincipal();
        User user = userService.findUserByLoginName(loginName);
        List<Role> roles = userService.findRolesByUid(Integer.parseInt(user.getUid()));
        Set<String> roleSet = new HashSet<>();
        Set<String>  permissionSet = new HashSet<>();
        for(Role role : roles) {
            List<Permission> permissions = userService.findPermissionsByRoleId(role.getId());
            for(Permission permission : permissions) {
                permissionSet.add(permission.getPermission());
            }
        }
        // 将角色名称提供给授权info
        authorizationInfo.setRoles(roleSet);
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissionSet);

        return authorizationInfo;
    }
}
