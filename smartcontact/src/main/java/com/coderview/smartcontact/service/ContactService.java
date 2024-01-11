package com.coderview.smartcontact.service;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {

    // Now Pagination work start
    // per page contact sow = 5 [n]
    // current page = 0 [page]
    Page<Contact> findContactByUser(long userId, Integer page);

    Contact findContactByContactId(long contactId);

    String deleteContact(Contact contact);

    List<Contact> findContactByNameContainingKeywords(String name, User user);
}
