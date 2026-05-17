package com.scm.Entities;


import java.util.*;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String cloudinaryImagePublicId;
    @Column(length = 1000)
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;

    // Many contacts belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Social Links
    @OneToMany(
        mappedBy = "contact",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<SocialLink> links = new ArrayList<>();

    private LocalDateTime createdAt;

@jakarta.persistence.PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
}
}
