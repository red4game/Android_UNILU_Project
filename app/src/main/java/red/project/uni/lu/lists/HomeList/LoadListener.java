package red.project.uni.lu.lists.HomeList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public abstract class LoadListener extends RecyclerView.OnScrollListener {
    LinearLayoutManager llm; // llm = LinearLayoutManager
    public LoadListener(LinearLayoutManager llm) {
        this.llm = llm;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView,dx,dy);
        int visibleItemCount = llm.getChildCount();
        int totalItemCount = llm.getItemCount();
        int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
