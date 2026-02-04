package com.oljochum.simplifier_server.sessions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {
    @Query("""
        SELECT s
        FROM Session s
        WHERE s.id = :sessionId
        AND s.user.id = :userId
    """)
    Optional<Session> findByIdAndUserId(
        @Param("sessionId") Long sessionId,
        @Param("userId") Long userId
    );

    @Query("""
        SELECT s
        FROM Session s
        WHERE s.user.id = :userId
    """)
    List<Session> findByUserId(@Param("userId") Long userId);
}
