package com.manoj.autonest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manoj.autonest.model.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long>{
	
	@Query("SELECT f.id FROM Forum f JOIN f.likedBy u WHERE u.id = :userId")
    List<Long> findLikedForumsByUserId(@Param("userId") Long userId);
}
