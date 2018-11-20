package hud.veizon.com.myapplication.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import hud.veizon.com.myapplication.R;
import hud.veizon.com.myapplication.databinding.MostViewedItemBinding;
import hud.veizon.com.myapplication.model.Media;
import hud.veizon.com.myapplication.model.MultiMedia;
import hud.veizon.com.myapplication.model.Results;

/**
 * Created by akram on 20/11/18.
 */

public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.MostViewHolder> {
    private List<Results> mResultsList;
    private Context mContext;
    private final int VIEW_TYPE_NO_RESULT = 1;
    private final int VIEW_TYPE_RESULT = 2;

    MostViewAdapter(Context context, List<Results> resultsList) {
        mContext = context;
        mResultsList = resultsList;
    }

    public void setResultsList(List<Results> resultsList) {
        mResultsList = resultsList;
        notifyDataSetChanged();
    }

    @Override
    public MostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MostViewedItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.most_viewed_item, parent, false);
        return new MostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MostViewHolder holder, int position) {
        holder.bind(mResultsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResultsList.size();
    }

    class MostViewHolder extends RecyclerView.ViewHolder {
        private MostViewedItemBinding mMostViewedItemBinding;

        MostViewHolder(MostViewedItemBinding binding) {
            super(binding.getRoot());
            mMostViewedItemBinding = binding;

        }

        void bind(Results results) {
            if (!TextUtils.isEmpty(results.getTitle())) {
                mMostViewedItemBinding.title.setText(results.getTitle());
            }

            if (!TextUtils.isEmpty(results.getByline())) {
                mMostViewedItemBinding.desc.setText(results.getByline());
            }

            if (!TextUtils.isEmpty(results.getPublished_date())) {
                mMostViewedItemBinding.date.setText(results.getPublished_date());
            }

            Media media = results.getMedia().get(0);
            String thumbnailUrl = "";
            if (media.getMultiMedia() != null) {
                for (MultiMedia multiMedia : media.getMultiMedia()) {
                    if (multiMedia.getFormat().equals("Standard Thumbnail")) {
                        thumbnailUrl = multiMedia.getUrl();
                        break;
                    }
                }

                try {
                    mMostViewedItemBinding.thumbnail.setBackground(null);
                    Picasso.with(mContext).load(thumbnailUrl).error(R.drawable.rp_store_no_image).into(mMostViewedItemBinding.thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mMostViewedItemBinding.thumbnail.setVisibility(View.GONE);
            }
        }
    }
}
