package dev.artsupplier.paintingtracker.repository;

import dev.artsupplier.paintingtracker.entity.PaintingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingSessionRepository extends JpaRepository<PaintingSession, Long> {
    //interface that extends JpaRepository, which does CRUD operations for the entity

    //later: findAll(), save(), deleteById(), findById()


}
