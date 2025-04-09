package com.example.CertificateDestributionApp.Repo;

import com.example.CertificateDestributionApp.Entity.CertificateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CertificateDataRepo extends JpaRepository<CertificateData,Long> {
    Optional<CertificateData> findByEmail(String email);
    Optional<CertificateData> findByName(String name);

    // Add these methods:
    List<CertificateData> findAllByEmail(String email);
    List<CertificateData> findAllByName(String name);

    List<CertificateData> findAllByNameIgnoreCase(String name);
    List<CertificateData> findAllByEmailIgnoreCase(String email);


}
