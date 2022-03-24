package io.github.caesiumfox.web4.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<User, String> {
    boolean existsById(String s);
    Optional<User> findById(String s);
    User save(User entity);
}
