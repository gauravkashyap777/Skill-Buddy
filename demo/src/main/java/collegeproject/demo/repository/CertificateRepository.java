package collegeproject.demo.repository;

import collegeproject.demo.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByUserIdOrderByCompletedAtDesc(Long userId);

    Optional<Certificate> findByUserIdAndSkill(Long userId, String skill);

    Optional<Certificate> findByCertificateId(String certificateId);
}
