package com.example.episodicshows.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */

@Entity(name ="viewings")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Viewing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long showId;
    private Long episodeId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date updatedAt;
    private int timecode;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getShowId() {
        return showId;
    }

    public Long getEpisodeId() {
        return episodeId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getTimecode() {
        return timecode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTimecode(int timeCode) {
        this.timecode = timeCode;
    }
}
