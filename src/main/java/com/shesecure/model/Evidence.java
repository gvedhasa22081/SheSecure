package com.shesecure.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity
public class Evidence {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String originalName; private String contentType; private String storageKey; private LocalDateTime uploadedAt=LocalDateTime.now();
 @ManyToOne(optional=false) private Incident incident;
 public Long getId(){return id;} public String getOriginalName(){return originalName;} public void setOriginalName(String v){originalName=v;}
 public String getContentType(){return contentType;} public void setContentType(String v){contentType=v;}
 public String getStorageKey(){return storageKey;} public void setStorageKey(String v){storageKey=v;}
 public LocalDateTime getUploadedAt(){return uploadedAt;} public Incident getIncident(){return incident;} public void setIncident(Incident v){incident=v;}
}
