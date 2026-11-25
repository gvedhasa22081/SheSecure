package com.shesecure.model;
import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"owner_id","itemKey"}))
public class ChecklistProgress {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private AppUser owner;
 @Column(nullable=false) private String itemKey;
 private boolean completed;
 public Long getId(){return id;} public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
 public String getItemKey(){return itemKey;} public void setItemKey(String v){itemKey=v;}
 public boolean isCompleted(){return completed;} public void setCompleted(boolean v){completed=v;}
}
