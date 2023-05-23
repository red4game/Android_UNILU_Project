package red.project.uni.lu.ui.film_detailled;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;
import red.project.uni.lu.lists.ToWatchList.ToWatchItem;

public class AddToWatchDialog extends DialogFragment {
    CalendarView calendarToWatch;
    TextView notesToWatch;
    String dateToWatch;
    ToWatchDataSource toWatchDataSource;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static AddToWatchDialog newInstance(int movieId, String title, String posterUrl, String dateOfRelease, String description) {
        AddToWatchDialog f = new AddToWatchDialog();

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_towatch_dialog, null);
        // initialize date to watch with today's date
        dateToWatch = dateFormat.format(System.currentTimeMillis());

        calendarToWatch = view.findViewById(R.id.CalendarToWatch);
        notesToWatch = view.findViewById(R.id.inputNotesToWatch);


        toWatchDataSource = new ToWatchDataSource(getActivity());

        calendarToWatch.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                dateToWatch = year + "-" + month + "-" + dayOfMonth;
            }
        });

        int movieId = getArguments().getInt("movieId");
        String title = getArguments().getString("title");
        String posterUrl = getArguments().getString("posterUrl");
        String dateOfRelease = getArguments().getString("dateOfRelease");
        String description = getArguments().getString("description");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.add_towatch_dialog, null))

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("Added movieId : " + movieId + " to toWatchList");
                        System.out.println("Date to watch : " + dateToWatch);
                        System.out.println("Notes : " + notesToWatch.getText().toString());
                        System.out.println("Title : " + title);
                        System.out.println("PosterUrl : " + posterUrl);
                        System.out.println("Date of release : " + dateOfRelease);
                        System.out.println("Description : " + description);

                        ToWatchItem twi;
                        toWatchDataSource.open();
                        if (notesToWatch.getText().length() > 0){
                            twi = toWatchDataSource.createToWatchItem(title, posterUrl, notesToWatch.getText().toString(),dateOfRelease , dateToWatch, movieId);
                        } else {
                            twi = toWatchDataSource.createToWatchItem(title, posterUrl, description, dateOfRelease, dateToWatch, movieId);
                        }
                        toWatchDataSource.close();
                        // need to add in database from bundle infos
                        if (twi == null){
                            Toast.makeText(getActivity(), "Error adding to toWatchList", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Added to toWatchList", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddToWatchDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();


    }

}
