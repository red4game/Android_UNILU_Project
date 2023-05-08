package red.project.uni.lu.lists.HomeList;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import red.project.uni.lu.R;

public class HomeProgressViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public HomeProgressViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.HomeLoader);
    }
}
