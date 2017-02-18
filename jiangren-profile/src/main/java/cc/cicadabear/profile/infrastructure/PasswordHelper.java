package cc.cicadabear.profile.infrastructure;

import cc.cicadabear.profile.domain.user.User;
import cn.iutils.common.config.JConfig;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 密码操作
 *
 * @author Jack
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${profile.shiro.algorithmName}")
    private String algorithmName;
    //    private String algorithmName = JConfig.getConfig("profile.shiro.algorithmName");
    @Value("${profile.shiro.hashIterations}")
    private int hashIterations;
//    private int hashIterations = JConfig.getInteger("profile.shiro.hashIterations");

    public void setRandomNumberGenerator(
            RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }


    public String encodePassword(String rawPass, Object salt) {
        String encPass = new SimpleHash(algorithmName, rawPass,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
        return encPass;
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return encPass.equals(encodePassword(rawPass, salt));
    }

    /**
     * 密码加密
     *
     * @param user
     */
    public void encryptPassword(User user) {
        user.salt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, user.password(),
                ByteSource.Util.bytes(user.salt()),
                hashIterations).toHex();
        user.password(newPassword);
    }

    /**
     * 获取密码
     *
     * @param user
     * @return
     */
    public String getPassword(User user) {
        String password = new SimpleHash(algorithmName, user.password(),
                ByteSource.Util.bytes(user.salt()),
                hashIterations).toHex();
        return password;
    }

}
