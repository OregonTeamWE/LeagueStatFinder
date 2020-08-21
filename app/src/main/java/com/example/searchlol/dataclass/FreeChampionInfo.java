package com.example.searchlol.dataclass;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import java.util.List;

public class FreeChampionInfo implements Serializable {
        @PrimaryKey
        @NonNull
        public List<Integer> freeChampionIds;
}


