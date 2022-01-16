package ru.itmo.alkarized.lab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.alkarized.lab4.entities.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
}
