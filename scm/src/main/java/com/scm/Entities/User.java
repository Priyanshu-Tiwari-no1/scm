package com.scm.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user") 
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Lob//hibernate automatically MySQL me LONGTEXT ya TEXT column bana dega.
    private String about;

    @Lob
    private String profilePic;

    private String phoneNum;

    private boolean enabled = false;
    private boolean emailVarified = false;
    private boolean phoneVarified = false;

    @Enumerated(EnumType.STRING)
    private Providers provider = Providers.SELF;

    private String providerUserId;
}
