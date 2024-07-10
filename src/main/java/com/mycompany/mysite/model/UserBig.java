package com.mycompany.mysite.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBig extends User{

    public UserBig(User user) {
        this.setId( user.getId());
        this.setEmail( user.getEmail());
        this.setPassword( user.getPassword());
        this.setFirstName( user.getFirstName());
        this.setUsername( user.getUsername());
        this.setRole( user.getRole());
        this.setNotes(user.getNotes());
        this.setUser_info(user.getUser_info());
        try {
            this.setAge((String) user.getUser_info().get("Age"));
        }
        catch (Exception e) {
            this.setAge(null);
        }
        try {
            this.setGender((String) user.getUser_info().get("Gender"));
        }
        catch (Exception e) {
            this.setGender(null);
        }
        try {
            this.setLocation((String) user.getUser_info().get("Location"));
        }
        catch (Exception e) {
            this.setLocation(null);
        }
    }

    public String age;
    public String gender;
    public String location;
}
