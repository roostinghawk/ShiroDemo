package liuwei.demo.shiro.consts;

/**
 * @author liuwei
 * @date 2019/4/11
 */
public class Const {

    public static final String SESSION_KEY_USERNAME = "username";

    /**
     * 密码加密算法
     */
    public static final String HASH_ALGORITHM = "md5";

    /**
     * 哈希次数
     */
    public static final int HASH_INTERATIONS = 3;

    /**
     * 盐的字节长度
     */
    public static final int SALT_BYTES_LENGTH = 6;
}
