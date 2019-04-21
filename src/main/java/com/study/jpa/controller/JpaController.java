package com.study.jpa.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.vo.QUser;
import com.study.jpa.vo.User;

/**
 * 기초 CRUD
 * http://www.querydsl.com/static/querydsl/3.4.3/reference/ko-KR/html_single 참조!!
 * */

@RestController
@Transactional
@RequestMapping("/jpa")
public class JpaController {
	
	@Autowired
    private EntityManager em;
	
    @Autowired
    private JPAQueryFactory jPAQueryFactory;
    
	@GetMapping("/list")
	public List<User> list() {
		final QUser qUser = QUser.user;
		
		List<User> list =
			jPAQueryFactory.selectFrom(qUser).innerJoin(qUser)
					//.on(qUser.id.eq(xxxx.key1), qUser.email.eq(xxxx.key2))
					//.innerJoin(target)
					//.where(qUser.firstName.eq("kim"))
				.fetch();
		
		return list;
	}
	
	@GetMapping("/details")
	public User details() {
		final QUser qUser = QUser.user;
		
		User user =
				jPAQueryFactory.selectFrom(qUser).where(qUser.firstName.eq("kim")).fetchOne();
		
		return user;
	}
	
	/*
	 * @PostMapping public void insert() { em.persist( User.builder()
	 * .firstName("lee") .lastName("saein") .email("saein-lee@gmail.com")
	 * .password("3333") .build() ); }
	 */
	
	@PutMapping
	public long update() {
		final QUser qUser = QUser.user;
		
		long result =
				jPAQueryFactory.update(qUser).set(qUser.password, "2222").where(qUser.firstName.eq("kim")).execute();
		
		return result;
	}
}
