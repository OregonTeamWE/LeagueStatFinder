package com.example.searchlol.dataclass.TFTData;

import java.io.Serializable;
import java.util.List;

public class info implements Serializable {
    public long game_datetime;
    public float game_length;
    public String game_version;
    public List<ParticipantDto> participants;
}
