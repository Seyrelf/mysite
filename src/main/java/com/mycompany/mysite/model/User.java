package com.mycompany.mysite.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.aspectj.weaver.ast.Not;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User{

    public User(UserBig userBig){
        this.id = userBig.getId();
        this.username = userBig.getUsername();
        this.password = userBig.getPassword();
        this.email = userBig.getEmail();
        this.notes = userBig.getNotes();
        this.role = userBig.getRole();
        this.user_info=userBig.getUser_info();
        this.firstName=userBig.getFirstName();
        this.user_info.put("Gender",userBig.getGender());
        this.user_info.put("Age",userBig.getAge());
        this.user_info.put("Location",userBig.getLocation());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_info")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> user_info = new HashMap<>();
    @Column(name = "username",unique = true,nullable = false)
    private String username;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "role")
    private String role = "ROLE_USER";
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Note> notes = new ArrayList<Note>();

    public void addNote(Note note){
        notes.add(note);
        note.setAuthor(this);
    }

    public void removeNote(Note note){
        notes.remove(note);
        note.setAuthor(null);
    }

    @Transactional
    public void removeNotesFromUsersProfile(Note note){
        this.removeNote(note);
    }
}
