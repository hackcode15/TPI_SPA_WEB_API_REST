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

    Optional<ServiceSpa> findByName(String name);

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

    // En ServiceSpaRepository
    @Query("SELECT s.name FROM ServiceSpa s WHERE s.id = :id")
    Optional<String> findServiceNameById(@Param("id") Integer id);

    @Query("SELECT s.durationMinutes FROM ServiceSpa s WHERE s.id = :id")
    Optional<Integer> findDurationMinutesById(@Param("id") Integer id);

    @Query("SELECT s.isGroupService FROM ServiceSpa s WHERE s.id = :id")
    Boolean findIsGroupServiceById(@Param("id") Integer id);

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
            WHERE s.categoryName = :category
            """)
    List<ServiceSpaProjection> findAllServicesByCategoryName(@Param("category") String category);


}
