package com.bookstore.bookstore.transfer.order;

import com.bookstore.bookstore.domain.Product;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class CreateOrderRequest {

    @NotNull
    private Set<Long> productsId;
    @NotNull
    private long userId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String address;
    @NotNull
    private String email;
    @NotNull
    private Double phoneNumber;


    public Set<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(Set<Long> productsId) {
        this.productsId = productsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
