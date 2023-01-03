package kr.co.auiot.common.dto.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	// user interface
	// to do 
	User getByUserId(String userId);
	
	@Nullable
	User findByUserId(@Nullable String userId);
	Optional<User> findOptionalByUserId(String userId);
}
