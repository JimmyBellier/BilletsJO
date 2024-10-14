package com.example.demo.dao;

import com.example.demo.entities.InfrastructureSportive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour gérer les opérations CRUD pour l'entité InfrastructureSportive.
 */
@Repository
public interface InfrastructureSportiveRepository extends CrudRepository<InfrastructureSportive, Long> {
}
