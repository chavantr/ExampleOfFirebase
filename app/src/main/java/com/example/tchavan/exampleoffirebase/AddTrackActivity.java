package com.example.tchavan.exampleoffirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    private TextView lblArtistName;
    private EditText txtTrackName;
    private Spinner spnRating;
    private ListView lstTracks;
    private Intent intent;
    private String id;
    private String name;
    private DatabaseReference databaseReferenceTrack;
    private List<Track> listTracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        intent = getIntent();
        lblArtistName = findViewById(R.id.lblArtistName);
        txtTrackName = findViewById(R.id.txtArtistName);
        spnRating = findViewById(R.id.spnArtist);
        lstTracks = findViewById(R.id.lstGeneres);

        listTracks = new ArrayList<>();


        id = intent.getStringExtra(MainActivity.ARTIST_ID);
        name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        lblArtistName.setText(name);

        findViewById(R.id.save).setOnClickListener(saveListener());

        databaseReferenceTrack = FirebaseDatabase.getInstance().getReference("tracks").child(id);


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceTrack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listTracks.clear();

                for (DataSnapshot trackSnapShot : dataSnapshot.getChildren()) {

                    Track track = trackSnapShot.getValue(Track.class);

                    listTracks.add(track);

                }

                TrackAdapter trackAdapter = new TrackAdapter(AddTrackActivity.this, listTracks);

                lstTracks.setAdapter(trackAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @NonNull
    private View.OnClickListener saveListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = databaseReferenceTrack.push().getKey();
                Track track = new Track(id, txtTrackName.getText().toString(), Integer.parseInt(spnRating.getSelectedItem().toString()));
                databaseReferenceTrack.child(id).setValue(track);
            }
        };
    }
}
