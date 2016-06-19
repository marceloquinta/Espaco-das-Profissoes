package br.ufg.extensao.espacodasprofissoes;

import android.media.Image;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class UselessSplash extends AppCompatActivity {

    private ImageView backgroundImage;
    private long timeToWaste;
    private static final long DEFAULT_TIME = 5000;
    public static final String WASTED_TIME = "WASTED_TIME";
    public static final String BACKGROUND_IMAGE = "BACKGROUND_IMAGE";
    private static final int NOTHING = -1;
    private int srcImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useless_splash);
        timeToWaste = getIntent().getLongExtra(WASTED_TIME,DEFAULT_TIME);
        srcImg = getIntent().getIntExtra(BACKGROUND_IMAGE,NOTHING);
        if(srcImg != NOTHING){
            ImageView image = (ImageView) findViewById(R.id.image_view);
            image.setImageDrawable(ContextCompat.getDrawable(this,srcImg));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountDownTimer countDownTimer = new CountDownTimer(timeToWaste,timeToWaste) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }
}
