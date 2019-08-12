package com.stackroute.trackservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tracks")
//Generates no-arg constructor
@NoArgsConstructor
//Generates all-args constructor with all properties in the class
@AllArgsConstructor
//Generates Getter,Setter Methods,toString() and hashcode()
@Data
public class Track {
    @Id
    private int trackId;
    private String trackName;
    private String comments;
}
