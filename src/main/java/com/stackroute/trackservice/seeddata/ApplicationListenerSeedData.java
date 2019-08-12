package com.stackroute.trackservice.seeddata;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.trackservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListenerSeedData implements ApplicationListener<ContextRefreshedEvent> {
    //   TrackService to perform operations on DB.
    private TrackService trackService;

    @Value("${trackId}")
    int id;

    @Autowired
    public Environment environment;
    @Autowired
    public ApplicationListenerSeedData( TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Track track1=new Track(id,environment.getProperty("trackName"),environment.getProperty("comments"));
        Track track2=new Track(2,"GoodByes","By Posty");
        Track track3=new Track(3,"Reverse","By G-Eazy");
        try {
            trackService.saveTrack(track1);
            trackService.saveTrack(track2);
            trackService.saveTrack(track3);
        }catch (TrackAlreadyExistsException ex){
            ex.printStackTrace();
        }
    }
}
