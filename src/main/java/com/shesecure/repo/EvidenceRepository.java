package com.shesecure.repo;
import com.shesecure.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface EvidenceRepository extends JpaRepository<Evidence,Long>{
 List<Evidence> findByIncidentOrderByUploadedAtDesc(Incident incident);
 Optional<Evidence> findByIdAndIncident(Long id,Incident incident);
}
