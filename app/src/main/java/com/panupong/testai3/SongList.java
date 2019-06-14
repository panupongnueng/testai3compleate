package com.panupong.testai3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class SongList extends AppCompatActivity
{
    private String[] itemAll;
    private ListView mSongsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        appExternalStoragePermission();


        mSongsList = findViewById(R.id.SongListView);

    }

    private void appExternalStoragePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        displayAudioSongName();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();



    }
    public ArrayList<File> readOnlyAudioSongs(File file) {
        ArrayList<File> arrayList = new ArrayList<>();

        File[] allFiles = file.listFiles();

        for (File individualFile : allFiles) {
            if (individualFile.isDirectory() && !individualFile.isHidden()) {
                arrayList.addAll(readOnlyAudioSongs(individualFile));

            } else {
                if (individualFile.getName().endsWith(".mp3") || individualFile.getName().endsWith(".aac") || individualFile.getName().endsWith(".wma")) {
                    arrayList.add(individualFile);
                }
            }
        }

        return arrayList;
    }

    private void displayAudioSongName()
    {

        final ArrayList<File> audioSongs = readOnlyAudioSongs(Environment.getExternalStorageDirectory());

        itemAll = new String[audioSongs.size()];

        for(int songCounter=0;songCounter<audioSongs.size();songCounter++)
        {
            itemAll[songCounter] = audioSongs.get(songCounter).getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SongList.this,android.R.layout.simple_list_item_1,itemAll);
        mSongsList.setAdapter(arrayAdapter);

        mSongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id)
            {

                String songName = mSongsList.getItemAtPosition(i).toString();
                Intent intent = new Intent(SongList.this,MainActivity.class);
                intent.putExtra("song",audioSongs);
                intent.putExtra("name",songName);
                intent.putExtra("position",i);
                startActivity(intent);
                Log.i("Test","click"+i);
            }
        });

    }



}
