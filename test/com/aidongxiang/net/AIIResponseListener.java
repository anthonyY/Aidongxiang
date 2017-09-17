package com.aidongxiang.net;

public interface AIIResponseListener<T> {

    public T get();

    public void onFailure(String content);

    public void onSuccess(T response);
    public void onServiceError(String error, int status);

    public void onStart();

    public void onFinish();


}