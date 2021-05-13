package com.bruceewu.configor.entity;

public interface IRefresher {

    void setOnRefreshListener(Runnable listener);

    void setRefreshing(boolean refreshing);
}
