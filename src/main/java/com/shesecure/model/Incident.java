package com.shesecure.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class Incident {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false) private String category;
 private String platform;
 @Column(nullable=false) private LocalDate incidentDate;
 @Column(length=5000) private String description;
 private LocalDateTime createdAt=LocalDateTime.now();
 @ManyToOne(optional=false) private AppUser owner;
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getPlatform(){return platform;} public void setPlatform(String v){platform=v;}
 public LocalDate getIncidentDate(){return incidentDate;} public void setIncidentDate(LocalDate v){incidentDate=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
}
