package red.project.uni.lu.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageSlider imageSlider = view.findViewById(R.id.CinemaSlider);
        ArrayList<SlideModel> models = new ArrayList<>();

        models.add(new SlideModel("https://picsum.photos/200/300", ScaleTypes.FIT));
        models.add(new SlideModel("https://picsum.photos/300/300", ScaleTypes.FIT));
        models.add(new SlideModel("https://picsum.photos/300/200", ScaleTypes.FIT));
        models.add(new SlideModel("https://picsum.photos/400/400", ScaleTypes.FIT));
        imageSlider.setImageList(models, ScaleTypes.FIT);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}