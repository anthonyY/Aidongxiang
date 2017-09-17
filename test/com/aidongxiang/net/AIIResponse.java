package com.aidongxiang.net;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AIIResponse<T> implements AIIResponseListener<T> {

    private Class<T> entityClass;

    private T t;

    public AIIResponse() {
        init();
    }


    private void init() {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (params != null && params.length > 0) {
                entityClass = (Class<T>) params[0];
                try {
                    t = entityClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public T get() {
        if (t == null) {
            try {
                return entityClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    @Override
    public void onFailure(String content) {

    }

    @Override
    public void onSuccess(T response) {

    }

    @Override
    public void onServiceError(String error, int status) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

}