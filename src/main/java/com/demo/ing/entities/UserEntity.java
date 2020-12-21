package com.demo.ing.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    public UserEntity() {
    }

    public UserEntity(String name, Integer ssn, String iban) {
        this.name = name;
        this.ssn = ssn;
        this.iban = iban;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "iban")
    private String iban;

    /**
     * Social security number ( cnp )
     */
    @Column(name = "ssn", nullable = false, unique = true)
    private Integer ssn;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getSsn() {
        return ssn;
    }

    public void setSsn(Integer ssn) {
        this.ssn = ssn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", iban='" + iban + '\'' +
                ", ssn=" + ssn +
                '}';
    }
}
