package edu.northeastern.tinyurl.repository;

import edu.northeastern.tinyurl.model.UrlMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
    @Transactional
    @Modifying
    @Query(value="delete from UrlMapping mapping where mapping.expiryDate < :now")
    void deleteExpiredUrlMapping(Date now);
}
