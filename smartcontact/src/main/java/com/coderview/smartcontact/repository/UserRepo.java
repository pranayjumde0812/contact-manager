package com.coderview.smartcontact.repository;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query(" select u from User u where u.email = :email")
    User getUserByUserName(@Param("email") String email);
}
