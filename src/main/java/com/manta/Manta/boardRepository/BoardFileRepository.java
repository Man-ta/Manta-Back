package com.manta.Manta.boardRepository;

import com.manta.Manta.boardEntity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
