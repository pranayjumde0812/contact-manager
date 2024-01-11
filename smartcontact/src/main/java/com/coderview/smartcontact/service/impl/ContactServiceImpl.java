package com.coderview.smartcontact.service.impl;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.repository.ContactRepo;
import com.coderview.smartcontact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    // Now Pagination work start
    // per page contact sow = 5 [n]
    // current page = 0 [page]
    @Override
    public Page<Contact> findContactByUser(long userId, Integer page) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Contact> contactsByUser = contactRepo.findContactsByUser(userId, pageable);
        return contactsByUser;
    }

    @Override
    public Contact findContactByContactId(long contactId) {

        Contact contactByContactId = contactRepo.findContactByContactId(contactId);

        return contactByContactId;
    }

    @Override
    public String deleteContact(Contact contact) {

        contactRepo.delete(contact);

        return "Contact Deleted Successfully";
    }

    @Override
    public List<Contact> findContactByNameContainingKeywords(String name, User user) {

        List<Contact> searchedContact = contactRepo.findByNameContainingAndUser(name, user);

        return searchedContact;
    }


//    public List<Contact> findContactByUser(long userId) {
//
//        List<Contact> contactListOfLoggedInUser = contactRepo.findContactsByUser(userId);
//        return contactListOfLoggedInUser;
//    }

}
