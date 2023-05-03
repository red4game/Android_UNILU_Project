package red.project.uni.lu.ui.no_watched_detailled;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoWatchedDetailledViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NoWatchedDetailledViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}