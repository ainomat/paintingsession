package dev.artsupplier.paintingtracker.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//entity for paint brand used in painting session
@Entity
public class Paint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto gen ID for each brand
    private Long id;

    private String name; //name: bloodred, cerulean, etc.
    private String colorCode; //color code: hex, rgb, etc.
    private String type; //type: acrylic, oil, watercolor, etc.
    private String brand; //brand: citadel, vallejo, etc.

    //many to many
    //many paints can be used in many sessions
    @ManyToMany(mappedBy = "paints")
    private Set<PaintingSession> sessions = new HashSet<>();
    //hashset stores unique values, so no duplicates
    // -> each session added only once, even if same paint is used in multiple sessions

    //getters and setters
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Set<PaintingSession> getSessions() {
        return sessions;
    }
    public void setSessions(Set<PaintingSession> sessions) {
        this.sessions = sessions;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColorCode() {
        return colorCode;
    }
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Brand: " + brand; //show name and brand in the combobox
    }

    //to add mock data to db
    public Paint() {
    }
    public Paint(String brand, String colorCode, String name, String type) {
        this.brand = brand;
        this.colorCode = colorCode;
        this.name = name;
        this.type = type;
    }
}
