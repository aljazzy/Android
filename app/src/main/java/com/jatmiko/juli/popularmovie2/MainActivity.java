package com.jatmiko.juli.popularmovie2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jatmiko.juli.popularmovie2.event.MovieErrorEvent;
import com.jatmiko.juli.popularmovie2.event.MovieEvent;
import com.jatmiko.juli.popularmovie2.model.Movie;
import com.jatmiko.juli.popularmovie2.utility.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_main)
    RecyclerView mMain;

    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    private EventBus eventBus;
    private MovieAdapter adapter;
    private MovieController controller;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MovieController();

        initView();

        errorLayout.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);

        page = 1;
        setPopularMovies(page);
    }

    private void initView() {
        ButterKnife.bind(this);

        int columns = Constant.Function.getColumnsCount(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, columns);
        mMain.setLayoutManager(layoutManager);
        mMain.setHasFixedSize(true);

        adapter = new MovieAdapter();
        mMain.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                errorLayout.setVisibility(View.GONE);
                adapter.setData(new ArrayList<Movie>());
                loadingBar.setVisibility(View.VISIBLE);
                page = 1;
                setPopularMovies(page);
                break;
            case R.id.action_top_rated:
                errorLayout.setVisibility(View.GONE);
                adapter.setData(new ArrayList<Movie>());
                loadingBar.setVisibility(View.VISIBLE);
                page = 1;
                setTopRatedMovies(page);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTopRatedMovies(int page){
        MainActivity.this.setTitle(getString(R.string.top_rated));

        this.page = page;
        controller.getTopRatedMovies(page);
    }

    private void setPopularMovies(int page) {
        MainActivity.this.setTitle(getString(R.string.popular));

        this.page = page;
        controller.getPopularMovies(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus = MainApp.getInstance().getEventBus();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        loadingBar.setVisibility(View.GONE);
        adapter.setData(event.getBody().getResults());
        mMain.scrollToPosition(0);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event){
        loadingBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        Log.e("errorResultData", event.getMessage());
    }
}
