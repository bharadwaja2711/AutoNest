package com.manoj.autonest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manoj.autonest.model.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long>{
	

}
