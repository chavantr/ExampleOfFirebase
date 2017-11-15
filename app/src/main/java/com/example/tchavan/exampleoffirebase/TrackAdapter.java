package com.example.tchavan.exampleoffirebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tchavan on 11/8/2017.
 */

public class TrackAdapter extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> lstTracks;


    public TrackAdapter(Activity context, List<Track> tracks) {
        super(context, R.layout.list_layout, tracks);
        this.context = context;
        this.lstTracks = tracks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View listViewItem = layoutInflater.inflate(R.layout.list_layout, null, true);

        ((TextView) listViewItem.findViewById(R.id.lblArtistName)).setText(lstTracks.get(position).getTrackName());

        ((TextView) listViewItem.findViewById(R.id.lblArtistType)).setText("" + lstTracks.get(position).getTrackRating());


        return listViewItem;


    }
}
