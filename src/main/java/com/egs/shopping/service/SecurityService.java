package com.egs.shopping.service;

import com.egs.shopping.domain.Customer;

public interface SecurityService {

    String createToken(Customer customer);

    Customer parseToken(String token);
}
