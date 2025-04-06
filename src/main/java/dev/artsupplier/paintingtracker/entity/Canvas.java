package dev.artsupplier.paintingtracker.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Canvas {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto gen ID for each brush
    private Long id; //id for brush

    private String material; //cotton, wood, paper etc.
    private String size; //A4, 16x20 etc.
    private String primed; //primed yes or no

    //one canvas -> many sessions, one session -> one canvas
    @OneToMany(mappedBy = "canvas")
    private Set<PaintingSession> sessions = new HashSet<>(); //setting sessions where the canvas

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Set<PaintingSession> getSessions() {
        return sessions;
    }
    public void setSessions(Set<PaintingSession> sessions) {
        this.sessions = sessions;
    }

    public String getPrimed() {
        return primed;
    }
    public void setPrimed(String primed) {
        this.primed = primed;
    }

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }

    //to add mock data to db
    public Canvas() {
    }
    public Canvas(String material, String size, String primed) {
        this.material = material;
        this.size = size;
        this.primed = primed;
    }

}
