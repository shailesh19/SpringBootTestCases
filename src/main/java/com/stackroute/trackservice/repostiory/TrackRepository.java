package com.stackroute.trackservice.repostiory;

import com.stackroute.trackservice.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track,Integer> {
    List<Track> findBy(String trackName);
}