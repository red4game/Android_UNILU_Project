package red.project.uni.lu.ui.no_watched_detailled;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import red.project.uni.lu.BuildConfig;
import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;

public class NoWatchedDetailledFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    int movieId;
    ArrayList<SlideModel> recoModels;
    View view;


    ImageView poster;
    TextView title;
    TextView description;
    TextView genres;
    TextView rating;
    Button addWatched;
    Button addToWatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_no_watched_detailled, container, false);


        poster = view.findViewById(R.id.ImageNoWatch);
        title = view.findViewById(R.id.TitleNoWatch);
        description = view.findViewById(R.id.DescriptionNoWatch);
        genres = view.findViewById(R.id.GenresNoWatch);
        rating = view.findViewById(R.id.RatingNoWatch);
        addWatched = view.findViewById(R.id.AddWDNoWatched);
        addToWatch = view.findViewById(R.id.AddTWNoWatch);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        movieId = (int) bundle.get("movieID");
        System.out.println("from detailled : " + movieId);
        recoModels = new ArrayList<>();

        // call to the api and put into all the fields








        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Make api call to get specific movie infos and put them into the fields
    private void getMovieFromId(int id) {

        String url = "https://api.themoviedb.org/3/movie/"; // TODO : make the url
        mRequestQueue = Volley.newRequestQueue(view.getContext());
        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                // TODO : get infos
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

}