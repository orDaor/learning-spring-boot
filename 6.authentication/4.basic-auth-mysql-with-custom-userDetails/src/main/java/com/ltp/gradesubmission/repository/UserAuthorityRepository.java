package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.entity.UserAuthority;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, UserAuthority> {

    Optional<List<UserAuthority>> findAllByUserDataUsername(String username);

}
