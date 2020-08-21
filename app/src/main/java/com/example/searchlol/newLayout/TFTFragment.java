package com.example.searchlol.newLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.searchlol.TFTActivity;
import com.example.searchlol.R;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TFTFragment extends Fragment {
    private static String finalDate;
    private static String myname;

    public TFTFragment() {
        // Required empty public constructor, DO NOT DELETE*
    }

    public void receiveDate(long theDate,String name){
        Date date = new Date(theDate);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        finalDate= format.format(date);
        myname=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tftmatch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView revisedDate= view.findViewById(R.id.tftmydate);
        revisedDate.setText(finalDate);

        Button searchButton = view.findViewById(R.id.myTFTbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TFTActivity.class);
                intent.putExtra("puuid", myname);
                startActivity(intent);
            }
        });
    }

}
