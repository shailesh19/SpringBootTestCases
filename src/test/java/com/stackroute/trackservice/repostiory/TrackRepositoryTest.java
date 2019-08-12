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
        public void setUp()
        {
            track = new Track();
            track.setTrackId(10);
            track.setTrackName("John");
            track.setComments("test1");
            trackToUpdate=new Track();
            trackToUpdate.setTrackId(10);
            trackToUpdate.setTrackName("Johnson");
            trackToUpdate.setComments("testupdate");


        }

        @After
        public void tearDown(){
            track=null;
            trackToUpdate=null;
            trackRepository.deleteAll();
        }

//Test for savetrack
        @Test
        public void givenTrackIdShouldFetchTheRespectiveTrack(){
            trackRepository.save(track);
            Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
            Assert.assertEquals(10,fetchTrack.getTrackId());

        }

//        Test for saveTrackFailure
        @Test
        public void GivenTrackIdShouldShowSaveTrackFailure(){
            Track testTrack = new Track(10,"anil","comment");
            trackRepository.save(testTrack);
            Track fetchTrack= trackRepository.findById(track.getTrackId()).get();
            Assert.assertNotSame(testTrack,fetchTrack);
        }

//        Test for getAllTtracks
        @Test
        public void testShouldReturnAllTracks() {
            Track track1 = new Track(12,"vadi pulla","comments for vadi pulla");
            Track track2 = new Track(13,"kanamma","comments for kanamma");
            trackRepository.save(track1);
            trackRepository.save(track2);
            List<Track> testTracks= new ArrayList<>();
            testTracks.add(track1);
            testTracks.add(track2);

            List<Track> outputList = trackRepository.findAll();
            Assert.assertEquals(testTracks,outputList);
        }

//        Test for deleteTrack
        @Test
        public void GivenTrackIdShouldDeletetrackAndReturnNull(){
            trackRepository.save(track);
            trackRepository.delete(track);
            Optional<Track> expected=Optional.empty();

            Assert.assertEquals(expected,trackRepository.findById(track.getTrackId()));
        }

//        Test for updatingTrack
    @Test
    public void GivenTrackWithSameIdShouldUpdatedTrackOfThatId(){
        trackRepository.save(track);
        Track updatedTrack = trackRepository.findById(trackToUpdate.getTrackId()).get();
        updatedTrack.setTrackName(trackToUpdate.getTrackName());
        updatedTrack.setComments(trackToUpdate.getComments());
        Track expected=trackToUpdate;
        Track foundTrack=trackRepository.save(trackToUpdate);

        Assert.assertEquals(expected,foundTrack);
    }



}




