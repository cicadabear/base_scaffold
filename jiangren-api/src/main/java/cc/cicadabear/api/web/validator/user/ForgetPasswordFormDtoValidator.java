package cc.cicadabear.api.web.validator.user;

import cc.cicadabear.api.common.utils.MatchUtils;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.domain.dto.user.ForgetPasswordFormDto;
import cc.cicadabear.profile.service.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Jack on 2/25/17.
 */
@Component
public class ForgetPasswordFormDtoValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ForgetPasswordFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public void validate(Object o, ResultVo resultVo) {
        resultVo.fail(false);
        ForgetPasswordFormDto formDto = (ForgetPasswordFormDto) o;

        validateMobile(formDto, resultVo);
        if (resultVo.fail()) return;

        validatePassword(formDto, resultVo);
        if (resultVo.fail()) return;

        validateValiCode(formDto, resultVo);
        if (resultVo.fail()) return;

    }


    private void validatePassword(ForgetPasswordFormDto formDto, ResultVo resultVo) {
        if (!MatchUtils.isValidPassword(formDto.getPassword()) || !MatchUtils.isValidPassword(formDto.getRePassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.password_format_error"));
            return;
        }
        if (!formDto.getPassword().equals(formDto.getRePassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.passwords_inconsistent"));
            return;
        }
    }

    private void validateMobile(ForgetPasswordFormDto regDto, ResultVo resultVo) {
        if (!MatchUtils.isMobileNo(regDto.getMobile())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.mobile_no_error"));
            return;
        }
        if (!userService.isExistedMobile(regDto.getMobile())) {
            resultVo.setCode(HttpStatus.SC_FORBIDDEN);
            resultVo.fail(MessageUtils.getMessage("user.form.validator.mobile_not_exists"));
            return;
        }
    }


    private void validateValiCode(ForgetPasswordFormDto regDto, ResultVo resultVo) {
        if (!MatchUtils.isSixNumbersCode(regDto.getCode())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.sms.valicode_wrong"));
            return;
        }
    }

}
