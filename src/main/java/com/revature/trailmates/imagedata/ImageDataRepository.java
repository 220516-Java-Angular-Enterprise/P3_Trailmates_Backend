package com.revature.trailmates.imagedata;

import com.revature.trailmates.trailflag.TrailFlag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImageDataRepository extends CrudRepository<ImageData, String> {
    @Query(value="select * from imagedata where imagedata.timestamp = (select max(imagedata.timestamp) from imagedata where user_id = ?1 and filetype = 'PROFILE')", nativeQuery =true)
    Optional<List<ImageData>> getLatestPPByUserId(String userId);
}
