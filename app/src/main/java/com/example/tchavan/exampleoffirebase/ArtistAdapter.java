package com.example.tchavan.exampleoffirebase;

import android.app.Activity;
import android.content.Context;
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

public class ArtistAdapter extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> lstArtist;


    public ArtistAdapter(Activity context, List<Artist> artists) {
        super(context, R.layout.list_layout, artists);
        this.context = context;
        this.lstArtist = artists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View listViewItem = layoutInflater.inflate(R.layout.list_layout, null, true);

        ((TextView) listViewItem.findViewById(R.id.lblArtistName)).setText(lstArtist.get(position).getArtistName());

        ((TextView) listViewItem.findViewById(R.id.lblArtistType)).setText(lstArtist.get(position).getArtistGenre());


        return listViewItem;


    }
}
