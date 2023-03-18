package com.petra.lib.stability;

public interface BulkheadCallback<T> {

    void executed(T response);
}
