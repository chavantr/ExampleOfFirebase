package com.example.tchavan.exampleoffirebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String ARTIST_NAME = "artistName";
    public static final String ARTIST_ID = "artistId";
    private DatabaseReference databaseReferenceArtist;
    private ListView lstGeners;
    private List<Artist> lstArtist;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReferenceArtist = FirebaseDatabase.getInstance().getReference("artists");

        findViewById(R.id.save).setOnClickListener(saveListener());
        lstGeners = (ListView) findViewById(R.id.lstGeneres);

        lstArtist = new ArrayList<>();

        lstGeners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = lstArtist.get(i);

                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class);
                intent.putExtra(ARTIST_NAME, artist.getArtistName());
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                startActivity(intent);
            }
        });

        lstGeners.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist = lstArtist.get(i);


                showUpdateDialog(artist.getArtistId(), artist.getArtistName());

                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lstArtist.clear();

                for (DataSnapshot artistSnapShot : dataSnapshot.getChildren()) {
                    Artist artist = artistSnapShot.getValue(Artist.class);
                    lstArtist.add(artist);
                }

                ArtistAdapter artistAdapter = new ArtistAdapter(MainActivity.this, lstArtist);
                lstGeners.setAdapter(artistAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void showUpdateDialog(final String artistId, String artistName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = getLayoutInflater();

        final View dialogView = layoutInflater.inflate(R.layout.update_dialog, null);


        builder.setView(dialogView);

        final EditText txtName = dialogView.findViewById(R.id.txtName);

        final TextView lblTitleName = dialogView.findViewById(R.id.lblTitleName);

        final Button update = dialogView.findViewById(R.id.update);
        final Spinner spnGeneres = dialogView.findViewById(R.id.spnArtist);
        final Button delete = dialogView.findViewById(R.id.delete);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArtist(artistId, txtName.getText().toString(), spnGeneres.getSelectedItem().toString());
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(artistId);
            }
        });


        builder.setTitle("Update Artist");

        dialog = builder.create();

        dialog.show();


    }

    private void deleteArtist(String artistId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(artistId);

        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(artistId);

        databaseReference.removeValue();

        drTracks.removeValue();


    }

    private void updateArtist(String id, String name, String genre) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id);

        Artist artist = new Artist(id, name, genre);

        databaseReference.setValue(artist);


    }

    @NonNull
    private View.OnClickListener saveListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id = databaseReferenceArtist.push().getKey();

                Artist artist = new Artist(id, "Heyyyyyyyy", "HipPop");

                databaseReferenceArtist.child(id).setValue(artist);

                Toast.makeText(MainActivity.this, "Hey", Toast.LENGTH_LONG).show();
            }
        };
    }
}
