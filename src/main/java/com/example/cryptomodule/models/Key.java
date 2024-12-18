package com.example.cryptomodule.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "keys")
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Key() {}

    public Key(String keyValue, User user) {
        this.keyValue = keyValue;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(id, key.id) &&
                Objects.equals(keyValue, key.keyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyValue);
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
