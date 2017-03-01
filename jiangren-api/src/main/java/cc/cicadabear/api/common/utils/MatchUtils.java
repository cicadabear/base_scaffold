package cc.cicadabear.api.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author Shengzhao Li
 */
public abstract class MatchUtils {

    /**
     * BigDecimal regex.
     */
    public static final String BIG_DECIMAL_REGEX = "^(\\d+)||(\\d+\\.\\d+)$";

    /**
     * Positive Number regex.
     */
    public static final String POSITIVE_NUMBER_REGEX = "^\\d+$";

    /**
     * Email regex.
     */
    public static final String EMAIL_REGEX = ".+@.+\\.[a-z]+";

    /**
     *
     */
    public static final String PASSWORD_REGEX = "^[0-9A-Za-z]{6,20}$";

    public static final String VALICODE_REGEX = "^[0-9]{6}$";

    public static final String PASSWORD_SECURITY_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    public static final String MOBILE_REGEX = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";


    public static final String CREATE_TEXT = "create";


    private MatchUtils() {
    }

    public static boolean isBigDecimal(String text) {
        return StringUtils.isNotEmpty(text) && text.matches(BIG_DECIMAL_REGEX);
    }

    public static boolean isEmail(String email) {
        return StringUtils.isNotEmpty(email) && email.matches(EMAIL_REGEX);
    }

    public static boolean isPositiveNumber(String numberText) {
        return StringUtils.isNotEmpty(numberText) && numberText.matches(POSITIVE_NUMBER_REGEX);
    }

    public static boolean isCreate(String text) {
        return CREATE_TEXT.equalsIgnoreCase(text);
    }

    public static boolean isValidPassword(String password) {
        return StringUtils.isNotEmpty(password) && password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidSecurityPassword(String password) {
        return StringUtils.isNotEmpty(password) && password.matches(PASSWORD_SECURITY_REGEX);
    }

    /**
     * 检测手机号有效性*
     *
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static final boolean isMobileNo(String mobile) {
        return StringUtils.isNotEmpty(mobile) && mobile.matches(MOBILE_REGEX);
    }

    public static final boolean isSixNumbersCode(String code) {
        return StringUtils.isNotEmpty(code) && code.matches(code);
    }


}