package com.elnino.getparking.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elnino.getparking.R;
import com.elnino.getparking.config.VariableConstants;
import com.elnino.getparking.config.api.Urls;
import com.elnino.getparking.libs.utils.ProgressBarUtils;
import com.elnino.getparking.models.Kickstarter;
import com.elnino.getparking.ui.activities.ProjectDetailWebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by jeetupal on 28/05/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private Context context;
  private List<Kickstarter> kickstarterList;
  private final int VIEW_TYPE_ITEM = 1;
  private final int VIEW_TYPE_PROGRESSBAR = 0;

  public HomeAdapter(Context context, List<Kickstarter> kickstarterList) {
    this.context = context;
    this.kickstarterList = kickstarterList;
  }
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
    View view;
    RecyclerView.ViewHolder holder = null;
    if (viewType == VIEW_TYPE_ITEM) {
      view = layoutInflater.inflate(R.layout.list_item_projects, viewGroup, false);
      holder = new KickViewHolder(view);
    } else {
      view = layoutInflater.inflate(R.layout.layout_progress_circle, viewGroup, false);
      holder = new ProgressViewHolder(view);
    }

    return holder;
  }
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    Kickstarter kickstarter = kickstarterList.get(position);
    if (viewHolder instanceof KickViewHolder){
      KickViewHolder kickViewHolder = (KickViewHolder) viewHolder;
      kickViewHolder.category.setText(kickstarter.getType());
      kickViewHolder.name.setText(kickstarter.getTitle());
      kickViewHolder.blurb.setText(kickstarter.getBlurb());
      kickViewHolder.backersCount.setText(kickstarter.getNumBackers());
      kickViewHolder.percent.setText(kickstarter.getPercentageFunded()+"%");
      kickViewHolder.deadlineCountdown.setText(kickstarter.getEndTime());

      kickViewHolder.percentageFunded.setProgress(ProgressBarUtils.progress(kickstarter.getPercentageFunded()));


    } else if (viewHolder instanceof ProgressViewHolder){
      ((ProgressViewHolder)viewHolder).progressBar.setIndeterminate(true);
    }

  }
  @Override
  public int getItemCount() {
    if (kickstarterList != null)
      return kickstarterList.size();
    return 0;
  }
  @Override
  public int getItemViewType(int position) {
    return kickstarterList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_PROGRESSBAR;
  }

  /**
   * ViewHolders
   */
  public class KickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.photo) ImageView photo;
    @BindView(R.id.project_card_photo) LinearLayout projectCardPhoto;
    @BindView(R.id.category) TextView category;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.blurb) TextView blurb;
    @BindView(R.id.percentage_funded) ProgressBar percentageFunded;
    @BindView(R.id.project_card_info) LinearLayout projectCardInfo;
    @BindView(R.id.successfully_funded_text_view) TextView successfullyFundedTextView;
    @BindView(R.id.funding_unsuccessful_text_view) TextView fundingUnsuccessfulTextView;
    @BindView(R.id.project_state_view_group) LinearLayout projectStateViewGroup;
    @BindView(R.id.backers_count) TextView backersCount;
    @BindView(R.id.percent) TextView percent;
    @BindView(R.id.deadline_countdown) TextView deadlineCountdown;
    @BindView(R.id.deadline_countdown_unit) TextView deadlineCountdownUnit;
    @BindView(R.id.project_card_view) CardView projectCardView;
    @BindView(R.id.project_card_view_group) RelativeLayout projectCardViewGroup;
    @OnClick({R.id.project_card_view})
    public void onClick(View v) {
      Timber.d("item click");
      int postion = getLayoutPosition();
      Timber.d("" + postion);
            Intent intent = new Intent( context, ProjectDetailWebActivity.class);
//            Kickstarter kickstarter = kickstarterList.get(getLayoutPosition());
            String url  = Urls.BASE_URL + kickstarterList.get(getLayoutPosition()).getUrl();
            intent.putExtra(VariableConstants.INTENT_PROJECT_URL, url);
            context.startActivity( intent );
    }
    public KickViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, itemView);
    }
  }

  public class ProgressViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.progressBar) ProgressBar progressBar;
    public ProgressViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, itemView);
    }
  }
}
