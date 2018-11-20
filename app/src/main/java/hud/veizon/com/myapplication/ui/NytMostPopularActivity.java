package hud.veizon.com.myapplication.ui;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import hud.veizon.com.myapplication.R;
import hud.veizon.com.myapplication.databinding.ActivityNytMostPopularBinding;
import hud.veizon.com.myapplication.model.Results;
import hud.veizon.com.myapplication.network.GetNytApi;
import hud.veizon.com.myapplication.presenter.MainPresenter;

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
        mPresenter.callNytApi();
        setAdapter();
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
        mPresenter.onRefresh();
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
