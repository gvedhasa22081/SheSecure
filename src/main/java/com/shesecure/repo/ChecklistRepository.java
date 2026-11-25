package com.shesecure.repo;
import com.shesecure.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface ChecklistRepository extends JpaRepository<ChecklistProgress,Long>{
 List<ChecklistProgress> findByOwner(AppUser owner); Optional<ChecklistProgress> findByOwnerAndItemKey(AppUser owner,String key);
}
