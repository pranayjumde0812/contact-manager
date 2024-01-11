package com.coderview.smartcontact.repository;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {

//    @Query("from Contact as c where c.user.userId = :userId")
//    List<Contact> findContactsByUser(@Param("userId") long userId);

    // Pagination
    // currentPage = page
    // contact per page = 5
    @Query("from Contact as c where c.user.userId = :userId")
    Page<Contact> findContactsByUser(@Param("userId") long userId, Pageable pageable);

    Contact findContactByContactId(long contactId);

    List<Contact> findByNameContainingAndUser(String name, User user);
}
