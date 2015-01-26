package lelee.cardsaganistandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.parse.ParseObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class GamePage extends ActionBarActivity {
    public ArrayList<String> whiteCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.fragment_game_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        readFromFile();

        Bundle b = getIntent().getExtras();
        String playerName = b.getString("playerName");

        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName",playerName);
        gameScore.saveInBackground();
    }

    private void readFromFile() {
        whiteCards = new ArrayList<String>();

        try {
            InputStream inputStream = getAssets().open("wcards.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    whiteCards.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("txt file reading error", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("txt file reading error", "Can not read file: " + e.toString());
        }
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_page, menu);
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

        return super.onOptionsItemSelected(item);
    }


}
