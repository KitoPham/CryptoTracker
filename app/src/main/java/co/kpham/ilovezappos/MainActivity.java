package co.kpham.ilovezappos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static BitStampSingleton singleton;
    private TextView mTextMessage;
    private FragmentManager fm;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getFragmentManager();
        changeFrags(new orderBookFragment());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fr;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fr = new orderBookFragment();
                    changeFrags(fr);
                    break;
                case R.id.navigation_graph:
                    fr = new graphFragment();
                    changeFrags(fr);
                    break;
                case R.id.navigation_notifications:
                    fr= new notifFragment();
                    changeFrags(fr);
                    break;
            }
            return true;
        }

    };
    private void changeFrags(Fragment fr){
        Log.d("Process", "changeFrags: fragment transaction: " + fr);
        fm.beginTransaction()
            .replace(R.id.fragment_frame, fr)
            .commit();
        Log.d("Process", "changeFrags: fragmentTransaction finished");
    }

}
