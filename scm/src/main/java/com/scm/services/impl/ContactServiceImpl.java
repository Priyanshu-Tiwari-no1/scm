package com.scm.services.impl;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.scm.Entities.Contact;
import com.scm.Entities.User;
import com.scm.helper.ResourcesNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        var contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found"));

        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with id " + id));
    }

    @Override
    public void delete(String id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with id " + id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page,
                                       String sortBy, String order, User user) {

        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (name == null || name.isEmpty()) {
            return contactRepo.findByUser(user, pageable);
        }

        return contactRepo.findByUserAndNameContainingIgnoreCase(user, name, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page,
                                        String sortBy, String order, User user) {

        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (email == null || email.isEmpty()) {
            return contactRepo.findByUser(user, pageable);
        }

        return contactRepo.findByUserAndEmailContainingIgnoreCase(user, email, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phone, int size, int page,
                                              String sortBy, String order, User user) {

        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (phone == null || phone.isEmpty()) {
            return contactRepo.findByUser(user, pageable);
        }

        return contactRepo.findByUserAndPhoneNumberContainingIgnoreCase(user, phone, pageable);
    }

    @Override
    public Page<Contact> getContacts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepo.findAll(pageable);
    }

@Override
public int countFavorites(User user) {
    return contactRepo.countByUserAndFavoriteTrue(user);
}


@Override
public int countRecent(User user) {
    LocalDateTime last7Days = LocalDateTime.now().minusDays(7);
    return contactRepo.countByUserAndCreatedAtAfter(user, last7Days);
}
@Override
public List<Contact> getContactsByUser(User user) {
    return contactRepo.findByUser(user);
}

}