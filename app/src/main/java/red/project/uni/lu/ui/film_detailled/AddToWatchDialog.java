package red.project.uni.lu.ui.film_detailled;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;

public class AddToWatchDialog extends DialogFragment {
    CalendarView calendarToWatch;
    String dateToWatch;
    ToWatchDataSource toWatchDataSource;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static AddToWatchDialog newInstance(int num) {
        AddToWatchDialog f = new AddToWatchDialog();

        Bundle args = new Bundle();
        args.putInt("movieId", num);
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
        toWatchDataSource = new ToWatchDataSource(getActivity());

        calendarToWatch.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                dateToWatch = year + "-" + month + "-" + dayOfMonth;
            }
        });

        int movieId = getArguments().getInt("movieId");
        System.out.println("from dialog" + movieId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.add_towatch_dialog, null))

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("Added movieId : " + movieId + " to toWatchList");
                        // need to add in database from bundle infos
                        Toast.makeText(getActivity(), "Added to toWatchList", Toast.LENGTH_SHORT).show();
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
