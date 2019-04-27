package com.nyt.mostviewed.presenter;

import android.arch.lifecycle.MutableLiveData;

import com.nyt.mostviewed.model.Results;
import com.nyt.mostviewed.network.INetworkPresenter;
import com.nyt.mostviewed.ui.IActivityPresenter;

import java.util.List;

/**
 * Created by akram on 20/11/18.
 */

public class MainPresenter implements IPresenter/*, INetworkPresenter.OnFinishedListener*/ {
    private IActivityPresenter mActivityPresenter;
    private INetworkPresenter mNetworkPresenter;

    public MainPresenter(IActivityPresenter activityPresenter, INetworkPresenter networkPresenter) {
        mActivityPresenter = activityPresenter;
        mNetworkPresenter = networkPresenter;
    }

    @Override
    public void onRefresh() {
        mActivityPresenter.showProgressBar();
        mNetworkPresenter.callNytMostPopularApi();
    }

    @Override
    public void callNytApi() {
        mActivityPresenter.showProgressBar();
        mNetworkPresenter.callNytMostPopularApi();
    }

    @Override
    public MutableLiveData<List<Results>> getApiResponse() {
        return mNetworkPresenter.getApiResponse();
    }


   /*without using RX JAVA @Override
    public void onSucess(List<Results> resultsList) {
        if (mActivityPresenter != null) {
            mActivityPresenter.dismissProgressBar();
            mActivityPresenter.setResultData(resultsList);
        }

    }

    @Override
    public void onFailure(Throwable t) {
        if (mActivityPresenter != null) {
            mActivityPresenter.dismissProgressBar();
            mActivityPresenter.onError(t);
        }

    } */
}
