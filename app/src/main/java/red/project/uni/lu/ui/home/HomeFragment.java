package red.project.uni.lu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Objects;

import red.project.uni.lu.BuildConfig;
import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;
import red.project.uni.lu.lists.HomeList.HomeAdapter;
import red.project.uni.lu.lists.HomeList.LoadListener;
import red.project.uni.lu.ui.no_watched_detailled.NoWatchedDetailledFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    ArrayList<SlideModel> models;
    ArrayList<Integer> moviesIDS;
    ImageSlider imageSlider;
    Button sortTitle;
    Button sortDate;
    Button sortRating;

    SearchView searchBar;

    RecyclerView homeList;
    View view;



    int page = 1;
    boolean isLoading = false;
    boolean isLastPage = false;
    String query = "";
    int numPages = 5;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.NowPlayingSlider);
        sortTitle = view.findViewById(R.id.HomeSortTitle);
        sortDate = view.findViewById(R.id.HomeSortDate);
        sortRating = view.findViewById(R.id.HomeSortRating);
        searchBar = view.findViewById(R.id.HomeSearch);
        homeList = view.findViewById(R.id.HomeRecyclerList);

        models = new ArrayList<>();
        moviesIDS = new ArrayList<>();

        getCinemaMovies();

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        HomeAdapter homeAdapter = new HomeAdapter();
        homeList.setLayoutManager(llm);
        homeList.setAdapter(homeAdapter);

        homeList.addOnScrollListener(new LoadListener(llm) {
            @Override
            protected void loadMore() {
                isLoading = true;
                page++;
                nextPageListMovies();

            }

            @Override
            public boolean isLast() {
                return isLastPage;
            }

            @Override
            public boolean isLoad() {
                return isLoading;
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Make api call to get the movies from the database and return url of the image + title
    private void getCinemaMovies() {

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.TMDB_API_KEY +"&language=fr-FR&page=1&region=FR";
        mRequestQueue = Volley.newRequestQueue(view.getContext());
        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                for (int i = 0; i < jsonObj.getJSONArray("results").length(); i++) {
                    JSONObject movie = jsonObj.getJSONArray("results").getJSONObject(i);
                    int id = movie.getInt("id");
                    String title = movie.getString("title");
                    String date = movie.getString("release_date");
                    String poster_path = movie.getString("backdrop_path");
                    models.add(new SlideModel("https://image.tmdb.org/t/p/w500/" + poster_path, title + " - " + date, ScaleTypes.FIT));

                    // Add new click listener to the image slider



                    moviesIDS.add(id);
                }
                imageSlider.setImageList(models, ScaleTypes.FIT);
                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void doubleClick(int i) {
                        // DO NOTHING
                    }

                    @Override
                    public void onItemSelected(int i) {
                        // open an intent to the movie page by passing the id of the movie
                        Bundle bundle = new Bundle();
                        bundle.putInt("movieID", moviesIDS.get(i));
                        System.out.println(moviesIDS.get(i));
                        Navigation.findNavController(view).navigate(R.id.action_nav_home_to_noWatchedDetailledFragment, bundle);

                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void firstLoadListMovies() {
        // TODO : make the request for making a basic request at start (without search)
        // Maybe do latest movies or something like that
    }

    private void firstQueryListMovies(String query) {
        this.query = query;
        // TODO : make the request when the user search for a movie
    }

    private void nextPageListMovies() {
        page++;
        // TODO : make the request
    }





}