package com.example.episodicevents.events.types;

import com.example.episodicevents.events.dataTypes.ForwardData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Rudyard Moreno on 5/22/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@Getter
@Setter
@NoArgsConstructor
public class FastForward extends Event {
    private ForwardData data;

    public FastForward(int userId, int showId, int episodeId, Date createdAt, ForwardData data) {
        super("fastForward", userId, showId, episodeId,createdAt);
        this.data = data;
    }
    public String getType() {
        return "fastForward";
    }
}
