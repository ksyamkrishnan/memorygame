package com.example.syamkrishnanck.memorygamesample;


import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {


    View rootView;
    ImageView imageView;
    EditText answer;
    int anchor = 0;
    String currentImageView;
    List<String> imageList = new ArrayList<>();
    boolean[] sucess = new boolean[15];
    public QueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        Bundle bundle = getArguments();
        imageList = bundle.getStringArrayList("image");
        imageView = (ImageView) rootView.findViewById(R.id.image_ques);
        answer = (EditText) rootView.findViewById(R.id.answer);
        answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try{
                    hideKeybord();
                    int searchText = Integer.parseInt(v.getText().toString());
                    if (imageList.get(searchText).equalsIgnoreCase(currentImageView)) {
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
                        setQuestion();
                    } else {
                        Toast.makeText(getActivity(), "Sorry!!", Toast.LENGTH_SHORT).show();
                    }}catch (Exception e){
                        Toast.makeText(getActivity(), "Sorry!!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        setQuestion();
        return rootView;
    }

    private void setQuestion() {
        Random random= new Random();
        int i = random.nextInt(15);
        if (anchor == 9) {
            QueryActivity queryActivity = (QueryActivity) getActivity();
            queryActivity.loadFragmentWithTag(new Sucess(),false,"","");
            Toast.makeText(getActivity(), "Winnner", Toast.LENGTH_LONG).show();
            return;
        }

        for (int y=0; y < 15; y++) {
            if (imageList.get(i) != null && !imageList.get(i).isEmpty()&&sucess[i]==false) {
                currentImageView = imageList.get(i);
                answer.setText("");
                Glide.with(getActivity().getApplicationContext()).load(imageList.get(i)).centerCrop().centerCrop()
                        .into(imageView);
                sucess[i]=true;
                anchor++;
                return;
            }
        }
        setQuestion();
    }

    public void hideKeybord() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }catch (Exception exception){
        }

    }

}
