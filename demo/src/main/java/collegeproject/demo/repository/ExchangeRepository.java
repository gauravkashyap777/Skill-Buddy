package collegeproject.demo.repository;

import collegeproject.demo.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findByUserIdOrderByCreatedAtDesc(Long userId);
}
