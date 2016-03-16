package com.company.g5.g5labtest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class PartA_Activity extends AppCompatActivity {
    private String webLink="http://hkuspace.hku.hk/cc";
    Button butHome;
    MediaPlayer myMus = null; // a data member of MediaPlayer

    @Override
    protected void onResume(){ // callback method, active: when interacting with user
        super.onResume(); // always call superclass
        if (myMus != null) myMus.start(); // start playing
    }
    @Override
    protected void onPause(){ // callback method, inactive: when no interacting
        super.onPause(); // always call superclass
        if (myMus != null) myMus.pause(); // start playing
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_a_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        butHome=(Button) findViewById(R.id.button);
        butHome.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View V){
                        finish();
                    }
                }
        );
        WebView aWebView = (WebView) findViewById(R.id.webView);
        WebSettings aWebSettings = aWebView.getSettings();
        aWebSettings.setJavaScriptEnabled(true);
        aWebView.loadUrl(webLink);
        aWebView.setWebViewClient(new WebViewClient());

        myMus = MediaPlayer.create(this, R.raw.sound); // sound file “s1” in raw folder
        myMus.setLooping(true); // set loop-playing mode
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_part_a, menu);
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

        if (id == R.id.action_soundoff) {
            if (myMus != null && myMus.isPlaying()) myMus.pause();
            return true;
        }
        if (id == R.id.action_soundon) {
            if (myMus != null && !myMus.isPlaying()) myMus.start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
