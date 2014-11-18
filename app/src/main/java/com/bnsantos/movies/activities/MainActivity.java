package com.bnsantos.movies.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bnsantos.movies.App;
import com.bnsantos.movies.R;
import com.bnsantos.movies.adapter.MovieAdapter;
import com.bnsantos.movies.model.Movie;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;


public class MainActivity extends Activity {
    private ListView mListView;
    private MovieAdapter mAdapter;
    private ProgressBar mSpinner;

    private Subscription mSubscription;
    private int from = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initAdapter();

        mSubscription = App.getInstance().getProvider().subscribe().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movies) {
                        Toast.makeText(MainActivity.this, "worked " + from++, Toast.LENGTH_SHORT).show();
                        mAdapter.addAll(movies);
                        mSpinner.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "ops... error", Toast.LENGTH_SHORT).show();
                        mSpinner.setVisibility(View.GONE);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.moviesListView);
        mSpinner = (ProgressBar) findViewById(R.id.moviesLoadingSpinner);
    }

    private void initAdapter() {
        mAdapter = new MovieAdapter(this, R.layout.adapter_movie);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mSubscription.unsubscribe();
        super.onDestroy();
    }
}
