package com.bnsantos.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnsantos.movies.R;
import com.bnsantos.movies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by bruno on 14/11/14.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_movie, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.movieTitle);
            TextView score = (TextView) convertView.findViewById(R.id.movieScore);
            TextView rating = (TextView) convertView.findViewById(R.id.movieRating);
            ImageView poster = (ImageView) convertView.findViewById(R.id.moviePoster);
            convertView.setTag(new ViewHolder(title, score, rating, poster));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        final Movie movie = getItem(position);

        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setText(movie.getMpaa_rating());
        viewHolder.score.setText(Integer.toString(movie.getRatings().getCritics_score()));
        Picasso.with(getContext()).load(movie.getPosters().getDetailed()).fit().into(viewHolder.poster);

        return convertView;
    }

    private class ViewHolder {
        public final TextView title;
        public final TextView score;
        public final TextView rating;
        public final ImageView poster;

        private ViewHolder(TextView title, TextView score, TextView rating, ImageView poster) {
            this.title = title;
            this.score = score;
            this.rating = rating;
            this.poster = poster;
        }
    }
}
