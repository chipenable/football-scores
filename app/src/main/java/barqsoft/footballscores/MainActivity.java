package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
    private static final String LOG_TAG = "MainActivity";
    private static final String SAVE_TAG = "Save Test";
    private static final String KEY_PAGER_CURRENT = "pager_current";
    private static final String KEY_SELECTED_MATCH = "selected_match";
    private static final String KEY_PAGER_FRAGMENT = "pager_fragment";

    public static int sSelectedMatchId;
    public static int sCurrentFragment = 2;

    private PagerFragment mMyMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Reached MainActivity onCreate");


        if (savedInstanceState == null) {
            mMyMain = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mMyMain)
                    .commit();
        }
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
        if (id == R.id.action_about)
        {
            Intent startAbout = new Intent(this,AboutActivity.class);
            startActivity(startAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.v(SAVE_TAG,"will save");
        Log.v(SAVE_TAG,"fragment: " + String.valueOf(mMyMain.mPagerHandler.getCurrentItem()));
        Log.v(SAVE_TAG,"selected id: " + sSelectedMatchId);

        outState.putInt(KEY_PAGER_CURRENT, mMyMain.mPagerHandler.getCurrentItem());
        outState.putInt(KEY_SELECTED_MATCH, sSelectedMatchId);
        getSupportFragmentManager().putFragment(outState, KEY_PAGER_FRAGMENT, mMyMain);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.v(SAVE_TAG,"will retrive");
        Log.v(SAVE_TAG,"fragment: " + String.valueOf(savedInstanceState.getInt(KEY_PAGER_CURRENT)));
        Log.v(SAVE_TAG,"selected id: " + savedInstanceState.getInt(KEY_SELECTED_MATCH));

        sCurrentFragment = savedInstanceState.getInt(KEY_PAGER_CURRENT);
        sSelectedMatchId = savedInstanceState.getInt(KEY_SELECTED_MATCH);
        mMyMain = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_PAGER_FRAGMENT);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
