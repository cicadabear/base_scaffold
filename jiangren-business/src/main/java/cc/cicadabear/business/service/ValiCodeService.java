package cc.cicadabear.business.service;

import cc.cicadabear.business.common.CrudService;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.common.utils.DateUtils;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.common.utils.RandomUtils;
import cc.cicadabear.business.common.utils.SMSUtils;
import cc.cicadabear.business.domain.dao.IValiCodeDao;
import cc.cicadabear.business.domain.entity.user.ValiCode;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限服务
 *
 * @author cc
 */
@Service
@Transactional(readOnly = false)
public class ValiCodeService extends CrudService<IValiCodeDao, ValiCode> {

    public static final int MAX_SEND_TRY_TIMES = 5;//发送不成功最多尝试5次

    public static final int MAX_VERIFY_TRY_TIMES = 7;//验证不成功最多尝试7次

    public static final int MAX_RESEND_TIME_SECONDS = 90;//90秒才可以重新发送

    public static final int MAX_VALIDCODE_ALIVE_TIME_MINUTES = 20;//默认验证码有效时间为20分钟

    @Transactional(readOnly = true)
    public ValiCode findValidCode(String mobile) {
        ValiCode validCode = dao.getValiCodeByMobile(mobile);

        return validCode;
    }

    /**
     * 发送验证码
     *
     * @param mobile
     * @param type
     * @param resultVo
     * @return
     */
    @Transactional(readOnly = false)
    public ResultVo sendValiCode(String mobile, Integer type, ResultVo resultVo) {
        ValiCode validCode = findValidCode(mobile);
        if (validCode != null) {
            if (DateUtils.periodAsSeconds(validCode.getUpdateDate(), DateUtils.now()) < MAX_RESEND_TIME_SECONDS) {
                resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.send_too_frequent"));
                resultVo.setRet(ResultVo.FAILURE);
                resultVo.setCode(HttpStatus.SC_FORBIDDEN);
                return resultVo;
            } else if (validCode.getExpireTime().before(DateUtils.now())) {
                delete(validCode.getId());
                validCode = null;
            }
        }

        int sendTimes = 0;
        String code = RandomUtils.randomNumbers(6);
        while (!SMSUtils.sendCode(mobile, code, type) && sendTimes < MAX_SEND_TRY_TIMES) {
            sendTimes++;
        }
        if (validCode == null) {
            validCode = new ValiCode();
            validCode.setNewId(true);
        }
        validCode.setExpireTime(DateUtils.plusMinutes(MAX_VALIDCODE_ALIVE_TIME_MINUTES));
        validCode.setTarget(mobile);
        validCode.setCode(code);
        validCode.setType(type);
        save(validCode);

        resultVo.setCode(200);
        resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.send_success"));
        resultVo.setRet(ResultVo.SUCCESS);
        return resultVo;
    }


    /**
     * 检验验证码
     *
     * @param mobile
     * @param type
     * @param code
     * @param resultVo
     * @return
     */
    @Transactional(readOnly = false)
    public ResultVo checkValiCode(String mobile, Integer type, String code, ResultVo resultVo) {

        boolean isFail = false;
        ValiCode validCode = findValidCode(mobile);

        resultVo.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        resultVo.setRet(ResultVo.FAILURE);

        if (validCode != null) {
            if (validCode.getExpireTime().before(DateUtils.now())) {
                resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.valicode_expired"));
                delete(validCode);
                isFail = true;
            }
            if (!validCode.getType().equals(type)) {
                resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.valicode_retry"));
                delete(validCode);
                isFail = true;
            }
            //已经错过多于两次
            if (validCode.getErrorTimes() > 2 && validCode.getErrorTimes() <= MAX_VERIFY_TRY_TIMES && !validCode.getCode().equals(code)) {
                //达到试错最大次数，直接让用户重新发送
                if (validCode.getErrorTimes() == MAX_VERIFY_TRY_TIMES) {
                    resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.valicode_retry"));
                    delete(validCode);
                    //还没达到最大次数，提示您还有几次机会
                } else {
                    validCode.setErrorTimes(validCode.getErrorTimes().intValue() + 1);
                    resultVo.setMsg(String.format(MessageUtils.getMessage("user.form.validator.sms.valicode_remain_times"), MAX_SEND_TRY_TIMES - validCode.getErrorTimes()));
                    save(validCode);
                }
                isFail = true;
                //错误次数不多于两次，再一次出错，提示验证码错误
            } else if (validCode.getErrorTimes() <= MAX_VERIFY_TRY_TIMES && !validCode.getCode().equals(code)) {
                isFail = true;
                validCode.setErrorTimes(validCode.getErrorTimes().intValue() + 1);
                resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.valicode_wrong"));
                save(validCode);
            }
        } else {
            resultVo.setMsg(MessageUtils.getMessage("user.form.validator.sms.valicode_retry"));
            isFail = true;
        }

        if (!isFail) {
            resultVo.setRet(ResultVo.SUCCESS);
        }
        return resultVo;
    }

    /**
     * 删除用过的验证码
     *
     * @param mobile
     */
    public void removeByMobile(String mobile) {
        dao.delete(dao.getValiCodeByMobile(mobile));
    }


}
