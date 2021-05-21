package com.api.psm.auth.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.psm.auth.db.entity.TbAuth;

public interface TbAuthRepository extends JpaRepository<TbAuth, Integer> {
	
}