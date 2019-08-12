package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.repostiory.TrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TrackServiceImplTest {
    private Track track;
    //Creates a mock repository for UserRepository
    @Mock
    private TrackRepository trackRepository;
    //Injects the mocks as dependencies into TrackServiceImpl class
    @InjectMocks
    private TrackServiceImpl trackService;
    private List<Track> list;

    @Before
    public void setUp() {
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setTrackName("Go Flex");
        track.setComments("DailyMix");
        track.setTrackId(555);
        list = new ArrayList<>();
        list.add(track);
    }

    @After
    public void teardown() {
        track = null;
        list = null;
    }

    @Test
    public void givenTrackToSaveShouldReturnSavedTrack() throws TrackAlreadyExistsException {
        when(trackRepository.save((Track) any())).thenReturn(track);
        Track resultTrack = trackService.saveTrack(track);
        assertEquals(track, resultTrack);
        //verify here verifies that trackRepository save method is only called once
        verify(trackRepository, times(1)).existsById(track.getTrackId());
        verify(trackRepository, times(1)).save(track);
    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void givenAlreadyExistingTrackShouldThrowTrackAlreadyExistsException() throws TrackAlreadyExistsException {
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        Track resultTrack = trackService.saveTrack(track);
    }

    @Test
    public void givenInputShouldReturnAllTracks() throws Exception {
        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
        Assert.assertEquals(list, tracklist);
    }

    @Test(expected = TrackNotFoundException.class)
    public void givenTrackIdShouldReturnTrackNotFoundException() throws TrackNotFoundException {

        //stubbing the mock to return specific data
        when(trackRepository.existsById(track.getTrackId())).thenReturn(false);
        Track resultTrack = trackService.getTrackById(track.getTrackId());
    }

    @Test
    public void givenTrackIdShouldReturnDeletedTrack() throws TrackNotFoundException, Exception {
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.of(track));
        Track savedTrack = trackService.deleteTrackById(track.getTrackId()).get();
        Assert.assertEquals(track, savedTrack);
    }

    @Test(expected = Exception.class)
    public void givenTrackIdShouldReturnServerException() throws TrackAlreadyExistsException, Exception {
        when(trackRepository.existsById(anyInt())).thenReturn(true);
        when(trackRepository.findById(any())).thenThrow(Exception.class);
        trackService.deleteTrackById(track.getTrackId());
    }

    @Test
    public void givenTrackIdShouldReturnUpdatedTrack() throws TrackNotFoundException, Exception {
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.of(track));
        Track savedTrack = trackService.deleteTrackById(track.getTrackId()).get();
        Assert.assertEquals(track, savedTrack);
    }
}
