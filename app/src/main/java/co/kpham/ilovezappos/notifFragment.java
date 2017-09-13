package co.kpham.ilovezappos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kito Pham on 9/12/2017.
 */

public class notifFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.notif_fragment, container, false);
    }
}
