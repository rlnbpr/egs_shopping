package com.egs.shopping.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.egs.shopping.domain.enumeration.CustomerRoles;
import com.egs.shopping.domain.enumeration.CustomerStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.egs.shopping.domain.Customer} entity. This class is used
 * in {@link com.egs.shopping.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CustomerRoles
     */
    public static class CustomerRolesFilter extends Filter<CustomerRoles> {

        public CustomerRolesFilter() {
        }

        public CustomerRolesFilter(CustomerRolesFilter filter) {
            super(filter);
        }

        @Override
        public CustomerRolesFilter copy() {
            return new CustomerRolesFilter(this);
        }

    }
    /**
     * Class for filtering CustomerStatus
     */
    public static class CustomerStatusFilter extends Filter<CustomerStatus> {

        public CustomerStatusFilter() {
        }

        public CustomerStatusFilter(CustomerStatusFilter filter) {
            super(filter);
        }

        @Override
        public CustomerStatusFilter copy() {
            return new CustomerStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private StringFilter firstName;

    private StringFilter lastName;

    private CustomerRolesFilter role;

    private CustomerStatusFilter status;

    private StringFilter address;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.address = other.address == null ? null : other.address.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public CustomerRolesFilter getRole() {
        return role;
    }

    public void setRole(CustomerRolesFilter role) {
        this.role = role;
    }

    public CustomerStatusFilter getStatus() {
        return status;
    }

    public void setStatus(CustomerStatusFilter status) {
        this.status = status;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(role, that.role) &&
            Objects.equals(status, that.status) &&
            Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        email,
        firstName,
        lastName,
        role,
        status,
        address
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
            "}";
    }

}
