package cn.iutils.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 反射工具
 * @author iutils.cn
 */
public class ReflectUtil {

    /**
     * 调用Getter方法.
     */
    public static Object invokeGetterMethod(Object target, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用Getter方法.
     */
    public static Object invokeGetterMethodWithCascade(Object target, String propertyName) {
        if (propertyName==null || propertyName.indexOf(".") == -1) {
            return invokeGetterMethod(target, propertyName);
        }
        String[] properties = propertyName.split("[.]");
        Object objTaget = target;
        for (String property : properties) {
            objTaget = invokeGetterMethod(objTaget, property);
        }
        return objTaget;
    }

    /**
     * 调用Setter方法
     */
    public static <T> void invokeSetter(Object target, String fieldName, Object args)
            throws NoSuchFieldException, SecurityException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // 如果属性名为xxx，则方法名为setXxx
        String methodName = "set" + JStringUtils.firstCharUpperCase(fieldName);
        Class<?> clazz = target.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        Method method = clazz.getMethod(methodName, field.getType());
        method.invoke(target, args);
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     */
    public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }

        method.setAccessible(true);

        try {
            return method.invoke(object, parameters);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredMethod.
     *
     * 如向上转型到Object仍无法找到, 返回null.
     */
    protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        Assert.notNull(object, "object不能为空");

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 得到所有的属性
     *
     * @param entityClass
     * @return
     */
    public static ArrayList<Field> getAllFields(Class<?> entityClass) {
        ArrayList<Field> fs = new ArrayList<Field>();
        // 得到model所有属性
        Class<?> class1 = entityClass;
        while (!class1.getSimpleName().equals("Object")) {
            fs.addAll(Arrays.asList(class1.getDeclaredFields()));
            class1 = class1.getSuperclass();
        }
        return fs;
    }

    /**
     * 得到对象的属性值
     *
     * @param model
     * @param name
     * @return
     */
    public static String getSimpleProperty(Object model, String name) {
        try {
            return BeanUtils.getSimpleProperty(model, name);
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
    }


    /**
     * 得到对象属性的值，使用getSimpleProperty，防止出现hibernate延迟加载读到null
     *
     * @param obj
     * @param prepertyName
     * @return
     */
    public static Object getFieldValue(Object obj, String prepertyName) {
        return getSimpleProperty(obj, prepertyName);
    }

    /**
     * 得到对象属性的值（父类属性值可以取到）
     */
    public static Object getFieldValueWithSuper(Object obj, String prepertyName) {
        try {
            Field field = getClassFieldWithSuper(obj.getClass(), prepertyName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(obj);
            }
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
        return null;
    }

    /**
     * 得到Class的属性（包括父类属性）
     */
    public static Field getClassFieldWithSuper(Class<?> cls, String fieldName) {
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && !superclass.getSimpleName().equals("Object")) {
            // 递归
            return getClassFieldWithSuper(superclass, fieldName);
        }
        return null;
    }

    public static boolean isExistsClassField(Class<?> cls, String fieldName){
        return getClassFieldWithSuper(cls, fieldName)!=null;
    }

    /**
     * 得到对象属性的值
     *
     * @param model
     * @param prepertyName
     */
    public static void setFieldValue(Object model, String prepertyName, String value) {
        Class<?>[] plusPara = { String.class };
        Object[] transPlusPara = { value };
        if(!isExistsClassField(model.getClass(),prepertyName)){
            return;
        }
        try {
            model.getClass().getMethod("set" + prepertyName.substring(0, 1).toUpperCase() + prepertyName.substring(1), plusPara).invoke(model, transPlusPara);
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 给某个对象的属性设置
     * @author wuyw
     * @date 2015-8-31 上午12:50:31
     * @param model
     * @param propertyClass
     * @param propertyName
     * @param propertyValue
     */
    public static void setFieldValue(Object model, Class<?> propertyClass,  String propertyName, Object propertyValue) {
        Class<?>[] plusPara = { propertyClass };
        Object[] transPlusPara = { propertyValue };
        try {
            model.getClass().getMethod("set" + StringUtils.capitalize(propertyName), plusPara).invoke(model, transPlusPara);
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 得到对象属性的值
     *
     * @param model
     * @param prepertyName
     */
    public static void setFieldCharValue(Object model, String prepertyName, Character value) {
        Class<?>[] plusPara = { Character.class };
        Object[] transPlusPara = { value };
        try {
            model.getClass().getMethod("set" + prepertyName.substring(0, 1).toUpperCase() + prepertyName.substring(1), plusPara).invoke(model, transPlusPara);
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 得到类属性的类型
     *
     * @param class1
     * @param propertyName
     * @return
     */
    public static Class<?> getFieldClass(Class<?> class1, String propertyName) {
        try {
            Field field = class1.getDeclaredField(propertyName);
            return field.getClass();
        } catch (Exception e) {
            throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 通过反射,获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao extends HibernateDao<user>
     *
     * @param clazz
     *            The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * 如public UserDao extends HibernateDao<user,Long>
     *
     * @param clazz
     *            clazz The class to introspect
     * @param index
     *            the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 转换字符串到相应类型.
     *
     * @param value
     *            待转换的字符串
     * @param toType
     *            转换目标类型
     */
    public static Object convertStringToObject(String value, Class<?> toType) {
        try {
            return ConvertUtils.convert(value, toType);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

}
