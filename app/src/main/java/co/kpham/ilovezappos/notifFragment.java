package co.kpham.ilovezappos;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.FileNotFoundException;

import co.kpham.ilovezappos.data.fileClient;

/**
 * Created by Kito Pham on 9/12/2017.
 */

public class notifFragment extends Fragment {

    public EditText price;
    public SharedPreferences prefs;
    private RecyclerView notifRecyclerView;
    private notifAdapter notifAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.notif_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getActivity().getSharedPreferences(
                "co.kpham.ilovezappos", Context.MODE_PRIVATE);

        //prefs.edit().remove("price");
        price = (EditText) getView().findViewById(R.id.notificationPrice);
        price.setText(prefs.getString("price", "4000"));
        prefs.edit().putString("price", price.getText().toString()).commit();

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (price.getText().toString().equals("")){
                    price.setText("0");
                } else {
                    prefs.edit().putString("price", price.getText().toString()).apply();
                    Log.d("notifTest", "afterTextChanged: setting changed to " + prefs.getString("price", "4000"));
                }
            }
        });
        notifAdapter = new notifAdapter(getView().getContext());
        renderNotificationsTable();
    }

    public void renderNotificationsTable(){

        fileClient.readFile(getView().getContext(), "notifs");

        notifRecyclerView = (RecyclerView) getView().findViewById(R.id.notifRecycler);
        RecyclerView.LayoutManager bidLayoutManager = new LinearLayoutManager(getView().getContext());
        notifRecyclerView.setLayoutManager(bidLayoutManager);
        notifRecyclerView.setAdapter(notifAdapter);
        notifRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL);
        notifRecyclerView.addItemDecoration(itemDecoration);

    }
}
