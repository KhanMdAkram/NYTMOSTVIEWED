package hud.veizon.com.myapplication.presenter;

import java.util.List;

import hud.veizon.com.myapplication.model.Results;
import hud.veizon.com.myapplication.network.INetworkPresenter;
import hud.veizon.com.myapplication.ui.IActivityPresenter;

/**
 * Created by akram on 20/11/18.
 */

public class MainPresenter implements IPresenter, INetworkPresenter.OnFinishedListener {
    private IActivityPresenter mActivityPresenter;
    private INetworkPresenter mNetworkPresenter;

    public MainPresenter(IActivityPresenter activityPresenter, INetworkPresenter networkPresenter) {
        mActivityPresenter = activityPresenter;
        mNetworkPresenter = networkPresenter;
    }

    @Override
    public void onRefresh() {
        mActivityPresenter.showProgressBar();
        mNetworkPresenter.callNytMostPopularApi(this);
    }

    @Override
    public void callNytApi() {
        mActivityPresenter.showProgressBar();
        mNetworkPresenter.callNytMostPopularApi(this);
    }


    @Override
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

    }
}
