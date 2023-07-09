package com.mtfm.core;

import java.io.Serializable;

public interface PropertyEntity<T> extends Serializable {
    T getTarget();

}
