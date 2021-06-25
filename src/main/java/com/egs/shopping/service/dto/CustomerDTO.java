package com.egs.shopping.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.egs.shopping.domain.enumeration.CustomerRoles;
import com.egs.shopping.domain.enumeration.CustomerStatus;

/**
 * A DTO for the {@link com.egs.shopping.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {
    
    private Long id;

    
    private String email;

    private String firstName;

    private String lastName;

    private CustomerRoles role;

    private CustomerStatus status;

    private String address;

    
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

    public CustomerRoles getRole() {
        return role;
    }

    public void setRole(CustomerRoles role) {
        this.role = role;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", role='" + getRole() + "'" +
            ", status='" + getStatus() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
