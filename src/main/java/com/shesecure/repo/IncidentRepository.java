package com.shesecure.repo;
import com.shesecure.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface IncidentRepository extends JpaRepository<Incident,Long>{
 List<Incident> findByOwnerOrderByIncidentDateDesc(AppUser owner);
 Optional<Incident> findByIdAndOwner(Long id,AppUser owner);
 long countByOwner(AppUser owner);
}
