package hud.veizon.com.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import hud.veizon.com.myapplication.databinding.ActivityNytMostPopularBinding;
import hud.veizon.com.myapplication.model.MostViewedResponse;
import hud.veizon.com.myapplication.model.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NytMostPopularActivity extends AppCompatActivity {
    ActivityNytMostPopularBinding mBinding;
    private ProgressDialog mProgressDialog;
    private MostViewAdapter mMostViewedAdapter;
    private List<Results> mResultList = new ArrayList<>();
    private final String TAG = "NytMostPopularActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nyt_most_popular);
        setAdapter();
        callNytApi();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViews.setLayoutManager(linearLayoutManager);
        mMostViewedAdapter = new MostViewAdapter(this, mResultList);
        mBinding.recyclerViews.setAdapter(mMostViewedAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        callNytApi();
        return true;
    }

    private void callNytApi() {
        showProgressDialog("Refreshing...");
        Call<MostViewedResponse> responseCall = ApiServiceSingleton.getInstance().getMostView("all-sections", "7");
        responseCall.enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(Call<MostViewedResponse> call, Response<MostViewedResponse> response) {
                dismissProgressDialog();
                if (response.body() != null && response.body().getResults() != null) {
                    mResultList = response.body().getResults();
                    mMostViewedAdapter.setResultsList(mResultList);
                } else {
                    Log.w(TAG, "Error : " + "No Result found");
                }
            }

            @Override
            public void onFailure(Call<MostViewedResponse> call, Throwable t) {
                dismissProgressDialog();
                Log.w(TAG, "Error : " + "No Response");
                t.printStackTrace();
            }
        });
    }

    private void showProgressDialog(String message) {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (!isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
