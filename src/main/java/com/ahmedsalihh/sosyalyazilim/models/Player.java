package com.ahmedsalihh.sosyalyazilim.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfBirth;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "player_team",
//            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")})
//    private Set<Team> teams = new HashSet<>();
}
