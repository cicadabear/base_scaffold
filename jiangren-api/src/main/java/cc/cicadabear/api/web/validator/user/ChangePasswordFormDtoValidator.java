package cc.cicadabear.api.web.validator.user;

import cc.cicadabear.api.common.utils.MatchUtils;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.domain.dto.user.ChangePasswordFormDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Jack on 2/25/17.
 */
@Component
public class ChangePasswordFormDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePasswordFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public void validate(Object o, ResultVo resultVo) {
        resultVo.fail(false);
        ChangePasswordFormDto formDto = (ChangePasswordFormDto) o;

        validatePassword(formDto, resultVo);
        if (resultVo.fail()) return;
    }


    private void validatePassword(ChangePasswordFormDto formDto, ResultVo resultVo) {
        if (!MatchUtils.isValidPassword(formDto.getOldPassword()) || !MatchUtils.isValidPassword(formDto.getRePassword()) || !MatchUtils.isValidPassword(formDto.getNewPassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.password_format_error"));
            return;
        }
        if (!formDto.getNewPassword().equals(formDto.getRePassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.passwords_inconsistent"));
            return;
        }
    }

}
