package com.stackroute.trackservice.seeddata;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//creates Bean for this class
@Component

//provides .properties file of defined to application environment
@PropertySource("track.properties")

@ConfigurationProperties("track")
public class CommandLineRunnerSeedData implements CommandLineRunner {
    int trackId;
    String trackName;
    String comments;

   private TrackService trackService;

    @Autowired
    public CommandLineRunnerSeedData( TrackService trackService){
        this.trackService=trackService;

    }

    @Override
    public void run(String... args) throws Exception {
        Track track1=new Track(trackId,trackName,comments);
        Track track2=new Track(5,"Goodbyes","By Posty");
        Track track3=new Track(6,"Reverse","By G-Eazy");
        try {
            trackService.saveTrack(track1);
            trackService.saveTrack(track2);
            trackService.saveTrack(track3);
        }catch (TrackAlreadyExistsException ex){
            ex.printStackTrace();
        }

    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
