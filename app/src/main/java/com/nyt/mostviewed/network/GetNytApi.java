package com.nyt.mostviewed.network;

import android.arch.lifecycle.MutableLiveData;

import com.nyt.mostviewed.model.MostViewedResponse;
import com.nyt.mostviewed.model.Results;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by akram on 20/11/18.
 */

public class GetNytApi implements INetworkPresenter {

    private MutableLiveData<List<Results>> mResponseLiveData = new MutableLiveData<>();

    /*without using RX JAVA
    @Override
    public void callNytMostPopularApi(final OnFinishedListener onFinishedListener) {
        Call<MostViewedResponse> responseCall = ApiServiceSingleton.getInstance().getMostView("all-sections", "7");
        responseCall.enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(Call<MostViewedResponse> call, Response<MostViewedResponse> response) {
                onFinishedListener.onSucess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MostViewedResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }*/

    @Override
    public MutableLiveData<List<Results>> getApiResponse() {
        return mResponseLiveData;
    }

    @Override
    public void callNytMostPopularApi() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<MostViewedResponse> getObservable() {
        return ApiServiceSingleton.getInstance()
                .getMostView("all-sections", "7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observer<MostViewedResponse> getObserver() {
        return new Observer<MostViewedResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MostViewedResponse mostViewedResponse) {
                if (mostViewedResponse.getResults() != null) {
                    mResponseLiveData.setValue(mostViewedResponse.getResults());
                }
            }

            @Override
            public void onError(Throwable e) {
                mResponseLiveData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };

    }


}
