package com.app.JWTImplementation.repository;

import java.util.List;
import java.util.Optional;

import com.app.JWTImplementation.dto.projection.ServiceSpaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.JWTImplementation.model.ServiceSpa;

@Repository
public interface ServiceSpaRepository extends JpaRepository<ServiceSpa, Integer> {

    // Busca el servicio por su nombre
    List<ServiceSpa> findByNameAndCategoryName(String name, String categoryName);

    // Necesario para acceder a campos de la entidad relacionada
    @Query("""
    SELECT
        s.id as id,
        s.name as name,
        s.description as description,
        s.categoryName as categoryName,
        s.durationMinutes as durationMinutes,
        s.isActive as isActive,
        s.isGroupService as isGroup
    FROM ServiceSpa s
    """)
    List<ServiceSpaProjection> findAllServiceSpaWhitEntities();

    @Query("""
    SELECT
        s.id as id,
        s.name as name,
        s.description as description,
        s.categoryName as categoryName,
        s.durationMinutes as durationMinutes,
        s.isActive as isActive,
        s.isGroupService as isGroup
    FROM ServiceSpa s
    WHERE s.id = :id
    """)
    Optional<ServiceSpaProjection> findServiceSpaWhitEntity(@Param("id") Integer id);

}
