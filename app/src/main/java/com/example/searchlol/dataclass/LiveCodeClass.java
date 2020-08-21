package com.example.searchlol.dataclass;

import java.io.Serializable;
import java.util.List;

public class LiveCodeClass implements Serializable {
    public int id;
    public int themeId;
    public String nameKey;
    public String nameKeySecondary;
    public List<TournamentPhaseDto> schedule;
}
