package com.example.CertificateDestributionApp.Entity;

import jakarta.persistence.*;

@Entity
public class CertificateData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CertificateData_sequence")
    @SequenceGenerator(name = "CertificateData_seq")
    private Long id;

    public String email;
    public String name;
    public String type;

    public CertificateData() {
    }

    public CertificateData(Long id, String email, String name, String type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = type;
    }

    public CertificateData(String email, String name, String type) {
        this.email = email;
        this.name = name;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CertificateData{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
