package com.example.episodicshows.shows;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
public interface EpisodeRepository extends CrudRepository<Episode, Long> {
    @Query(value =
            "SELECT * " +
            "FROM   episodes " +
            "WHERE  show_id=?1 ",
            nativeQuery = true)
    List<Episode> findAllByShowId(long id);

}