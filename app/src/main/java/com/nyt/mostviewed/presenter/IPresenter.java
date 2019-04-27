package com.nyt.mostviewed.presenter;

import android.arch.lifecycle.MutableLiveData;

import com.nyt.mostviewed.model.Results;

import java.util.List;

/**
 * Created by akram on 20/11/18.
 */

public interface IPresenter {
    void onRefresh();
    void callNytApi();
    MutableLiveData<List<Results>> getApiResponse();
}
