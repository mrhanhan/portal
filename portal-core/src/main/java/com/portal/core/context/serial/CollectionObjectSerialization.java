package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * CollectionObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 11:04
 */
@AllArgsConstructor
public class CollectionObjectSerialization extends AbstractObjectSerialization<Collection> {


    @Getter
    @Setter
    private ObjectSerialization<Object> childrenObjectSerialization;

    @Override
    public Collection serial(Param param, Type type) {
        // 判断是否是 List
        Collection collection;
        Class<?> cls = Collection.class;
        // 集合内容类型
        Type componentType = Object.class;
        if (type instanceof Class<?>) {
            cls = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            cls = (Class<?>) parameterizedType.getRawType();
            componentType = parameterizedType.getActualTypeArguments()[0];
        }
        int size = 0;
        Param[] children = param.getChildren();
        if (children != null) {
           size = children.length;
        }
        if (ClassUtil.isAssignable(cls, List.class)) {
            // 是否是ArrayList
            collection = new ArrayList();;
            if (ClassUtil.isAssignable(cls, LinkedList.class)) {
                collection = new LinkedList();
            } else if (ClassUtil.isAssignable(cls, Vector.class)) {
                collection = new Vector();
            }
        }  else if (ClassUtil.isAssignable(cls, Set.class)) {
            // 判断是否是 Set
            // 是否是ArrayList
            collection = new HashSet();
            if (ClassUtil.isAssignable(cls, TreeSet.class)) {
                collection = new TreeSet();
            }
        } else if (ClassUtil.isAssignable(cls, Queue.class)) {
            // 判断是否是 Queue
            collection = new ArrayBlockingQueue(size);
            if (ClassUtil.isAssignable(cls, Deque.class)) {
                collection = new ArrayDeque(size);
            }
        } else {
            collection = new ArrayList();
        }
        // 判断是否是Collection
        if (children != null) {
            for (Param child : children) {
                collection.add(childrenObjectSerialization.serial(child, componentType));
            }
        }
        return collection;
    }

    @Override
    public boolean isSupport(Param param, Type cls) {
        return param.getType() == ParamTypeEnum.ARRAY && !param.isQuote();
    }
}
