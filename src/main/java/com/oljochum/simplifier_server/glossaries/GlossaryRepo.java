package com.oljochum.simplifier_server.glossaries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlossaryRepo extends JpaRepository<Glossary, Long> {
    @Query("""
        SELECT g
        FROM Glossary g
        WHERE g.id = :glossaryId
        AND g.user.id = :userId
    """)
    Optional<Glossary> findByIdAndUserId(
        @Param("glossaryId") Long glossaryId,
        @Param("userId") Long userId
    );

    @Query("""
        SELECT g
        FROM Glossary g
        WHERE g.user.id = :userId
    """)
    List<Glossary> findByUserId(@Param("userId") Long userId);

    @Query("""
        DELETE
        FROM TermPair tp
        WHERE tp.id = :termPairId
    """)
    void deleteTermPairById(@Param("termPairId") Long termPairId);
}
