/**
 * Copyright (c) 2005-2011 springside.org.cn
 * <p>
 * $Id: PropertiesLoader.java 1690 2012-02-22 13:42:00Z calvinxiu $
 */
package cn.iutils.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 *
 * @author calvin
 * @version 2013-05-15
 */
public class PropertiesLoader {

    @Autowired
    ApplicationContext ctx;

    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final Properties properties = new Properties();

    private static PropertiesLoader singleton;

    public static PropertiesLoader getSingleton() {
        if (singleton == null) {
            singleton = new PropertiesLoader();
        }
        return singleton;
    }

    private PropertiesLoader() {

    }

//    public PropertiesLoader(String... resourcesPaths) throws IllegalAccessException {
//        this();
//        loadProperties(properties, resourcesPaths);
//    }

//    public PropertiesLoader(Properties... propertiess) {
//        this();
//        for (Properties properties : propertiess) {
//            this.properties.putAll(properties);
//        }
//    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
//        String envProperty = ctx.getEnvironment().getProperty(key);
//        if (envProperty != null) {
//            return envProperty;
//        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }

    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    public void setLocations(Resource... locations) {
        loadPropertiesFromResources(this.properties, locations);
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public void setProperties(List<Properties> propertiess) {
        for (Properties properties : propertiess)
            this.properties.putAll(properties);
    }

    public void setProperties(String... propertiesPaths) {
        loadProperties(this.properties, propertiesPaths);
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     */
    private void loadProperties(Properties props, String... resourcesPaths) {
//        Properties props = new Properties();

        for (String location : resourcesPaths) {

//			logger.debug("Loading properties file from:" + location);
            Resource resource = resourceLoader.getResource(location);
            loadPropertiesFromResources(props, resource);
        }
    }

    private void loadPropertiesFromResources(Properties props, Resource... resources) {
        for (Resource resource : resources) {
            InputStream is = null;
            try {
                props.load(resource.getInputStream());
            } catch (IOException ex) {
                logger.info("Could not load properties from path:" + resource.getFilename() + ", " + ex.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }
}
