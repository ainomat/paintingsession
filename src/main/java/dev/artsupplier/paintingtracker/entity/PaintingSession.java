package dev.artsupplier.paintingtracker.entity;


import com.vaadin.flow.component.textfield.NumberField;
import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//a single painting session: what was painted, when, paints, paintbrand, brushes
@Entity
public class PaintingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generae ID automatically, uses the database id

    private Long id;//id for session
    private String title; //title of the session/painting
    private LocalDate date; //date of the session

    //connec to other tables
    //OneToOne
    @OneToOne(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private SessionDetails sessionDetails;

    //ManyToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "session_paint",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "paint_id")
    )
    private Set<Paint> paints = new HashSet<>(); //setting paints used in session

    //Many sessions, only one canvas
    @ManyToOne
    @JoinColumn(name = "canvas_id")//foreign key to the canvas table
    private Canvas canvas; //canvas used in the session

    //getsetters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Canvas getCanvas() {
        return canvas;
    }
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Set<Paint> getPaints() {
        return paints;
    }
    public void setPaints(Set<Paint> paints) {
        this.paints = paints;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }
    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }
}
