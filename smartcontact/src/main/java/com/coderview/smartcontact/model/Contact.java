package com.coderview.smartcontact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Builder
@Table(name = "Contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactId")
    private long contactId;

    @Column(name = "Name")
    private String name;

    @Column(name = "SecondName")
    private String secondName;

    @Column(name = "Work")
    private String work;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "ImageUrl")
    private String imageUrl;

    @Column(name = "Description", length = 10000)
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;
}
