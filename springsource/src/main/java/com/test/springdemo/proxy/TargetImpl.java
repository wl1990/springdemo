package com.test.springdemo.proxy;

import com.test.springdemo.proxy.Target;

public class TargetImpl implements Target {
    @Override
    public int test(int i) {
        return 0;
    }
}
