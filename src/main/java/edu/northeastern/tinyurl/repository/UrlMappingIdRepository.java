package edu.northeastern.tinyurl.repository;

import edu.northeastern.tinyurl.model.UrlMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingIdRepository extends JpaRepository<UrlMappingId, Long> {
}
