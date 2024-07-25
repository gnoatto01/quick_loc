package com.br.soluctions.attos.quick_loc.entities.occurrence;

import java.sql.Date;
import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.br.soluctions.attos.quick_loc.entities.users.User;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_occurrences")
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "occurrence_id")
    private Long occurrenceId;

    @ManyToOne // varias ocorrencias estao relacionadas a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "time_of_occurrence")
    private Instant timeOfOccurence;

    @Column(name = "ocurrence_description")
    private String ocurrenceDescription;

    @Column(name = "write_location")
    private String writeLocation;

    private String latitude;

    private String longitude;

    @Column(name = "occurrence_status")
    private String occurreceStatus;

    @Column(name = "occurrence_photo")
    private String occurrencePhoto;

    @Column(name = "occurrence_priority")
    private String occurrencePriority;

    @Column(name = "responsible_name")
    private String responsibleName;

    @Column(name = "resolution_date")
    private Date resolutionDate;

    @Column(name = "user_contat")
    private String userContat;

    @Column(name = "occurrence_font")
    private String occurrenceFont;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private boolean isActive;

    @PrePersist
    protected void onCreate() {

        if (occurreceStatus == null) {
            occurreceStatus = "Em andamento";
        }
        if(occurrencePhoto == null){
            occurrencePhoto = "-"; 
        }
        isActive = true; 
    }
    

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Long getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(Long occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOcurrenceDescription() {
        return ocurrenceDescription;
    }

    public void setOcurrenceDescription(String ocurrenceDescription) {
        this.ocurrenceDescription = ocurrenceDescription;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public Instant getTimeOfOccurence() {
        return timeOfOccurence;
    }

    public void setTimeOfOccurence(Instant timeOfOccurence) {
        this.timeOfOccurence = timeOfOccurence;
    }

    public String getWriteLocation() {
        return writeLocation;
    }

    public void setWriteLocation(String writeLocation) {
        this.writeLocation = writeLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOccurreceStatus() {
        return occurreceStatus;
    }

    public void setOccurreceStatus(String occurreceStatus) {
        this.occurreceStatus = occurreceStatus;
    }

    public String getOccurrencePhoto() {
        return occurrencePhoto;
    }

    public void setOccurrencePhoto(String occurrencePhoto) {
        this.occurrencePhoto = occurrencePhoto;
    }

    public String getOccurrencePriority() {
        return occurrencePriority;
    }

    public void setOccurrencePriority(String occurrencePriority) {
        this.occurrencePriority = occurrencePriority;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getUserContat() {
        return userContat;
    }

    public void setUserContat(String userContat) {
        this.userContat = userContat;
    }

    public String getOccurrenceFont() {
        return occurrenceFont;
    }

    public void setOccurrenceFont(String occurrenceFont) {
        this.occurrenceFont = occurrenceFont;
    }

}
