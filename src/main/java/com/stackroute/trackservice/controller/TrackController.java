package com.stackroute.trackservice.controller;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TrackController {
    TrackService trackService;
    @Autowired
    public TrackController( TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track)throws TrackAlreadyExistsException {

            Track savedTrack=trackService.saveTrack(track);
            return new ResponseEntity<>(savedTrack, HttpStatus.CREATED);
    }

    @GetMapping("track/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable int id) throws TrackNotFoundException
    {
            Track foundTrackById = trackService.getTrackById(id);
            return new ResponseEntity<>(foundTrackById, HttpStatus.FOUND);
    }

    @DeleteMapping("track")
    public ResponseEntity<?> deleteTrackById(@RequestParam int id)throws TrackNotFoundException {

            Track deletedTrack=trackService.deleteTrackById(id).get();
            return new ResponseEntity<>(deletedTrack,HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("track")
    public ResponseEntity<?> getAllTracks()throws Exception {

            List<Track> listOfTracks=trackService.getAllTracks();
            return new ResponseEntity<>(listOfTracks,HttpStatus.OK);
    }

    @PutMapping("track")
    public ResponseEntity<?> updateTrack( @RequestBody Track trackToUpdate) throws TrackNotFoundException{

            Track updatedTrack=trackService.updateTrack(trackToUpdate);
            return new ResponseEntity<>(updatedTrack,HttpStatus.OK);
    }

    @GetMapping("tracks/{trackName}")
    public ResponseEntity<?> selectTrackByName(@PathVariable String trackName) throws TrackNotFoundException{

            List<Track> foundTracks=trackService.selectTrackByName(trackName);
            return new ResponseEntity<>(foundTracks,HttpStatus.FOUND);
    }
}
