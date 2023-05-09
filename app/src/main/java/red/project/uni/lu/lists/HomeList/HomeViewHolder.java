package red.project.uni.lu.lists.HomeList;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import red.project.uni.lu.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    ImageView HomeItemPreview;
    TextView HomeItemTitle, HomeItemDescription, HomeItemRelease, HomeItemRating, HomeItemVoteCount;


    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        HomeItemPreview = itemView.findViewById(R.id.HomeItemPreview);
        HomeItemTitle = itemView.findViewById(R.id.HomeItemTitle);
        HomeItemDescription = itemView.findViewById(R.id.HomeItemDescription);
        HomeItemRelease = itemView.findViewById(R.id.HomeItemRelease);
        HomeItemRating = itemView.findViewById(R.id.HomeItemRating);
        HomeItemVoteCount = itemView.findViewById(R.id.HomeItemVoteCount);
    }
}
