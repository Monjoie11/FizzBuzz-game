package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GestureDetectorCompat;
import androidx.preference.PreferenceManager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private Random rng = new Random();
  private int value;
  private TextView valueDisplay;
  private ViewGroup valueContainer;
  private Timer timer;
  private boolean running;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d("Trace", "Entering onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    valueDisplay = findViewById(R.id.value_display);
    valueContainer = findViewById(R.id.value_container);
    GestureDetectorCompat detector = new GestureDetectorCompat(this, new FlingListener());
    valueContainer.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
      if (!detector.onTouchEvent(event) && event.getActionMasked() == MotionEvent.ACTION_UP){
        valueContainer.setTranslationY(0);
        valueContainer.setTranslationX(0);
      }
        return true;
      }
    });
    Log.d("Trace", "Leaving onCreate");
  }

  @Override
  protected void onResume() {
    Log.d("Trace", "Entering onResume");
    super.onResume();
    Log.d("Trace", "Leaving onResume");
  }

  @Override
  protected void onRestart() {
    Log.d("Trace", "Entering onRestart");
    super.onRestart();
    Log.d("Trace", "Leaving onRestart");
  }

  @Override
  protected void onStart() {
    Log.d("Trace", "Entering onStart");
    super.onStart();
    Log.d("Trace", "Leaving onStart");
  }

  @Override
  protected void onStop() {
    Log.d("Trace", "Entering onStop");
    super.onStop();
    Log.d("Trace", "Leaving onStop");
  }

  @Override
  protected void onPause() {
    Log.d("Trace", "Entering onPause");
    super.onPause();
    Log.d("Trace", "Leaving onPause");
  }

  @Override
  protected void onDestroy() {
    Log.d("Trace", "Entering onDestroy");
    super.onDestroy();
    Log.d("Trace", "Leaving onDestroy");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    Log.d("Trace", "Entering onSaveInstancedState");
    super.onSaveInstanceState(outState);
    Log.d("Trace", "Leaving onSaveInstancedState");
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Log.d("Trace", "Entering onCreateOptionsMeu");
    getMenuInflater().inflate(R.menu.options, menu);
    Log.d("Trace", "Leaving onCreateOptionsMeu");
    return true;
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    Log.d("Trace", "Entering onRestoreInstancedState");
    super.onRestoreInstanceState(savedInstanceState);
    Log.d("Trace", "Leaving onRestoreInstancedState");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d("Trace", "Entering onOptionsItemSelected");
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.play:
        resumeGame();
        break;
      case R.id.pause:
        pauseGame();
        break;
      case R.id.settings:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        break;
      default:
        handled = super.onOptionsItemSelected(item);
        break;
    }
    Log.d("Trace", "Leaving onOptionsItemSelected");
    return handled;
  }

  private void pauseGame() {
    running = false;
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    //TODO update and necessary fields, timer, & menu
    invalidateOptionsMenu();
  }

  private void resumeGame() {
    running = true;
    if (timer != null){
      timer.cancel();
    }
    int timeLimit = PreferenceManager.getDefaultSharedPreferences(this)
        .getInt(getString(R.string.time_limit_key),
            getResources().getInteger(R.integer.time_limit_default));
    updateValue();
    if (timeLimit != 0) {
      timer = new Timer();
      timer.schedule(new RandomValueTask(), timeLimit * 1000); //FIXME This should read preferences
    }
    //TODO update and necessary fields, timer, & menu
    invalidateOptionsMenu();
  }

  private void updateValue() {
    int numDigits = PreferenceManager.getDefaultSharedPreferences(this)
        .getInt(getString(R.string.num_digits_key),
            getResources().getInteger(R.integer.num_digits_default));
    int limit = (int) Math.pow(10, numDigits) - 1;
    value = 1 + rng.nextInt(limit);
    valueContainer.setTranslationX(0);
    valueContainer.setTranslationY(0);
    valueDisplay.setText(Integer.toString(value));
  }

  private class RandomValueTask extends TimerTask {

    @Override
    public void run() {
      Log.d("Trace", "Entering run");
      runOnUiThread(() -> resumeGame());
      Log.d("Trace", "Leaving run");
    }
  }


  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem play = menu.findItem(R.id.play);
    MenuItem pause = menu.findItem(R.id.pause);
    play.setVisible(!running);
    play.setEnabled(!running);
    pause.setVisible(running);
    pause.setEnabled(running);
    return true;
  }
  private class FlingListener extends GestureDetector.SimpleOnGestureListener{

    private float originX;
    private float originY;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      valueContainer.setTranslationX(e2.getX()-originX);
      valueContainer.setTranslationY(e2.getY()-originY);
      return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      //TODO update tally; detect if it really is a fling, and in which direction;
      Log.d("Trace",e2.toString());
      resumeGame();
      return true;
    }

    @Override
    public boolean onDown(MotionEvent evt) {
      originX = evt.getX();
      originY = evt.getY();
      return true;
    }
  }
}
