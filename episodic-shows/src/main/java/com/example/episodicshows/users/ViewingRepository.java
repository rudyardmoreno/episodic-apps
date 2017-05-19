package com.example.episodicshows.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
public interface ViewingRepository extends CrudRepository<Viewing, Long> {
    @Query(value =
            "SELECT * " +
            "FROM   viewings " +
            "WHERE  user_id=?1 " +
            "AND    show_id=?2 " +
            "AND    episode_id=?3 " +
            "LIMIT 1 ",
            nativeQuery = true)
    Viewing findByIds(long userId, long showId, long episodeId);

    List<Viewing> findAllByUserId(long userId);
}