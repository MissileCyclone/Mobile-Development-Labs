package com.robert.a9;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView recipeOutput;
    private TextToSpeech tts;
    private VideoView recipeVideo;
    private MediaPlayer startupPlayer;


    private MediaPlayer alarmPlayer1, alarmPlayer2;

    private View timerContainer, layoutTimer1, layoutTimer2;
    private TextView timerText1, timerText2;
    private EditText timerInput1, timerInput2;

    private CountDownTimer timer1, timer2;
    private long timeLeft1, timeLeft2;
    private boolean isTimer1Running, isTimer2Running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeOutput = findViewById(R.id.recipeOutput);
        recipeVideo = findViewById(R.id.recipeVideo);
        timerContainer = findViewById(R.id.timerContainer);

        layoutTimer1 = findViewById(R.id.layoutTimer1);
        layoutTimer2 = findViewById(R.id.layoutTimer2);

        timerText1 = findViewById(R.id.timerText1);
        timerText2 = findViewById(R.id.timerText2);

        timerInput1 = findViewById(R.id.timerInput1);
        timerInput2 = findViewById(R.id.timerInput2);

        if (!getIntent().getBooleanExtra("is_restarting", false)) playStartupSound();
        tts = new TextToSpeech(this, this);

        findViewById(R.id.btnRu).setOnClickListener(v -> setAppLocale("ru"));
        findViewById(R.id.btnEn).setOnClickListener(v -> setAppLocale("en"));
        findViewById(R.id.btnZh).setOnClickListener(v -> setAppLocale("zh"));

        RadioGroup filterGroup = findViewById(R.id.filterGroup);
        filterGroup.setOnCheckedChangeListener((group, checkedId) -> {
            updateUI(checkedId);
            speak();
        });

        setupTimer1Logic();
        setupTimer2Logic();

        updateUI(R.id.radioBreakfast);
    }

    private void stopAlarm1() {
        if (alarmPlayer1 != null) {
            alarmPlayer1.stop();
            alarmPlayer1.release();
            alarmPlayer1 = null;
        }
    }

    private void stopAlarm2() {
        if (alarmPlayer2 != null) {
            alarmPlayer2.stop();
            alarmPlayer2.release();
            alarmPlayer2 = null;
        }
    }

    private void setupTimer1Logic() {
        findViewById(R.id.btnStart1).setOnClickListener(v -> {
            if (isTimer1Running) return;
            stopAlarm1();
            if (timeLeft1 <= 0) {
                String input = timerInput1.getText().toString();
                if (input.isEmpty()) return;
                timeLeft1 = Long.parseLong(input) * 1000;
            }
            isTimer1Running = true;
            timer1 = new CountDownTimer(timeLeft1, 1000) {
                @Override
                public void onTick(long ms) { timeLeft1 = ms; updateTimerText(timerText1, ms); }
                @Override
                public void onFinish() {
                    isTimer1Running = false;
                    timeLeft1 = 0;
                    alarmPlayer1 = MediaPlayer.create(MainActivity.this, R.raw.alarm1);
                    if (alarmPlayer1 != null) alarmPlayer1.start();
                }
            }.start();
        });
        findViewById(R.id.btnPause1).setOnClickListener(v -> {
            if(timer1 != null) timer1.cancel();
            isTimer1Running = false;
            stopAlarm1();
        });
        findViewById(R.id.btnReset1).setOnClickListener(v -> {
            if(timer1 != null) timer1.cancel();
            isTimer1Running = false;
            timeLeft1 = 0;
            timerText1.setText("00:00");
            timerText1.setTextColor(Color.BLACK);
            stopAlarm1();
        });
    }

    private void setupTimer2Logic() {
        findViewById(R.id.btnStart2).setOnClickListener(v -> {
            if (isTimer2Running) return;
            stopAlarm2();
            if (timeLeft2 <= 0) {
                String input = timerInput2.getText().toString();
                if (input.isEmpty()) timeLeft2 = 60000;
                else timeLeft2 = Long.parseLong(input) * 1000;
            }
            isTimer2Running = true;
            timer2 = new CountDownTimer(timeLeft2, 1000) {
                @Override
                public void onTick(long ms) { timeLeft2 = ms; updateTimerText(timerText2, ms); }
                @Override
                public void onFinish() {
                    isTimer2Running = false;
                    timeLeft2 = 0;
                    alarmPlayer2 = MediaPlayer.create(MainActivity.this, R.raw.alarm2);
                    if (alarmPlayer2 != null) alarmPlayer2.start();
                }
            }.start();
        });
        findViewById(R.id.btnPause2).setOnClickListener(v -> {
            if(timer2 != null) timer2.cancel();
            isTimer2Running = false;
            stopAlarm2();
        });
        findViewById(R.id.btnReset2).setOnClickListener(v -> {
            if(timer2 != null) timer2.cancel();
            isTimer2Running = false;
            timeLeft2 = 0;
            timerText2.setText("00:00");
            timerText2.setTextColor(Color.BLACK);
            stopAlarm2();
        });
    }

    private void updateTimerText(TextView tv, long millis) {
        int m = (int) (millis / 1000) / 60;
        int s = (int) (millis / 1000) % 60;
        tv.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
        tv.setTextColor(millis < 60000 ? Color.RED : Color.BLACK);
    }

    private void updateUI(int checkedId) {
        if (tts != null) tts.stop();
        recipeVideo.stopPlayback();
        recipeVideo.setVisibility(View.GONE);
        timerContainer.setVisibility(View.VISIBLE);

        if (checkedId == R.id.radioBreakfast) {
            recipeOutput.setText(R.string.recipe_scrambled_eggs_full);
        } else if (checkedId == R.id.radioLunch) {
            recipeOutput.setText(R.string.recipe_borch_full);
        } else if (checkedId == R.id.radioDinner) {
            recipeOutput.setText(R.string.recipe_steak_full);
        }
    }

    private void playStartupSound() {
        try {
            startupPlayer = MediaPlayer.create(this, R.raw.startup_sound);
            if (startupPlayer != null) {
                startupPlayer.start();
                startupPlayer.setOnCompletionListener(MediaPlayer::release);
            }
        } catch (Exception e) {}
    }

    private void setAppLocale(String langCode) {
        MediaPlayer.create(this, R.raw.click_sound).start();
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration conf = getResources().getConfiguration();
        conf.setLocale(locale);
        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("is_restarting", true);
        startActivity(intent);
        finish();
    }

    private void showVideo() {
        recipeVideo.setVisibility(View.VISIBLE);
        recipeVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_recipe));
        recipeVideo.setOnPreparedListener(mp -> { mp.setLooping(true); recipeVideo.start(); });
        recipeVideo.setOnClickListener(v -> { if (recipeVideo.isPlaying()) recipeVideo.pause(); else recipeVideo.start(); });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) tts.setLanguage(getResources().getConfiguration().locale);
    }

    private void speak() {
        if (tts != null) {
            tts.setLanguage(getResources().getConfiguration().locale);
            String text = recipeOutput.getText().toString();
            tts.setOnUtteranceProgressListener(new android.speech.tts.UtteranceProgressListener() {
                @Override public void onStart(String id) {}
                @Override public void onError(String id) {}
                @Override public void onDone(String id) {
                    runOnUiThread(() -> {
                        if (((RadioGroup)findViewById(R.id.filterGroup)).getCheckedRadioButtonId() == R.id.radioDinner) showVideo();
                    });
                }
            });
            Bundle p = new Bundle();
            p.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "id");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, p, "id");
        }
    }

    @Override protected void onDestroy() {
        if (tts != null) tts.shutdown();
        stopAlarm1();
        stopAlarm2();
        super.onDestroy();
    }
}