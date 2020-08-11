package com.example.searchlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.adapter.SavedSummonerAdapter;
import com.example.searchlol.viewmodel.SavedSummonerViewModel;

import static com.example.searchlol.MainActivity.trigger;

public class SavedSummonerActivity extends AppCompatActivity implements SavedSummonerAdapter.OnNameItemClickListener {

    private SavedSummonerViewModel mViewModel;
    private SavedSummonerAdapter adapter;
    static Timer myTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_name);

        adapter = new SavedSummonerAdapter(this);

        RecyclerView savedReposRV = findViewById(R.id.rv_saved_name);
        savedReposRV.setLayoutManager(new LinearLayoutManager(this));
        savedReposRV.setHasFixedSize(true);

        savedReposRV.setAdapter(adapter);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
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
                Toast.makeText(SavedSummonerActivity.this, "Champion deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(savedReposRV);

    }

    @Override
    public void onNameItemClick(SummonerClass summonerClass) {
        //load summoner name
        SummonerSearchRepository newSearch = new SummonerSearchRepository("");
        newSearch.loadSearchResults(summonerClass.name);

        Intent intent = new Intent(this, LOLActivity.class);

        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (trigger == 1) {
                    startActivity(intent);
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