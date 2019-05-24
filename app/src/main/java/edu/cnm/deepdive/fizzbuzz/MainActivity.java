package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private int value;
  private TextView valueDisplay;
  private Timer timer;
  private boolean running;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d("Trace", "Entering onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    valueDisplay = findViewById(R.id.value_display);
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
  private void pauseGame(){
    running =false;
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    //TODO update and necessary fields, timer, & menu
    invalidateOptionsMenu();
  }

  private void resumeGame() {
    running = true;
    timer = new Timer();
    timer.schedule(new RandomValueTask(), 0, 3000); //FIXME This should read preferences
    //TODO update and necessary fields, timer, & menu
    invalidateOptionsMenu();
  }

  private class RandomValueTask extends TimerTask {

    private Random rng = new Random();

    @Override
    public void run() {
      Log.d("Trace", "Entering run");
      value = rng.nextInt(100);
      runOnUiThread(() -> valueDisplay.setText(Integer.toString(value)));
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
  }
