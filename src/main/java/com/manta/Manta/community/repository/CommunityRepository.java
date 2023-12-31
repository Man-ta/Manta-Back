package com.manta.Manta.community.repository;

import com.manta.Manta.community.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CommunityRepository extends JpaRepository<Board, Long> {

    @Query("select new map ( b.bid as bid, b.title as title ) from Board b")
    Page<Board> findListB5(Pageable pageable);

    @Query("select b from Board b where b.title like %?1%")
    List<Board> findList(String title, Sort sort);

    @Query("select new map ( b.bid as bid, b.title as title, b.userName as userName, b.hit as hit, b.writeDt as writeDt ) from Board b where b.title like %?1%")
    Page<Board> findListNonCnWithPage(String title, Pageable pageable);

    @Query("select b from Board b where b.bid = ?1")
    List<Board> findDetail(Long bid);

    @Transactional
    @Modifying
    @Query("update Board b set b.hit = b.hit+1 where b.bid = ?1")
    int updateHit(Long bid);
}
