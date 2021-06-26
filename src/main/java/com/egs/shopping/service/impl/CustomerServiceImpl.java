package com.egs.shopping.service.impl;

import com.egs.shopping.domain.enumeration.CustomerRoles;
import com.egs.shopping.domain.enumeration.CustomerStatus;
import com.egs.shopping.service.CustomerService;
import com.egs.shopping.domain.Customer;
import com.egs.shopping.repository.CustomerRepository;
import com.egs.shopping.service.SecurityService;
import com.egs.shopping.service.dto.CustomerDTO;
import com.egs.shopping.service.exception.*;
import com.egs.shopping.service.mapper.CustomerMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final SecurityService securityService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.securityService = securityService;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable)
            .map(customerMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
            .map(customerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public JSONObject register(CustomerDTO customerDTO) {
        if(Objects.isNull(customerDTO.getPassword()) ||
            Objects.isNull(customerDTO.getConfirmPassword()) ||
            !customerDTO.getPassword().equals(customerDTO.getConfirmPassword())) {
            throw new InvalidPasswordException();
        }
        customerDTO.setRole(CustomerRoles.USER);
        customerDTO.setStatus(CustomerStatus.UNBLOCK);
        //we can use any other encryption algorithm instead of sha256
        customerDTO.setPassword(DigestUtils.sha256Hex(customerDTO.getPassword()));
        this.save(customerDTO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        return jsonObject;
    }

    @Override
    public JSONObject login(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByEmail(customerDTO.getEmail()).orElseThrow(() ->
            new InvalidEmailPasswordException());
        if(customer.getStatus().equals(CustomerStatus.BLOCK)) {
            throw new UserBlockException();
        }
        if(!DigestUtils.sha256Hex(customerDTO.getPassword()).equals(customer.getPassword())) {
            throw new InvalidEmailPasswordException();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("token", securityService.createToken(customer));
        return jsonObject;
    }

    @Override
    public JSONObject changeCustomerAccessibility(CustomerDTO customerDTO) {
        Customer customer = securityService.parseToken(customerDTO.getToken());
        if(!customer.getRole().equals(CustomerRoles.ADMIN)) {
            throw new InvalidAuthorizationException();
        }
        customer = this.customerRepository.findByEmail(customerDTO.getEmail()).orElseThrow(() ->
            new UserNotFoundException());
        customer.setStatus(customerDTO.getStatus());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        return jsonObject;
    }
}
