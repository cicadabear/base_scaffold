package cc.cicadabear.api.web.validator.user;

import cc.cicadabear.api.common.utils.MatchUtils;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.domain.dto.user.RegisterFormDto;
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
public class RegisterFormDtoValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public void validate(Object o, ResultVo resultVo) {
        resultVo.fail(false);
        RegisterFormDto registerFormDto = (RegisterFormDto) o;
        validateMobile(registerFormDto, resultVo);
        if (resultVo.fail()) return;

        validatePassword(registerFormDto, resultVo);
        if (resultVo.fail()) return;

        validateValiCode(registerFormDto, resultVo);
        if (resultVo.fail()) return;

    }

    private void validateMobile(RegisterFormDto regDto, ResultVo resultVo) {
        if (!MatchUtils.isMobileNo(regDto.getUsername())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.mobile_no_error"));
            return;
        }
        if (userService.isExistedMobile(regDto.getUsername())) {
            resultVo.setCode(HttpStatus.SC_FORBIDDEN);
            resultVo.fail(MessageUtils.getMessage("user.form.validator.mobile_already_exists"));
            return;
        }
    }

    private void validatePassword(RegisterFormDto regDto, ResultVo resultVo) {
        if (!MatchUtils.isValidPassword(regDto.getPassword()) || !MatchUtils.isValidPassword(regDto.getRePassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.password_format_error"));
            return;
        }
        if (!regDto.getPassword().equals(regDto.getRePassword())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.passwords_inconsistent"));
            return;
        }
    }

    private void validateValiCode(RegisterFormDto regDto, ResultVo resultVo) {
        if (!MatchUtils.isSixNumbersCode(regDto.getCode())) {
            resultVo.fail(MessageUtils.getMessage("user.form.validator.sms.valicode_wrong"));
            return;
        }
    }
}
