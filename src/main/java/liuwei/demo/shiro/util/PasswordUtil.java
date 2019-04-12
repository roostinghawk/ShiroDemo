package liuwei.demo.shiro.util;


import liuwei.demo.shiro.consts.Const;
import liuwei.demo.shiro.model.User;
import org.apache.shiro.codec.Hex;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author liuwei
 * @date 2019/4/12
 */
public class PasswordUtil {

    private static SecureRandom random = new SecureRandom();

    public static void entryptPassword(User user) {
        byte[] saltBytes = generateSaltBytes(Const.SALT_BYTES_LENGTH);
        user.setSalt(Hex.encodeToString(saltBytes));
        byte[] hashPassword = digest(user.getPassword().getBytes(), Const.HASH_ALGORITHM,
                saltBytes, Const.HASH_INTERATIONS);
        user.setPassword(Hex.encodeToString(hashPassword));
    }

    /**
     * 生成随机的Byte[]作为salt.
     *
     * @param numBytes byte数组的大小
     */
    private static byte[] generateSaltBytes(int numBytes) {
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("加密异常", e);
        }
    }
}
