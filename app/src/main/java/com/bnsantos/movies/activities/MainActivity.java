package com.bnsantos.movies.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bnsantos.movies.App;
import com.bnsantos.movies.MovieListType;
import com.bnsantos.movies.R;
import com.bnsantos.movies.Utils;
import com.bnsantos.movies.adapter.MovieAdapter;
import com.bnsantos.movies.controller.request.RetrieveMoviesRequest;
import com.bnsantos.movies.model.Movie;

import java.util.List;


public class MainActivity extends Activity implements Response.ErrorListener, Response.Listener<List<Movie>> {
    private ListView mListView;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().getRestController().addRequest(new RetrieveMoviesRequest(MovieListType.IN_THEATERS, 10, this, this), "retrieving movies");

        mListView = (ListView) findViewById(R.id.moviesListView);
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
    public void onErrorResponse(VolleyError volleyError) {
        Utils.logVolleyError(MainActivity.class.getName(), volleyError);
        Toast.makeText(this, "ops... error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<Movie> movies) {
        Toast.makeText(this, "worked", Toast.LENGTH_SHORT).show();
        mAdapter.addAll(movies);
        //mAdapter.notifyDataSetChanged();
    }
}
