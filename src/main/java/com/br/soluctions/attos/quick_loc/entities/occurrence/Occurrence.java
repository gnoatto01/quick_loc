package com.br.soluctions.attos.quick_loc.entities.occurrence;

import java.time.Instant;

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

    @ManyToOne //varias ocorrencias estao relacionadas a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ocurrence_description")
    private String ocurrenceDescription;

    @Column(name = "responsible_name")
    private String responsibleName;

    @CreationTimestamp
    private Instant timeOfOccurence;

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

}
