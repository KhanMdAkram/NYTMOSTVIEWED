package hud.veizon.com.myapplication.network;

import java.util.List;

import hud.veizon.com.myapplication.model.Results;

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
