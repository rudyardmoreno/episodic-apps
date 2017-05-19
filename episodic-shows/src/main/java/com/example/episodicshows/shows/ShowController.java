package com.example.episodicshows.shows;

import org.springframework.web.bind.annotation.*;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    public ShowController(ShowRepository showRepository, EpisodeRepository episodeRepository) {
        this.showRepository = showRepository;
        this.episodeRepository=episodeRepository;
    }

    @GetMapping("")
    public Iterable<Show> GetShows() {
        Iterable<Show> shows = this.showRepository.findAll();
        return shows;
    }

    @PostMapping("")
    public Show CreateUser(@RequestBody Show show) {
        return this.showRepository.save(show);
    }

    @GetMapping("/{id}/episodes")
    public Iterable<Episode> GetEpisodes(@PathVariable Long id) {
        Iterable<Episode> episodes = this.episodeRepository.findAllByShowId(id);
        return episodes;
    }

    @PostMapping("/{id}/episodes")
    public Episode CreateEpisode(@PathVariable Long id, @RequestBody Episode episode) {
        episode.setShowId(id);
        return episodeRepository.save(episode);
    }
}
