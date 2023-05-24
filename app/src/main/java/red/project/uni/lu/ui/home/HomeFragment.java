package red.project.uni.lu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import red.project.uni.lu.BuildConfig;
import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;
import red.project.uni.lu.lists.HomeList.HomeAdapter;
import red.project.uni.lu.lists.HomeList.HomeItem;
import red.project.uni.lu.lists.HomeList.LoadListener;

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
    Button sortVote;

    TextView noItemText;

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
    int sortStateVote = 0;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing in order that it don't crash
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.NowPlayingSlider);

        sortTitle = view.findViewById(R.id.HomeSortTitle);
        sortDate = view.findViewById(R.id.HomeSortDate);
        sortRating = view.findViewById(R.id.HomeSortRating);
        sortVote = view.findViewById(R.id.HomeSortVote);
        noItemText = view.findViewById(R.id.NoItemText);

        resetSortButtons();


        sortRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortStateTitle != 0){
                    CharSequence newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "-";
                    sortTitle.setText(newtextSortTitle);
                    sortStateTitle = 0;
                }
                if (sortStateDate != 0){
                    CharSequence newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "-";
                    sortDate.setText(newtextSortDate);
                    sortStateDate = 0;
                }
                if (sortStateVote != 0){
                    CharSequence newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "-";
                    sortVote.setText(newtextSortVote);
                    sortStateVote = 0;
                }
                CharSequence newtextSortRating;
                switch (sortStateRating) {
                    case 0:
                        newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "↑";
                        sortRating.setText(newtextSortRating);
                        sortStateRating = 1;
                        if (query.length()>0) {
                            homeAdapter.sortByRatingAsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 1:
                        newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "↓";
                        sortRating.setText(newtextSortRating);
                        sortStateRating = 2;
                        if (query.length()>0) {
                             homeAdapter.sortByRatingDsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 2:
                        newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "-";
                        sortRating.setText(newtextSortRating);
                        sortStateRating = 0;
                        if (query.length()>0) {
                            // DO NOTHING FOR NOW
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                }
                homeList.scrollToPosition(0);
            }
        });

        sortTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortStateDate != 0){
                    CharSequence newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "-";
                    sortDate.setText(newtextSortDate);
                    sortStateDate = 0;
                }
                if (sortStateRating != 0){
                    CharSequence newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "-";
                    sortRating.setText(newtextSortRating);
                    sortStateRating = 0;
                }
                if (sortStateVote != 0){
                    CharSequence newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "-";
                    sortVote.setText(newtextSortVote);
                    sortStateVote = 0;
                }
                CharSequence newtextSortTitle;
                switch (sortStateTitle) {
                    case 0:
                        newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "↑";
                        sortTitle.setText(newtextSortTitle);
                        sortStateTitle = 1;
                        if (query.length()>0) {
                            homeAdapter.sortByTitleAsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 1:
                        newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "↓";
                        sortTitle.setText(newtextSortTitle);
                        sortStateTitle = 2;
                        if (query.length()>0) {
                            homeAdapter.sortByTitleDsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 2:
                        newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "-";
                        sortTitle.setText(newtextSortTitle);
                        sortStateTitle = 0;
                        if (query.length()>0) {
                            // DO NOTHING FOR NOW
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                }
                homeList.scrollToPosition(0);

            }
        });

        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortStateTitle != 0){
                    CharSequence newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "-";
                    sortTitle.setText(newtextSortTitle);
                    sortStateTitle = 0;
                }
                if (sortStateVote != 0){
                    CharSequence newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "-";
                    sortVote.setText(newtextSortVote);
                    sortStateVote = 0;
                }
                if (sortStateRating != 0){
                    CharSequence newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "-";
                    sortRating.setText(newtextSortRating);
                    sortStateRating = 0;
                }
                CharSequence newtextSortDate;
                switch (sortStateDate) {
                    case 0:
                        newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "↑";
                        sortDate.setText(newtextSortDate);
                        sortStateDate = 1;
                        if (query.length()>0) {
                            homeAdapter.sortByDateAsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 1:
                        newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "↓";
                        sortDate.setText(newtextSortDate);
                        sortStateDate = 2;
                        if (query.length()>0) {
                            homeAdapter.sortByDateDsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 2:
                        newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "-";
                        sortDate.setText(newtextSortDate);
                        sortStateDate = 0;
                        if (query.length()>0) {
                            // DO NOTHING FOR NOW
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                }
                homeList.scrollToPosition(0);
            }
        });

        sortVote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (sortStateTitle != 0){
                    CharSequence newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "-";
                    sortTitle.setText(newtextSortTitle);
                    sortStateTitle = 0;
                }
                if (sortStateDate != 0){
                    CharSequence newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "-";
                    sortDate.setText(newtextSortDate);
                    sortStateDate = 0;
                }
                if (sortStateRating != 0){
                    CharSequence newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "-";
                    sortRating.setText(newtextSortRating);
                    sortStateRating = 0;
                }
                CharSequence newtextSortVote;
                switch (sortStateVote) {
                    case 0:
                        newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "↑";
                        sortVote.setText(newtextSortVote);
                        sortStateVote = 1;
                        if (query.length()>0) {
                            homeAdapter.sortByVoteAsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 1:
                        newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "↓";
                        sortVote.setText(newtextSortVote);
                        sortStateVote = 2;
                        if (query.length()>0) {
                            homeAdapter.sortByVoteDsc();
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                    case 2:
                        newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "-";
                        sortVote.setText(newtextSortVote);
                        sortStateVote = 0;
                        if (query.length()>0) {
                            // DO NOTHING FOR NOW
                        } else {
                            firstLoadListMovies();
                        }
                        break;
                }
                homeList.scrollToPosition(0);
            }
        });


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
                    sortStateDate = 0;
                    sortStateRating = 0;
                    sortStateTitle = 0;
                    resetSortButtons();
                    firstQueryListMovies(s);
                    homeList.scrollToPosition(0);
                } else {
                    sortStateDate = 0;
                    sortStateRating = 0;
                    sortStateTitle = 0;
                    resetSortButtons();
                    firstLoadListMovies();
                    homeList.scrollToPosition(0);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0){
                    sortStateDate = 0;
                    sortStateRating = 0;
                    sortStateTitle = 0;
                    resetSortButtons();
                    firstLoadListMovies();
                    homeList.scrollToPosition(0);
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
                        Navigation.findNavController(view).navigate(R.id.action_nav_home_to_FilmDetailledFragment, bundle);

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
        query = "";
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
                    if (sortStateVote != 0) {
                        if (sortStateVote == 1) {
                            sortOrder = "vote_count.asc";
                        } else {
                            sortOrder = "vote_count.desc";
                        }
                    } else {
                        sortOrder = "popularity.desc";
                    }
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
                    String vote_count = movie.getString("vote_count");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating,vote_count);
                    homeItem.setId(id);
                    movies.add(homeItem);

                }
                isLoading = false;
                if (movies.size() == 0) {
                    homeAdapter.clear();
                    homeList.setVisibility(View.GONE);
                    noItemText.setVisibility(View.VISIBLE);

                } else {
                    homeList.setVisibility(View.VISIBLE);
                    noItemText.setVisibility(View.GONE);

                    homeAdapter.replace(movies);

                    if (jsonObj.getInt("total_pages") == page) {
                        isLastPage = true;
                        homeAdapter.setLoadingActiveFalse();
                    } else {
                        homeAdapter.addLoading();
                    }
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
                    String vote_count = movie.getString("vote_count");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating,vote_count);
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
                    String vote_count = movie.getString("vote_count");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating,vote_count);
                    homeItem.setId(id);
                    movies.add(homeItem);

                }

                isLoading = false;
                if (movies.size() == 0) {
                    homeAdapter.clear();
                    homeList.setVisibility(View.GONE);
                    noItemText.setVisibility(View.VISIBLE);

                } else {
                    homeList.setVisibility(View.VISIBLE);
                    noItemText.setVisibility(View.GONE);

                    homeAdapter.replace(movies);
                    System.out.println("Number of pages :" + jsonObj.getInt("total_pages"));
                    System.out.println("Current page :" + page);

                    if (jsonObj.getInt("total_pages") == page) {
                        isLastPage = true;
                        homeAdapter.setLoadingActiveFalse();
                    } else {

                        homeAdapter.addLoading();
                    }
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
                    String vote_count = movie.getString("vote_count");

                    HomeItem homeItem = new HomeItem("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + poster_path, title, description, date, rating,vote_count);
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


    public void resetSortButtons(){
        // set the text for buttons when restoring state it is useful
        CharSequence newtextSortTitle;
        newtextSortTitle =  sortTitle.getText().subSequence(0,sortTitle.getText().length()-1) + "-";
        sortTitle.setText(newtextSortTitle);
        sortStateTitle = 0;

        CharSequence newtextSortDate;
        newtextSortDate =  sortDate.getText().subSequence(0,sortDate.getText().length()-1) + "-";
        sortDate.setText(newtextSortDate);
        sortStateDate = 0;

        CharSequence newtextSortVote;
        newtextSortVote =  sortVote.getText().subSequence(0,sortVote.getText().length()-1) + "-";
        sortVote.setText(newtextSortVote);
        sortStateVote = 0;


        CharSequence newtextSortRating;
        newtextSortRating =  sortRating.getText().subSequence(0,sortRating.getText().length()-1) + "-";
        sortRating.setText(newtextSortRating);
        sortStateRating = 0;

    }




}