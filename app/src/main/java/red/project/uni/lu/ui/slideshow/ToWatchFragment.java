package red.project.uni.lu.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.ToWatchList.ToWatchAdapter;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;
import red.project.uni.lu.lists.ToWatchList.ToWatchItem;
import red.project.uni.lu.databinding.FragmentSlideshowBinding;

public class ToWatchFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private ToWatchDataSource toWatchDataSource;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        RecyclerView toWatchRecyclerView = view.findViewById(R.id.recyclerToWatch);
        toWatchDataSource = new ToWatchDataSource(getContext());
        toWatchDataSource.open();


        // This is only for testing purposes
        ToWatchItem TWI1 = new ToWatchItem("https://www.themoviedb.org/t/p/original/vIeu8WysZrTSFb2uhPViKjX9EcC.jpg","Suzume","Suzume Yosano has lived deep in Japan's countryside all her life until she's forced to move to Tokyo to stay with her uncle. A new city, a new crowd and a new tall, dark haired peculiar stranger that just happens to be her homeroom teacher...","2016-04-06","2021-05-20",916224);
        ToWatchItem TWI2 = new ToWatchItem("https://www.themoviedb.org/t/p/original/vIeu8WysZrTSFb2uhPViKjX9EcC.jpg","Suzume","Suzume Yosano has lived deep in Japan's countryside all her life until she's forced to move to Tokyo to stay with her uncle. A new city, a new crowd and a new tall, dark haired peculiar stranger that just happens to be her homeroom teacher...","2016-04-06","2022-05-20",918737);
        ToWatchItem TWI3 = new ToWatchItem("https://www.themoviedb.org/t/p/original/vIeu8WysZrTSFb2uhPViKjX9EcC.jpg","Suzume","Suzume Yosano has lived deep in Japan's countryside all her life until she's forced to move to Tokyo to stay with her uncle. A new city, a new crowd and a new tall, dark haired peculiar stranger that just happens to be her homeroom teacher...","2016-04-06","2023-05-20",912424);

        ToWatchItem TempTWI;
        TempTWI = toWatchDataSource.createToWatchItem(TWI1);
        if (TempTWI != null) {
            System.out.println("ToWatchItem1 created with id " + TempTWI.getId());
        } else {
            System.out.println("ToWatchItem1 not created");
        }
        TempTWI = toWatchDataSource.createToWatchItem(TWI2);
        if (TempTWI != null) {
            System.out.println("ToWatchItem2 created with id " + TempTWI.getId());
        } else {
            System.out.println("ToWatchItem2 not created");
        }
        TempTWI = toWatchDataSource.createToWatchItem(TWI3);
        if (TempTWI != null) {
            System.out.println("ToWatchItem2 created with id " + TempTWI.getId());
        } else {
            System.out.println("ToWatchItem2 not created");
        }
        List<ToWatchItem> toWatchItems = toWatchDataSource.getAllToWatchItems();




        toWatchRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        toWatchRecyclerView.setAdapter(new ToWatchAdapter(toWatchItems));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}