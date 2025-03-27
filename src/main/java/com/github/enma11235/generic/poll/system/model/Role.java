package com.github.enma11235.generic.poll.system.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private final RoleEnum authority;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(RoleEnum authority) {
        this.authority = authority;
        this.users = new ArrayList<>();
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}