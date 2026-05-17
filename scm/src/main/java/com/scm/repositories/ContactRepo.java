package com.scm.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.Entities.Contact;
import com.scm.Entities.User;

public interface ContactRepo extends JpaRepository<Contact, String> {

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContainingIgnoreCase(User user, String name, Pageable pageable);

    Page<Contact> findByUserAndEmailContainingIgnoreCase(User user, String email, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContainingIgnoreCase(User user, String phoneNumber, Pageable pageable);

    int countByUserAndFavoriteTrue(User user);

    int countByUser(User user);

    List<Contact> findByUser(User user);

    int countByUserAndCreatedAtAfter(User user, LocalDateTime time);

}