package hud.veizon.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import hud.veizon.com.myapplication.databinding.ActivityNytMostPopularBinding;
import hud.veizon.com.myapplication.model.MostViewedResponse;
import hud.veizon.com.myapplication.model.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NytMostPopularActivity extends AppCompatActivity {
    ActivityNytMostPopularBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nyt_most_popular);
        mBinding.nyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callNytApi();
            }
        });
    }

    private void callNytApi() {
        Call<MostViewedResponse> responseCall = ApiServiceSingleton.getInstance().getMostView("all-sections", "7");
        responseCall.enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(Call<MostViewedResponse> call, Response<MostViewedResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    List<Results> results = response.body().getResults();
                    Log.w("Akram", "results : " + results.size());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NytMostPopularActivity.this);
                    mBinding.recyclerViews.setLayoutManager(linearLayoutManager);
                    MostViewAdapter adapter = new MostViewAdapter(response.body().getResults(), NytMostPopularActivity.this);
                    mBinding.recyclerViews.setAdapter(adapter);
                } else {
                    Log.w("Akram", "Error : " + "No Result found");
                }
            }

            @Override
            public void onFailure(Call<MostViewedResponse> call, Throwable t) {
                Log.w("Akram", "Error : " + "No Response");
                t.printStackTrace();
            }
        });
    }

}
