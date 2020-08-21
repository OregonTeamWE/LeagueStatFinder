package com.example.searchlol.dataclass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class FreeChampionInfo implements Serializable {
        @PrimaryKey
        @NonNull
        public List<Integer> freeChampionIds;
}


