package com.kitsune.backend.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, UUID>, JpaSpecificationExecutor<Record> {

    @Query("""
            SELECT r FROM Record r
                WHERE r.video.id = :videoId
                    AND MOD(MINUTE(r.createdAt), :interval) = 0
                    AND r.createdAt >= :after
                    AND r.createdAt <= :before
            """)
    Page<Record> findRecordByInterval(String videoId, int interval, OffsetDateTime after, OffsetDateTime before, Pageable pageable);

}