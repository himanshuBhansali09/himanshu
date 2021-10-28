package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.ecommerceApplication.ecommerceApplication.passwordvalidation.ValidPassword;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private int count;

    @Column(nullable=false,unique = true)
    @NotBlank(message = "Enter the UserName")
    @Email(message = "invalid_email")
    private String username;

    @Column(nullable=false)
    @NotEmpty(message = "Password cant be null")
    @Size(min=4)
    private String password;

  /*  @Pattern(regexp = "\"^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
            "\t\t+ \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$\"")

  */


    @Column(nullable=false)
    @NotEmpty(message = "Enter the First Name")
    private String firstName;

    @Column(nullable=true)
    private String middleName;

    @Column(nullable=false)
    @NotEmpty(message = "Enter the Last Name")
    private String lastName;

    @Column(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;


    private boolean isEnabled;

    private boolean isAccountNonLocked;

    private boolean isActive;

    private boolean isAccountNonExpired;

    private boolean isDeleted;

    public User()
    {
        isEnabled=false;
        isAccountNonLocked=true;
        isAccountNonExpired=true;
        isDeleted=false;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Address> addresses;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void addAddress(Address address)
    {
        if(address!=null)
        {
            if(addresses == null)
            {
                addresses= new HashSet<>();

            }
            addresses.add(address);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getisActive()
    {
        return isActive;
    }


    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public void addRoles(Role role)
    {
        if(role!=null)
        {
            if(roles == null)
            {
                roles= new HashSet<>();
            }
            /*  role.addUser(this);*/
            roles.add(role);
        }
    }

}