package com.example.foobook_android;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SampleViewModel extends ViewModel {
    private MutableLiveData<String> foo;

    public MutableLiveData<String> getFoo() {
        if (foo == null) {
            foo = new MutableLiveData<String>();
        }
        return foo;
    }
}
