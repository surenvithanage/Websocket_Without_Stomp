package com.ulpatha.web.chat.repository;

import com.ulpatha.web.chat.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserId(long id);

    User findByEmail(String email);

    @Query(" FROM User u WHERE u.email IS NOT :excludedUser")
    List<User> findFriendsListFor(@Param("excludedUser") String excludedUser);
}
