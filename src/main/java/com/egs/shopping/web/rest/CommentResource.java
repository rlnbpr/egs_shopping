package com.egs.shopping.web.rest;

import com.egs.shopping.aspects.AdminRole;
import com.egs.shopping.domain.Customer;
import com.egs.shopping.repository.CustomerRepository;
import com.egs.shopping.service.CommentService;
import com.egs.shopping.service.CustomerService;
import com.egs.shopping.service.SecurityService;
import com.egs.shopping.service.dto.CustomerDTO;
import com.egs.shopping.service.exception.InvalidAuthorizationException;
import com.egs.shopping.service.exception.UserNotFoundException;
import com.egs.shopping.web.rest.errors.BadRequestAlertException;
import com.egs.shopping.service.dto.CommentDTO;
import com.egs.shopping.service.dto.CommentCriteria;
import com.egs.shopping.service.CommentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.egs.shopping.domain.Comment}.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "egsComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentService commentService;

    private final CommentQueryService commentQueryService;

    private final SecurityService securityService;

    private final CustomerRepository customerRepository;

    public CommentResource(CommentService commentService,
                           CommentQueryService commentQueryService,
                           CustomerRepository customerRepository,
                           SecurityService securityService) {
        this.commentService = commentService;
        this.commentQueryService = commentQueryService;
        this.securityService = securityService;
        this.customerRepository = customerRepository;
    }

    /**
     * {@code POST  /comments} : Create a new comment.
     *
     * @param commentDTO the commentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentDTO, or with status {@code 400 (Bad Request)} if the comment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", commentDTO);
        if (commentDTO.getId() != null) {
            throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()) {
            throw new InvalidAuthorizationException();
        }
        Customer customer = securityService.parseToken(token);
        Customer localCustomer = customerRepository.findByEmail(customer.getEmail()).orElseThrow(() ->
            new UserNotFoundException());
        commentDTO.setCustomerId(localCustomer.getId());
        CommentDTO result = commentService.save(commentDTO);
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments} : Updates an existing comment.
     *
     * @param commentDTO the commentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentDTO,
     * or with status {@code 400 (Bad Request)} if the commentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments")
    public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", commentDTO);
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentDTO result = commentService.save(commentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments(CommentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comments by criteria: {}", criteria);
        Page<CommentDTO> page = commentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/count} : count all the comments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comments/count")
    public ResponseEntity<Long> countComments(CommentCriteria criteria) {
        log.debug("REST request to count Comments by criteria: {}", criteria);
        return ResponseEntity.ok().body(commentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comment.
     *
     * @param id the id of the commentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        log.debug("REST request to get Comment : {}", id);
        Optional<CommentDTO> commentDTO = commentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentDTO);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comment.
     *
     * @param id the id of the commentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @AdminRole
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
