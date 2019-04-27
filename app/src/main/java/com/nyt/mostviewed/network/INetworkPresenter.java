package com.nyt.mostviewed.network;

import android.arch.lifecycle.MutableLiveData;

import com.nyt.mostviewed.model.Results;

import java.util.List;

/**
 * Created by akram on 20/11/18.
 */

public interface INetworkPresenter {

    MutableLiveData<List<Results>> getApiResponse();
    void callNytMostPopularApi();

    /*without using RX JAVA
    interface OnFinishedListener {
        void onSucess(List<Results> resultsList);
        void onFailure(Throwable t);
    }
    void callNytMostPopularApi(OnFinishedListener onFinishedListener); */
}
