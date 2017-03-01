package cn.iutils.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.IOrganizationDao;
import cn.iutils.sys.entity.Organization;

/**
 * 组织机构服务
 * 
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class OrganizationService extends CrudService<IOrganizationDao, Organization> {

}
