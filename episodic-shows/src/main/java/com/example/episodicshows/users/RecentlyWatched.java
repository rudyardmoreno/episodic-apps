package com.example.episodicshows.users;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.Show;

import java.util.Date;


/**
 * Created by Rudyard Moreno on 5/18/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
public class RecentlyWatched {
    private Show show;
    private Episode episode;
    private Date updatedAt;
    private int timecode;

    public Show getShow() {
        return show;
    }

    public Episode getEpisode() {
        return episode;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getTimecode() {
        return timecode;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTimecode(int timecode) {
        this.timecode = timecode;
    }

    public RecentlyWatched(Show show, Episode episode, Viewing viewing) {
        this.show = show;
        this.episode = episode;
        this.updatedAt = viewing.getUpdatedAt();
        this.timecode=viewing.getTimecode();
    }
}
