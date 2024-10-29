package com.example.casinomoney.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Ensure the entity maps to the correct table name in your database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to the SERIAL type in PostgreSQL
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "money_amount") // Explicitly map the 'money_amount' column
    private Integer moneyAmount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
