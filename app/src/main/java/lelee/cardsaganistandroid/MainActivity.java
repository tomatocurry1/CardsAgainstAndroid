package lelee.cardsaganistandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "9YFp8fysZrbhMeTLgTxpR7LjnDcEHyaQpHpSn5sK", "HOYpty0AGdpQ4OX8mWFVNUAzpbCqTFFRAG9PDuNo");

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void gameStart(View view){
        mEdit = (EditText)findViewById(R.id.editText);
        Intent intent = new Intent(MainActivity.this, GamePage.class);
        Bundle b = new Bundle();
        b.putString("playerName", mEdit.getText().toString());
        intent.putExtras(b);

        final ParseObject player = new ParseObject("Users");
        player.put("name", b.getString("playerName"));
        player.put("points", 0);
        player.put("dealtCard", null);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.whereEqualTo("isCzar", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    player.put("isCzar", false);
                } else {
                    player.put("isCzar", true);
                }
                player.saveInBackground();
            }
        });

        startActivity(intent);
        finish();
    }
}
