package dev.artsupplier.paintingtracker.repository;

import dev.artsupplier.paintingtracker.entity.Canvas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanvasRepository extends JpaRepository<Canvas, Long> {

}
