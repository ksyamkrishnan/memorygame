package com.example.syamkrishnanck.memorygamesample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends AppCompatActivity {

    int fragment_container=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        fragment_container = R.id.fragment_container;
        List<String> image = getIntent().getStringArrayListExtra("image");
        QueryFragment queryFragment = new QueryFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("image", (ArrayList<String>) image);
        queryFragment.setArguments(bundle);
        loadFragmentWithTag(queryFragment, false, "sd", "ds");

    }
    public void loadFragmentWithTag(Fragment fragment, boolean pushToBackstack, String backstackName, String tag) {
        backstackName= fragment.getClass().getSimpleName();
        tag= fragment.getClass().getSimpleName();
        Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        if(oldFragment!=null) {
            getSupportFragmentManager().beginTransaction().remove(oldFragment).commit();
        }

        try {
            if (fragment_container > 0) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (pushToBackstack) {
                    ft.addToBackStack(backstackName);
                } else {
                    try {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ft.replace(fragment_container, fragment, tag);
                ft.commit();
//                getSupportFragmentManager().executePendingTransactions();
            } else {
                throw new RuntimeException("must call setFragmentContainerId() with id for container");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
