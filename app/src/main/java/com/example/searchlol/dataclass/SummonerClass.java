package com.example.searchlol.dataclass;

import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "summoners")
public class SummonerClass implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;

    public long summonerLevel;
    public String accountId;

    public int profileIconId;
    public long revisionDate;
}
