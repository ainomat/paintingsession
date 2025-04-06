package dev.artsupplier.paintingtracker.repository;

import dev.artsupplier.paintingtracker.entity.Paint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintRepository extends JpaRepository<Paint, Long> {
}
