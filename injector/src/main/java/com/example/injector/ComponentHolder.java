package com.example.injector;

/**
 * Holder components feature modules.
 * */

public interface ComponentHolder {
    void init(BaseDependencies dependencies);
    BaseApi get();
    void reset();
}

