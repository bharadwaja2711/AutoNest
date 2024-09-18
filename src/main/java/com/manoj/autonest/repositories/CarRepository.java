package com.manoj.autonest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manoj.autonest.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
