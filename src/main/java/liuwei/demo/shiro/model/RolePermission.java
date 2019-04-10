package liuwei.demo.shiro.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色权限对应表
 * @author liuwei
 */
@Getter
@Setter
public class RolePermission {

    private Integer permissionId;

    private Integer roleId;
}