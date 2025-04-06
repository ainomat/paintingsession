package dev.artsupplier.paintingtracker.entity;

import jakarta.persistence.*;

@Entity
public class SessionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id is needed for easier connection to other tables

    @Column(length = 255)
    private String notes; //notes about the session, what was painted, what paints were used, etc.

    private Integer duration; //duration of the session in minutes (like for tracking worked hours)

    @OneToOne
    @JoinColumn(name = "session_id") //foreign key to the session table
    private PaintingSession session; //session object, one-to-one relationship with the session table

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public PaintingSession getSession() {
        return session;
    }
    public void setSession(PaintingSession session) {
        this.session = session;
    }

    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
