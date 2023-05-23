package red.project.uni.lu.ui.film_detailled;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import red.project.uni.lu.BuildConfig;
import red.project.uni.lu.R;
import red.project.uni.lu.databinding.FragmentHomeBinding;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;

public class FilmDetailledFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private StringRequest mStringRequestReco;
    private ToWatchDataSource toWatchDataSource;
    int movieId;
    ArrayList<SlideModel> recoModels;
    ImageSlider recoSlider;
    View view;


    ImageView poster;
    String posterPath;
    TextView title;
    TextView description;
    TextView genres;
    TextView rating;
    String releaseDate;
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
        recoSlider = view.findViewById(R.id.RecoNoWatchSlider);
        this.toWatchDataSource = new ToWatchDataSource(view.getContext());

        Bundle bundle = this.getArguments();
        assert bundle != null;
        movieId = (int) bundle.get("movieID");
        System.out.println("from detailled : " + movieId);
        recoModels = new ArrayList<>();

        // call to the api and put into all the fields
        getMovieFromId(movieId);








        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Make api call to get specific movie infos and put them into the fields
    private void getMovieFromId(int id) {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR";
        mRequestQueue = Volley.newRequestQueue(view.getContext());
        mStringRequest = new StringRequest(url, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObj = new JSONObject(response);

                // Setup the fields
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + jsonObj.getString("poster_path")).into(poster);
                posterPath = "https://image.tmdb.org/t/p/w500"+jsonObj.getString("poster_path");
                title.setText(jsonObj.getString("title"));
                description.setText(jsonObj.getString("overview"));
                description.setMovementMethod(new android.text.method.ScrollingMovementMethod());
                releaseDate = jsonObj.getString("release_date");
                StringBuilder genres = new StringBuilder("Genres : \n");
                for (int i = 0; i < jsonObj.getJSONArray("genres").length(); i++) {
                    genres.append(" - ").append(jsonObj.getJSONArray("genres").getJSONObject(i).getString("name")).append("\n");
                }
                this.genres.setText(genres.toString());
                rating.setText("Note : " + jsonObj.getString("vote_average") + "/10");

                // Setup the buttons
                addWatched.setOnClickListener(v -> {
                    // TODO : add to watched
                });

                addToWatch.setOnClickListener(v -> {
                    // Open the dialog to add to watch
                    this.toWatchDataSource.open();
                    if (this.toWatchDataSource.isInToWatchList(movieId)) {
                        // Make a toast to say that the movie is already in the list
                        Toast.makeText(view.getContext(), "This movie is already in your watchlist", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        System.out.println("Add to watch");
                        AddToWatchDialog dialog = AddToWatchDialog.newInstance(movieId, title.getText().toString(),posterPath,releaseDate,description.getText().toString());
                        dialog.show(getChildFragmentManager(), "Add to watch");
                    }
                    this.toWatchDataSource.close();
                });

                // setup the recommendations slider
                String url2 = "https://api.themoviedb.org/3/movie/" + id + "/recommendations?api_key="+BuildConfig.TMDB_API_KEY+"&language=fr-FR&page=1";
                mStringRequestReco = new StringRequest(url2, response2 -> {
                    try {
                        System.out.println(response2);
                        JSONObject jsonObj2 = new JSONObject(response2);
                        for (int i = 0; i < jsonObj2.getJSONArray("results").length(); i++) {
                            recoModels.add(new SlideModel("https://image.tmdb.org/t/p/w500" + jsonObj2.getJSONArray("results").getJSONObject(i).getString("backdrop_path"),jsonObj2.getJSONArray("results").getJSONObject(i).getString("title"), ScaleTypes.FIT));
                        }

                        recoSlider.setImageList(recoModels, ScaleTypes.FIT);
                        recoSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void doubleClick(int i) {
                                // NOTHING TO DO
                            }

                            @Override
                            public void onItemSelected(int i) {
                                // TODO : redirect to the good view but when i'll have the database
                                System.out.println("clicked on reco : " + i);
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }, error -> {
                    Log.i("Error", error.toString());
                });
                mRequestQueue.add(mStringRequestReco);


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.i("Error", error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

}