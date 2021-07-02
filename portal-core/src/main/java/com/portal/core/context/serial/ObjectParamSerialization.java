package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.ClassUtil;
import com.portal.core.utils.EncryptUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * ObjectParamSerialization
 * 对象序列化
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
@AllArgsConstructor
public class ObjectParamSerialization extends AbstractParamSerialization<Object> {
    @Getter @Setter
    private ParamSerialization<Object> childrenParamSerialization;

    @Override
    public boolean isSupport(Object o) {
        return checkQuote(o);
    }

    @SneakyThrows
    @Override
    public Param serial(Object data) {
        Param param = createParam(ParamTypeEnum.OBJECT);
        // 序列化参数
        Class<?> aClass = data.getClass();
        List<Field> fieldList = ClassUtil.getAllFiled(aClass);
        // 序列化所有参数
        Param[] params = new Param[fieldList.size()];
        int i = 0;
        for (Field field : fieldList) {
            field.setAccessible(true);
            Object o = field.get(data);
            Param child = childrenParamSerialization.serial(o);
            child.setFiledName(generatorFiledName(field));
            params[i++] = child;
        }
        param.setChildren(params);
        return param;
    }

    /**
     * 生成字段名称
     * @param field 生成字段名称
     * @return  返回字段名称
     */
    public static String generatorFiledName(Field field) {
        return EncryptUtils.md5(field.getDeclaringClass().getTypeName() + field.getName());
    }

    @Override
    public int getDeep() {
        // 优先级最低
        return 0;
    }

    protected boolean checkQuote(Object o) {
        return o != null && o instanceof Serializable;
    }
}
