package com.kitsune.backend.entity;

import com.kitsune.backend.model.VideoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String>, JpaSpecificationExecutor<Video> {
    List<Video> findVideosByStatusIn(List<VideoStatus> statuses);

    @Query("""
                SELECT v FROM Video v
                WHERE
                    v.status IN :statuses
                    AND v.startAt <= CURRENT_TIMESTAMP
            """)
    List<Video> findRunningVideos(List<VideoStatus> statuses);
}
