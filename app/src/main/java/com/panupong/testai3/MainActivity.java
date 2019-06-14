package com.panupong.testai3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //for voice controller
    private RelativeLayout parentRelaytiveLayout;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private String keeper = "";


    //for buttom controller
    private Button voiceEnabledBtn;
    private String mode = "ON";
    private RelativeLayout lowerRelativeLayout;
    private ImageView playpauseBtn, nextBtn, previousBtn;
    private TextView songNameTxt;
    private ImageView Imagelogo;

    //for media player
    private MediaPlayer myMediaPlayer;
    private int position;
    private ArrayList<File> mySongs;
    private String mSongName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkVoicePermission();

        parentRelaytiveLayout = findViewById(R.id.parentlayout);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        songNameTxt = findViewById(R.id.songName);


        //for buttom controller
        voiceEnabledBtn = findViewById(R.id.voice_enabled_btn);
        lowerRelativeLayout = findViewById(R.id.lower);
        playpauseBtn = findViewById(R.id.play_pause_btn);
        nextBtn = findViewById(R.id.next_btn);
        previousBtn = findViewById(R.id.previous_btn);
        Imagelogo = findViewById(R.id.logo);

        validateReceiveValuesAndStartPlaying();
        Imagelogo.setBackgroundResource(R.drawable.logo);


        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {


            }

            @Override
            public void onBeginningOfSpeech() {


            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {


            }

            @Override
            public void onEndOfSpeech() {


            }

            @Override
            public void onError(int error) {


            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matchesFound = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                if (matchesFound != null) {
                    if (mode.equals("ON")) {
                        keeper = matchesFound.get(0);

                        if (keeper.equals("pause the song"))
                        {
                            PlayPauseSong();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play the song"))
                        {
                            PlayPauseSong();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play next song"))
                        {
                            PlayNextSong();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play previous song"))
                        {
                            PlayPreviousSong();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play over the horizon"))
                        {
                            OverTheHorizon();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play please"))
                        {
                            Please();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play youngohm"))
                        {
                            YoungOhm();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play next episode"))
                        {
                            nextEpisode();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play what you know"))
                        {
                            PlayWhatYouKnow();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play wanyai"))
                        {
                            Wanyai();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play in the end"))
                        {
                            IntheEnd();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play what i have done"))
                        {
                            WhateIhaveDone();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play where is the love"))
                        {
                            WhereIsTheLove();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                        else if (keeper.equals("play sexy back"))
                        {
                            Sexyback();
                            Toast.makeText(MainActivity.this, "Command = :" + keeper, Toast.LENGTH_LONG).show();
                        }
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {


            }

            @Override
            public void onEvent(int eventType, Bundle params) {


            }
        });

        parentRelaytiveLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer.startListening(speechRecognizerIntent);
                        keeper = "";
                        break;

                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;
                }


                return false;
            }
        });


        voiceEnabledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("ON")) {
                    mode = "OFF";
                    voiceEnabledBtn.setText("Voice enabled mode-OFF");
                    lowerRelativeLayout.setVisibility(View.VISIBLE);
                } else {
                    mode = "ON";
                    voiceEnabledBtn.setText("Voice enabled mode-ON");
                    lowerRelativeLayout.setVisibility(View.GONE);
                }


            }
        });


        playpauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlayPauseSong();

            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMediaPlayer.getCurrentPosition() > 0) {
                    PlayPreviousSong();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMediaPlayer.getCurrentPosition() > 0) {
                    PlayNextSong();
                }
            }
        });


    }// end of oncreate

    private void validateReceiveValuesAndStartPlaying() {
        if (myMediaPlayer != null) {

            myMediaPlayer.stop();
            myMediaPlayer.release();
        } else {


            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            mySongs = (ArrayList) bundle.getParcelableArrayList("song");
            mSongName = mySongs.get(position).getName();
            String songName = intent.getStringExtra("name");

            songNameTxt.setText(songName);
            songNameTxt.setSelected(true);

            position = bundle.getInt("position", 0);
            Uri uri = Uri.parse(mySongs.get(position).toString());

            myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
            myMediaPlayer.start();


        }


    }

    private void checkVoicePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);


            }


        }


    }

    private void PlayPauseSong() {


        if (myMediaPlayer.isPlaying()) {

            playpauseBtn.setImageResource(R.drawable.pause);
            myMediaPlayer.pause();
        } else {
            playpauseBtn.setImageResource(R.drawable.play);
            myMediaPlayer.start();


        }

    }

    private void PlayNextSong() {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = ((position + 1)) % mySongs.size();

        Uri uri = Uri.parse(mySongs.get(position).toString());

        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);

        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();





    }

    private void PlayPreviousSong() {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = ((position - 1) < 0 ? (mySongs.size() - 1) : (position - 1));
        Uri uri = Uri.parse(mySongs.get(position).toString());

        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();




    }
    private void OverTheHorizon()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 0;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.logo);
    }
    private void Please()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 1;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.pleaseeeee);
    }
    private void YoungOhm()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 2;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.youngohm);
    }
    private void nextEpisode()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 3;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.nextepisode);
    }


    private void PlayWhatYouKnow() {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 4;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.whatyouknow);
    }

    private void Wanyai()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 5;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.wanyai);
    }
    private void IntheEnd()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 6;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.intheend);
    }
    private void WhateIhaveDone()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 7;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.whatihavedone);
    }
    private void WhereIsTheLove()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 8;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.whereisthelove);
    }
    private void Sexyback()
    {
        myMediaPlayer.pause();
        myMediaPlayer.stop();
        myMediaPlayer.release();

        position = 9;
        Uri uri = Uri.parse(mySongs.get(position).toString());
        myMediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        myMediaPlayer.start();

        Imagelogo.setBackgroundResource(R.drawable.sexyback);
    }


}
