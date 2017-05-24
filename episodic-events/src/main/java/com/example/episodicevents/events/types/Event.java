package com.example.episodicevents.events.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Play.class, name = "play"),
        @JsonSubTypes.Type(value = Pause.class, name = "pause"),
        @JsonSubTypes.Type(value = FastForward.class, name = "fastForward"),
        @JsonSubTypes.Type(value = Rewind.class, name = "rewind"),
        @JsonSubTypes.Type(value = Progress.class, name = "progress"),
        @JsonSubTypes.Type(value = Scrub.class, name = "scrub")
})
public class Event {
    private String type;
    private int userId;
    private int showId;
    private int episodeId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date createdAt;

    public String getType() {
        return "anyEvent";
    }
}
