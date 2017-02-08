package cn.iutils.sys.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 公共配置表
* @author iutils.cn
* @version 1.0
*/
public class Config extends DataEntity<Config>{

    private static final long serialVersionUID = 1L;

    private String sysName;//系统名称
    private String moduleName;//模块名称
    private String configName;//配置key
    private String configValue;//配置值

    public Config() {
        super();
    }
    public Config(String id){
        super(id);
    }

    public String getSysName(){
        return sysName;
    }

    public void setSysName(String sysName){
        this.sysName = sysName;
    }

    public String getModuleName(){
        return moduleName;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }

    public String getConfigName(){
        return configName;
    }

    public void setConfigName(String configName){
        this.configName = configName;
    }

    public String getConfigValue(){
        return configValue;
    }

    public void setConfigValue(String configValue){
        this.configValue = configValue;
    }

}
