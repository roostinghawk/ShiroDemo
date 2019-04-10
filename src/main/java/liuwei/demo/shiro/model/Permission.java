package liuwei.demo.shiro.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限表
 * @author liuwei
 * @date 2019/4/7
 */
@Getter
@Setter
public class Permission {

    private Integer id;

    private String name;

    private Integer parentId;

    private String permission;

    private String resourceType;

    private String url;
}
