package com.nyt.mostviewed.ui;

import com.nyt.mostviewed.model.Results;

import java.util.List;

/**
 * Created by akram on 20/11/18.
 */

public interface IActivityPresenter {
    void showProgressBar();
    void dismissProgressBar();
    void setResultData(List<Results> resultsList);
    void onError(Throwable t);
}
