package dev.artsupplier.paintingtracker.repository;

import dev.artsupplier.paintingtracker.entity.SessionDetails;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Long> {
}
