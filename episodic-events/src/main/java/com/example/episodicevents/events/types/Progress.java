package com.example.episodicevents.events.types;

import com.example.episodicevents.events.dataTypes.DataDefault;
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
public class Progress extends Event {
    private DataDefault data;

    public Progress(int userId, int showId, int episodeId, Date createdAt, DataDefault data) {
        super("progress", userId, showId, episodeId,createdAt);
        this.data = data;
    }
    public String getType() {
        return "progress";
    }
}
