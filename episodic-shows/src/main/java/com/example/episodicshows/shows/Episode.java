package com.example.episodicshows.shows;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */

@Entity(name ="episodes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long showId;

    private int seasonNumber;

    private int episodeNumber;

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Long getShowId() {
        return showId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    @Transient
    public String getTitle() {
        return "S" + getSeasonNumber() + " E" + getEpisodeNumber();
    }

    public void setId(Long value) {id = value;}

    public void setShowId(Long value) {
        this.showId = value;
    }

    public void setSeasonNumber(int value) {
        this.seasonNumber = value;
    }

    public void setEpisodeNumber(int value) {
        this.episodeNumber = value;
    }

}
