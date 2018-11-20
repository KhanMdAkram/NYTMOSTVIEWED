package hud.veizon.com.myapplication.network;

import hud.veizon.com.myapplication.model.MostViewedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akram on 20/11/18.
 */

public class GetNytApi implements INetworkPresenter {

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
    }
}
