package com.example.fivechess;

/**
 * MVP 模式 V 的基础接口
 *
 * Created by 张佳欣 on 2018/7/9.
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

}
