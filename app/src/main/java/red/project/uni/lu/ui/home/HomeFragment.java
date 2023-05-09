package red.project.uni.lu.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import red.project.uni.lu.BuildConfig;
import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;
import red.project.uni.lu.lists.HomeList.HomeAdapter;
import red.project.uni.lu.lists.HomeList.HomeItem;
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
    HomeAdapter homeAdapter;
    View view;
    LinearLayoutManager llm;



    int page;
    boolean isLoading = false;
    boolean isLastPage = false;
    boolean isSearch = false;
    String query = "";
    String sortOrder;
    int max_pages = 5;

    int sortStateTitle = 0;
    int sortStateDate = 0;
    int sortStateRating = 0;




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


        llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        homeAdapter = new HomeAdapter();
        // Because i fill my list asynchronously, i need to prevent the recycler view to restore its state
        homeAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        homeList.setLayoutManager(llm);
        homeList.setAdapter(homeAdapter);

        homeList.setOnTouchListener((v, event) -> {
            v.findViewById(R.id.HomeItemDescription).getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        });




        homeList.addOnScrollListener(new LoadListener(llm) {
            @Override
            protected void loadMore() {
                isLoading = true;
                page++;
                if (isSearch) {
                    nextPageQueryListMovies();
                } else {
                    nextFirstLoadListMovies();
                }

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

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length()>0) {
                    homeList.scrollToPosition(0);
                    firstQueryListMovies(s);
                } else {
                    homeList.scrollToPosition(0);
                    firstLoadListMovies();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0){
                    homeList.scrollToPosition(0);
                    firstLoadListMovies();
                }
                return false;
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null){
            query = bundle.getString("query");
        }
        if (query.length() == 0) {
            firstLoadListMovies();
        } else {
            firstQueryListMovies(query);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        Bundle bundle = new Bundle();
        bundle.putString("query",query);
        this.setArguments(bundle);

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
        isSearch = false;
        isLastPage = false;
        if (sortStateTitle != 0) {
            if (sortStateTitle == 1){
                sortOrder = "original_title.asc";
            } else {
                sortOrder = "original_title.desc";
            }
        } else {
            if (sortStateDate != 0) {
                if (sortStateDate == 1) {
                    sortOrder = "release_date.asc";
                } else {
                    sortOrder = "release_date.desc";
                }
            } else {
                if (sortStateRating != 0) {
                    if (sortStateRating == 1) {
                        sortOrder = "vote_average.asc";
                    } else {
                        sortOrder = "vote_average.desc";
                    }
                } else {
                    sortOrder = "popularity.desc";
                }
            }
        }

        page = 1;
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR&region=FR&sort_by="+sortOrder+"&page=1";


        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                List<HomeItem> movies = new ArrayList<>();
                for (int i = 0; i < jsonObj.getJSONArray("results").length(); i++) {
                    JSONObject movie = jsonObj.getJSONArray("results").getJSONObject(i);
                    int id = movie.getInt("id");
                    String title = movie.getString("title");
                    String description = movie.getString("overview");
                    String date = movie.getString("release_date");
                    String rating = movie.getString("vote_average");
                    String poster_path = movie.getString("poster_path");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating);
                    homeItem.setId(id);
                    movies.add(homeItem);
                }
                homeAdapter.replace(movies);

                if (jsonObj.getInt("total_pages") == page) {
                    isLastPage = true;
                } else {
                    homeAdapter.addLoading();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void nextFirstLoadListMovies() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR&region=FR&sort_by="+sortOrder+"&page="+page;



        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                List<HomeItem> movies = new ArrayList<>();
                for (int i = 0; i < jsonObj.getJSONArray("results").length(); i++) {
                    JSONObject movie = jsonObj.getJSONArray("results").getJSONObject(i);
                    int id = movie.getInt("id");
                    String title = movie.getString("title");
                    String description = movie.getString("overview");
                    String date = movie.getString("release_date");
                    String rating = movie.getString("vote_average");
                    String poster_path = movie.getString("poster_path");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating);
                    homeItem.setId(id);
                    movies.add(homeItem);
                }
                homeAdapter.removeLoading();
                isLoading = false;
                homeAdapter.addAll(movies);

                if (jsonObj.getInt("total_pages") == page) {
                    isLastPage = true;
                } else {
                    homeAdapter.addLoading();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void firstQueryListMovies(String query) {
        this.query = query;
        page=1;
        isSearch = true;
        isLastPage = false;
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR&query="+query+"&page=1";

        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                List<HomeItem> movies = new ArrayList<>();
                for (int i = 0; i < jsonObj.getJSONArray("results").length(); i++) {
                    JSONObject movie = jsonObj.getJSONArray("results").getJSONObject(i);
                    int id = movie.getInt("id");
                    String title = movie.getString("title");
                    String description = movie.getString("overview");
                    String date = movie.getString("release_date");
                    String rating = movie.getString("vote_average");
                    String poster_path = movie.getString("poster_path");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating);
                    homeItem.setId(id);
                    movies.add(homeItem);
                }
                homeAdapter.replace(movies);
                System.out.println("Number of pages :" + jsonObj.getInt("total_pages"));
                if (jsonObj.getInt("total_pages") == page) {
                    isLastPage = true;
                } else {
                    homeAdapter.addLoading();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void nextPageQueryListMovies() {
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR&query="+query+"&page="+page;


        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);
                List<HomeItem> movies = new ArrayList<>();
                for (int i = 0; i < jsonObj.getJSONArray("results").length(); i++) {
                    JSONObject movie = jsonObj.getJSONArray("results").getJSONObject(i);
                    int id = movie.getInt("id");
                    String title = movie.getString("title");
                    String description = movie.getString("overview");
                    String date = movie.getString("release_date");
                    String rating = movie.getString("vote_average");
                    String poster_path = movie.getString("poster_path");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating);
                    homeItem.setId(id);
                    movies.add(homeItem);
                }
                homeAdapter.removeLoading();
                isLoading = false;
                homeAdapter.addAll(movies);

                if (jsonObj.getInt("total_pages") == page) {
                    isLastPage = true;
                } else {
                    homeAdapter.addLoading();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }





}