package cc.cicadabear.business.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * @author Shengzhao Li
 */
public class MessageUtils {


    private static MessageSource messageSource;


    public static String getMessage(String code) {
        return getMessage(code, new String[]{});
    }

    public static String getMessage(String code, Object... args) {
        //Just set default message is code
        return getMessage(code, code, args);
    }

    public static String getMessage(String code, String defaultMessage, Object... args) {
        return messageSource.getMessage(code, args, defaultMessage, currLocale());
    }


    /**
     * If not found locale from  the current logged user,
     * will use default locale  SIMPLIFIED_CHINESE.
     *
     * @return Locale instance
     */
    public static Locale currLocale() {
//        final user user = SecurityUtils.currUser();
//        if (user != null) {
//            return user.language().resolveLocale();
//        }
//        //default
        return Locale.SIMPLIFIED_CHINESE;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource is null");
        MessageUtils.messageSource = messageSource;
    }
}