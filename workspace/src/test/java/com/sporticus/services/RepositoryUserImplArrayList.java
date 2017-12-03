package com.sporticus.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.repositories.IRepositoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryUserImplArrayList implements IRepositoryUser {

	private long uniqueIndex = 01;
	private List<User> users = new ArrayList<>();

	public RepositoryUserImplArrayList() {

	}

	@Override
	public Iterable<User> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> found = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
		if (found.isPresent()) {
			return found.get();
		}
		return null;
	}

	@Override
	public User save(User entity) {
		if (findByEmail(entity.getEmail()) != null) {
			// TODO: throw an exception?
			return null;
		}
		users.add(entity);
		entity.setId(uniqueIndex++);
		return entity;
	}


	public <S extends User> Iterable<S> save(Iterable<S> entities) {
		entities.forEach(u -> save(u));
		return (Iterable<S>) findAll();
	}

	@Override
	public User findOne(Long aLong) {
		Optional<User> found = users.stream().filter(u -> u.getId().equals(aLong)).findFirst();
		if (found.isPresent()) {
			return found.get();
		}
		return null;
	}

	@Override
	public boolean exists(Long aLong) {
		return findOne(aLong) != null;
	}

	@Override
	public Iterable<User> findAll() {
		return () -> users.iterator();
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> longs) {
		return () -> users.iterator();
	}

	@Override
	public long count() {
		return users.size();
	}

	@Override
	public void delete(Long aLong) {
		User found = findOne(aLong);
		if (found != null) {
			users.remove(found);
		}
	}

	@Override
	public void delete(User entity) {
		users.remove(entity);
	}

	@Override
	public void delete(Iterable<? extends User> entities) {

	}

	@Override
	public void deleteAll() {
		users.clear();
	}
}
