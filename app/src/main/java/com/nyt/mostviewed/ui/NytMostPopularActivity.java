package com.nyt.mostviewed.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nyt.mostviewed.R;
import com.nyt.mostviewed.databinding.ActivityNytMostPopularBinding;
import com.nyt.mostviewed.model.Results;
import com.nyt.mostviewed.network.GetNytApi;
import com.nyt.mostviewed.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class NytMostPopularActivity extends AppCompatActivity implements IActivityPresenter {
    ActivityNytMostPopularBinding mBinding;
    private MainPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private MostViewAdapter mMostViewedAdapter;
    private List<Results> mResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nyt_most_popular);
        mPresenter = new MainPresenter(this, new GetNytApi());
        if (isNetworkAvailable()) {
            mPresenter.callNytApi();
            observeApiResponse();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
        setAdapter();

    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViews.setLayoutManager(linearLayoutManager);
        mMostViewedAdapter = new MostViewAdapter(this, mResultList);
        mBinding.recyclerViews.setAdapter(mMostViewedAdapter);
    }

    private void observeApiResponse() {
        mPresenter.getApiResponse().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> results) {
                dismissProgressBar();
                if (results != null) {
                    setResultData(results);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isNetworkAvailable()) {
            mPresenter.onRefresh();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void showProgressBar() {
        showProgressDialog("Refreshing");
    }

    @Override
    public void dismissProgressBar() {
        dismissProgressDialog();
    }

    @Override
    public void setResultData(List<Results> resultsList) {
        mMostViewedAdapter.setResultsList(resultsList);
    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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
