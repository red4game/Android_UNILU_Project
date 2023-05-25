package red.project.uni.lu.ui.film_detailled;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;
import red.project.uni.lu.lists.ToWatchList.ToWatchItem;
import red.project.uni.lu.lists.WatchedList.WatchedDataSource;
import red.project.uni.lu.lists.WatchedList.WatchedItem;

public class AddWatchedDialog extends DialogFragment {
    CalendarView calendarWatched;
    EditText notesWatched;
    RatingBar ratingWatched;
    String dateWatched;
    WatchedDataSource watchedDataSource;
    ToWatchDataSource toWatchDataSource;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public static AddWatchedDialog newInstance(int movieId, String title, String posterUrl, String dateOfRelease, String description) {
        AddWatchedDialog f = new AddWatchedDialog();

        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        args.putString("title", title);
        args.putString("posterUrl", posterUrl);
        args.putString("dateOfRelease", dateOfRelease);
        args.putString("description", description);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_watched_dialog, null);
        // initialize date to watch with today's date
        dateWatched = dateFormat.format(System.currentTimeMillis());

        calendarWatched = view.findViewById(R.id.CalendarWatched);
        notesWatched = view.findViewById(R.id.inputNotesWatched);
        ratingWatched = view.findViewById(R.id.ratingWatched);




        watchedDataSource = new WatchedDataSource(getActivity());
        toWatchDataSource = new ToWatchDataSource(getActivity());

        calendarWatched.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                dateWatched = year + "-" + month + "-" + dayOfMonth;
                System.out.println("Date watched : " + dateWatched);
            }
        });

        int movieId = getArguments().getInt("movieId");
        String title = getArguments().getString("title");
        String posterUrl = getArguments().getString("posterUrl");
        String dateOfRelease = getArguments().getString("dateOfRelease");
        String description = getArguments().getString("description");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        builder.setView(view)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        WatchedItem wdi;
                        watchedDataSource.open();

                        if (notesWatched.getText().length() > 0){
                            wdi = watchedDataSource.createWatchedItem(title, posterUrl, notesWatched.getText().toString(),dateOfRelease , dateWatched, movieId,ratingWatched.getRating());
                        } else {
                            wdi = watchedDataSource.createWatchedItem(title, posterUrl, description, dateOfRelease, dateWatched, movieId,ratingWatched.getRating());
                        }
                        watchedDataSource.close();
                        // need to add in database from bundle infos
                        if (wdi == null){
                            Toast.makeText(getActivity(), "Error adding to watchedList", Toast.LENGTH_SHORT).show();
                        } else {
                            toWatchDataSource.open();
                            if (toWatchDataSource.isInToWatchList(movieId)) {
                                toWatchDataSource.deleteToWatchItemByMovieId(movieId);
                                Toast.makeText(view.getContext(), "This movie has been removed from your toWatch list", Toast.LENGTH_SHORT).show();
                            }
                            toWatchDataSource.close();
                            Toast.makeText(getActivity(), "Added to watchedList", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddWatchedDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();


    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int)(getResources().getDisplayMetrics().widthPixels);
        window.setAttributes(params);
    }
}
