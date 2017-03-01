package cc.cicadabear.profile.infrastructure;

import cc.cicadabear.profile.domain.user.User;
import cn.iutils.common.config.JConfig;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码操作
 *
 * @author Jack
 */
@Service
public class PasswordHelper {

    private Logger logger = LoggerFactory.getLogger(PasswordHelper.class);
    private org.apache.log4j.Logger logger4j = org.apache.log4j.Logger.getLogger(PasswordHelper.class.getName());
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${profile.shiro.algorithmName}")
    private String algorithmName;
    //    private String algorithmName = JConfig.getConfig("profile.shiro.algorithmName");
    @Value("${profile.shiro.hashIterations}")
    private int hashIterations;
//    private int hashIterations = JConfig.getInteger("profile.shiro.hashIterations");


    MessageDigestPasswordEncoder encoder;

    @PostConstruct
    public void test() {
        System.out.println("============" + algorithmName);
        logger.info("============" + algorithmName);
        logger4j.info("============" + algorithmName);
        logger.debug("============" + algorithmName);
        logger4j.error("============" + algorithmName);

        encoder = new MessageDigestPasswordEncoder(algorithmName);
        encoder.setIterations(hashIterations);

    }

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
//        String encPass = new SimpleHash(algorithmName, rawPass,
//                ByteSource.Util.bytes(salt),
//                hashIterations).toHex();
        String encPass = encoder.encodePassword(rawPass, salt);
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

        user.salt(KeyGenerators.string().generateKey());

        String newPassword = encoder.encodePassword(user.password(), user.salt());
        user.password(newPassword);
//        user.salt(randomNumberGenerator.nextBytes().toHex());
//        String newPassword = new SimpleHash(algorithmName, user.password(),
//                ByteSource.Util.bytes(user.salt()),
//                hashIterations).toHex();
//        user.password(newPassword);
    }

    /**
     * 获取密码
     *
     * @param user
     * @return
     */
    public String getPassword(User user) {
//        String password = new SimpleHash(algorithmName, user.password(),
//                ByteSource.Util.bytes(user.salt()),
//                hashIterations).toHex();
        String password = encoder.encodePassword(user.password(), user.salt());
        return password;
    }

}
