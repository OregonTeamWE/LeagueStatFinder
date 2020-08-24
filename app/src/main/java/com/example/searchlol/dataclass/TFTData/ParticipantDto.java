package com.example.searchlol.dataclass.TFTData;

import java.io.Serializable;
import java.util.List;

public class ParticipantDto implements Serializable {
    public int level;
    public int placement;
    public String puuid;
    public List<units> units;
    public CompanionDto companion;
}
