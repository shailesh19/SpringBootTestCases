package com.stackroute.trackservice.repostiory;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;
    private Track track;
    private Track trackToUpdate;

    @Before
    public void setUp() {
        track = new Track();
        track.setTrackId(10);
        track.setTrackName("John");
        track.setComments("test1");
        trackToUpdate = new Track();
        trackToUpdate.setTrackId(10);
        trackToUpdate.setTrackName("Johnson");
        trackToUpdate.setComments("testupdate");
    }

    @After
    public void tearDown() {
        track = null;
        trackToUpdate = null;
        trackRepository.deleteAll();
    }

    @Test
    public void givenTrackIdShouldReturnTheTrackDetails() {
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
        Assert.assertEquals(10, fetchTrack.getTrackId());
    }

    @Test
    public void givenTrackIdShouldReturnSaveTrackFailure() {
        Track testTrack = new Track(189, "Wow", "Fav");
        trackRepository.save(testTrack);
        Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
        Assert.assertNotSame(testTrack, fetchTrack);
    }


    @Test
    public void givenInputShouldReturnAllTracks() {
        Track track1 = new Track(456, "Celebrate", "DailyMix");
        Track track2 = new Track(783, "Champion", "DailyMix");
        trackRepository.save(track1);
        trackRepository.save(track2);
        List<Track> testTracks = new ArrayList<>();
        testTracks.add(track1);
        testTracks.add(track2);
        List<Track> outputList = trackRepository.findAll();
        Assert.assertEquals(testTracks, outputList);
    }


    @Test
    public void givenTrackIdShouldReturnDeletedTrack() {
        trackRepository.save(track);
        trackRepository.delete(track);
        Optional<Track> expected = Optional.empty();
        Assert.assertEquals(expected, trackRepository.findById(track.getTrackId()));
    }


    @Test
    public void givenTrackIdShouldReturnUpdatedTrack() {
        trackRepository.save(track);
        Track updatedTrack = trackRepository.findById(trackToUpdate.getTrackId()).get();
        updatedTrack.setTrackName(trackToUpdate.getTrackName());
        updatedTrack.setComments(trackToUpdate.getComments());
        Track expected = trackToUpdate;
        Track foundTrack = trackRepository.save(trackToUpdate);
        Assert.assertEquals(expected, foundTrack);
    }
}




