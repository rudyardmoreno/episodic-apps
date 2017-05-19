package com.example.episodicshows.users;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;
    private final ViewingRepository viewingRepository;
    private final ShowRepository showRepository;

    public UserController(UserRepository userRepository, EpisodeRepository episodeRepository, ViewingRepository viewingRepository, ShowRepository showRepository) {
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
        this.viewingRepository=viewingRepository;
        this.showRepository=showRepository;
    }

    @GetMapping("")
    public Iterable<User> GetUsers() {
        Iterable<User> users = this.userRepository.findAll();
        return users;
    }

    @PostMapping("")
    public User CreateUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @PatchMapping("/{id}/viewings")
    public void UpdateViewing(@PathVariable Long id, @RequestBody Viewing viewing) {
        Episode episode = new Episode();
        Viewing viewingTmp = new Viewing();
        episode= episodeRepository.findOne(viewing.getEpisodeId());

        viewingTmp=viewingRepository.findByIds(id,episode.getShowId(),episode.getId());

        if (viewingTmp == null) { viewingTmp= new Viewing();};

        viewingTmp.setEpisodeId(episode.getId());
        viewingTmp.setShowId(episode.getShowId());
        viewingTmp.setUserId(id);
        viewingTmp.setUpdatedAt(viewing.getUpdatedAt());
        viewingTmp.setTimecode(viewing.getTimecode());

        viewingRepository.save(viewingTmp);
    }

    @GetMapping("/{id}/recently-watched")
    public List<RecentlyWatched> GetViewings(@PathVariable Long id) {
        List<RecentlyWatched> recentlyWatchedList = new ArrayList<RecentlyWatched>();
        Show show = new Show();
        Episode episode = new Episode();
        RecentlyWatched recentlyWatched;

        for (Viewing viewing : viewingRepository.findAllByUserIdOrderByUpdatedAtDesc(id)) {
            episode= episodeRepository.findOne(viewing.getEpisodeId());
            show = showRepository.findOne(viewing.getShowId());
            recentlyWatched = new RecentlyWatched(show,episode,viewing);
            recentlyWatchedList.add(recentlyWatched);
        }

        return recentlyWatchedList;
    }
}
