package com.example.gameweb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameweb.adapter.SavedSummonerAdapter;
import com.example.gameweb.data.SummonerSearchRepository;
import com.example.gameweb.dataclass.SummonerClass;
import com.example.gameweb.viewmodel.SavedSummonerViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.gameweb.MainActivity.trigger;

public class SavedSummonerFragment extends Fragment implements SavedSummonerAdapter.OnNameItemClickListener {

    private SavedSummonerViewModel mViewModel;
    private SavedSummonerAdapter adapter;
    static Timer myTimer = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_saved_name,container,false);
        adapter = new SavedSummonerAdapter(this);

        RecyclerView savedReposRV = view.findViewById(R.id.rv_saved_name);
        savedReposRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        savedReposRV.setHasFixedSize(true);

        savedReposRV.setAdapter(adapter);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(this.getActivity().getApplication())
        ).get(SavedSummonerViewModel.class);

        mViewModel.getAllSummoners().observe(this, new Observer<List<SummonerClass>>() {
            @Override

            public void onChanged(List<SummonerClass> gitHubRepos) {
                adapter.updateLocationList(gitHubRepos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.deleteSavedSummoner(adapter.getNameAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SavedSummonerFragment.this.getContext(), "Champion deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(savedReposRV);
         return view;
    }

    @Override
    public void onNameItemClick(SummonerClass summonerClass) {
        //load summoner name
        SummonerSearchRepository newSearch = new SummonerSearchRepository("");
        newSearch.loadSearchResults(summonerClass.name);

//        final Intent intent = new Intent(this, LOLFragment.class);

        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (trigger == 1) {
//                    startActivity(intent);
                    trigger = 0;
                    myTimer.cancel();
                }else if (trigger ==2){
                    myTimer.cancel();
                    trigger = 0;
                }
            }
        }, 500, 500);

    }

}