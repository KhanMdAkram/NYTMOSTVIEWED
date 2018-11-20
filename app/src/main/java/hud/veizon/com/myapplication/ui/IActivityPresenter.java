package hud.veizon.com.myapplication.ui;

import java.util.List;

import hud.veizon.com.myapplication.model.Results;

/**
 * Created by akram on 20/11/18.
 */

public interface IActivityPresenter {
    void showProgressBar();
    void dismissProgressBar();
    void setResultData(List<Results> resultsList);
    void onError(Throwable t);
}
