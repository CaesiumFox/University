package io.github.caesiumfox.web4.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {
    boolean existsById(String id);
    AuthToken save(AuthToken entity);
}
