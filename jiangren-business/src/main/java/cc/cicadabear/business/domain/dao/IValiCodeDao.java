package cc.cicadabear.business.domain.dao;

import cc.cicadabear.business.common.ICrudDao;
import cc.cicadabear.business.domain.entity.user.ValiCode;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Dao接口
 *
 * @author cc
 */
public interface IValiCodeDao extends ICrudDao<ValiCode> {

    /**
     * 根据电话号码获取验证码
     *
     * @param mobile
     * @return
     */
    public ValiCode getValiCodeByMobile(@Param("mobile") String mobile);

}
