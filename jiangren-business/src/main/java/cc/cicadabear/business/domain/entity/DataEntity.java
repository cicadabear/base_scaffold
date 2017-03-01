package cc.cicadabear.business.domain.entity;


import cc.cicadabear.business.common.BaseEntity;

import java.util.Date;

/**
 * 数据实体
 * Created by CC on 16/9/24.
 */
public abstract class DataEntity<T> extends BaseEntity<T> {


    private static final long serialVersionUID = 1536332605400158440L;

    @Override
    public void preInsert() {
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    @Override
    public void preUpdate() {
        this.updateDate = new Date();
    }
}

