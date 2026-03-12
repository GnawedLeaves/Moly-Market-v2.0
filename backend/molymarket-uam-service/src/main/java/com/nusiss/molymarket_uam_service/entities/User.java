package com.nusiss.molymarket_uam_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    private Double balance;

    @Override
    public String toString() {
        return "User{" +
                "id=" + (id != null ? id : "null") +
                ", username='" + (username != null ? username : "null") + '\'' +
                ", role='" + (role != null ? role : "null") + '\'' +
                ", balance=" + (balance != null ? balance : "null") +
                '}';
    }
}
