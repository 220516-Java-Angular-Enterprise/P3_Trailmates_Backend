package com.revature.trailmates.trails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailRepository extends CrudRepository<Trail, String> {

    @Modifying
    @Query(value = "INSERT INTO trails (id, name, short_desc, long_desc, image_url, website_url, is_reservation_required, are_pets_permitted, do_fees_apply, duration, states, park_code) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12)", nativeQuery = true)
    void saveTrailName(String id, String name, String short_desc, String long_desc, String image_url, String website_url, Boolean isReservationRequired, Boolean arePetsPermitted, Boolean doFeesApply, String duration, String states, String parkCode);

    @Query(value = "SELECT name FROM trails", nativeQuery = true)
    List<String> getAllTrailNames();

    @Query(value = "SELECT * FROM trails", nativeQuery = true)
    List<Trail> getAllTrails();

}
