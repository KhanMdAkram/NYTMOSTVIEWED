package com.nyt.mostviewed.network;

import com.nyt.mostviewed.model.Results;

import java.util.List;

/**
 * Created by akram on 20/11/18.
 */

public interface INetworkPresenter {
    interface OnFinishedListener {
        void onSucess(List<Results> resultsList);
        void onFailure(Throwable t);
    }
    void callNytMostPopularApi(OnFinishedListener onFinishedListener);
}
