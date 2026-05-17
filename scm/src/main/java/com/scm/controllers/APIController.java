package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scm.Entities.Contact;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class APIController {

    // get contact

    @Autowired
    private ContactService contactService;

@GetMapping("/{contactId}")
@ResponseBody
public Contact getContact(@PathVariable String contactId) {
    return contactService.getById(contactId);
}


}