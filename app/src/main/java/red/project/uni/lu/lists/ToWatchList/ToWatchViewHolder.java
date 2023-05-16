package red.project.uni.lu.lists.ToWatchList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import red.project.uni.lu.R;

public class ToWatchViewHolder extends RecyclerView.ViewHolder {

        ImageView preview;
        TextView title, notes, dateToWatch, dateOfRelease;
        public ToWatchViewHolder(@NonNull View itemView) {

            super(itemView);
            preview = itemView.findViewById(R.id.ToWatchItemPreview);
            title = itemView.findViewById(R.id.ToWatchItemTitle);
            notes = itemView.findViewById(R.id.ToWatchItemNotes);
            dateToWatch = itemView.findViewById(R.id.ToWatchItemDateToWatch);
            dateOfRelease = itemView.findViewById(R.id.ToWatchItemDateOfRelease);
        }
}
