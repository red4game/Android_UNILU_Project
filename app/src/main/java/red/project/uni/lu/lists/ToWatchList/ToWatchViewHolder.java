package red.project.uni.lu.lists.ToWatchList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import red.project.uni.lu.R;

public class ToWatchViewHolder extends RecyclerView.ViewHolder {

        ImageView preview;
        TextView title, description, date, genre, director;
        public ToWatchViewHolder(@NonNull View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.toWatchPreview);
            title = itemView.findViewById(R.id.toWatchTitle);
            description = itemView.findViewById(R.id.toWatchDescription);
            date = itemView.findViewById(R.id.toWatchDate);
            genre = itemView.findViewById(R.id.toWatchGenre);
            director = itemView.findViewById(R.id.toWatchDirector);
        }
}
