package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.repostiory.TrackRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("MainService")
public class TrackServiceImpl implements TrackService {
    TrackRepository trackRepository;

    public TrackServiceImpl() {
    }

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if (trackRepository.existsById(track.getTrackId())) {
            throw new TrackAlreadyExistsException("Track already exists!");
        } else {
            return trackRepository.save(track);

        }

    }

    @Override
    public Track getTrackById(int id) throws TrackNotFoundException {
        Track foundTrack = null;
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Track not available");
        } else {
            foundTrack = trackRepository.findById(id).get();
            if (foundTrack == null) {
                throw new TrackNotFoundException("Track is null");
            }
        }
        return foundTrack;
    }

    @Override
    public Optional<Track> deleteTrackById(int id) throws TrackNotFoundException {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Track does not exist");
        }
        Optional<Track> optionalTrack = trackRepository.findById(id);
        trackRepository.delete(optionalTrack.get());
        return optionalTrack;

    }

    @Override
    public List<Track> getAllTracks() throws Exception {
        if (trackRepository.findAll().isEmpty()) {
            throw new Exception();
        }
        return trackRepository.findAll();

    }

    @Override
    public Track updateTrack(Track trackToUpdate) throws TrackNotFoundException {
        if (trackRepository.existsById(trackToUpdate.getTrackId())) {
            Optional<Track> optionalTrack = trackRepository.findById(trackToUpdate.getTrackId());
            if (optionalTrack.isPresent()) {
                Track updatedTrack = optionalTrack.get();
                updatedTrack.setTrackName(trackToUpdate.getTrackName());
                updatedTrack.setComments(trackToUpdate.getComments());
                return trackRepository.save(trackToUpdate);
            }
        }
        throw new TrackNotFoundException();
    }


    @Override
    public List<Track> selectTrackByName(String trackname) throws TrackNotFoundException {

        List<Track> foundTracks = trackRepository.findBy(trackname);
        if (foundTracks.isEmpty()) {
            throw new TrackNotFoundException("No Track exists");
        }

        return foundTracks;
}

}
