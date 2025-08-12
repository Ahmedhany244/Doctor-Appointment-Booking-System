package com.global.hr.admin.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "admin")
public class Admin {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


  
    private String address;
    private String email; 
    private String phone; 

    public Admin(){}

    public Admin(String username, String password, String email, String address, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }


    


    public int getId() {
        return id;
    }

 


    public String getUsername() {
        return username;
    }


     
    public void setUsername(String username) {
        this.username = username;
    }

   
    public String getPassword() {
        return password;
    }

   
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }
 
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    
     
    public String getPhone() {
        return phone;
    }

   
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
