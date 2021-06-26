package com.egs.shopping.service;

import com.egs.shopping.service.dto.CustomerDTO;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.egs.shopping.domain.Customer}.
 */
public interface CustomerService {

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerDTO save(CustomerDTO customerDTO);

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerDTO> findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    JSONObject register(CustomerDTO customerDTO);

    JSONObject login(CustomerDTO customerDTO);

    JSONObject changeCustomerAccessibility(CustomerDTO customerDTO);
}
