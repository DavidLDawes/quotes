package com.virtualsoundnw.quotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.virtualsoundnw.quotes.service.QuoteService;
import com.virtualsoundnw.quotes.web.rest.util.HeaderUtil;
import com.virtualsoundnw.quotes.web.rest.util.PaginationUtil;
import com.virtualsoundnw.quotes.service.dto.QuoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Quote.
 */
@RestController
@RequestMapping("/api")
public class QuoteResource {

    private final Logger log = LoggerFactory.getLogger(QuoteResource.class);
        
    @Inject
    private QuoteService quoteService;

    /**
     * POST  /quotes : Create a new quote.
     *
     * @param quoteDTO the quoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quoteDTO, or with status 400 (Bad Request) if the quote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/quotes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> createQuote(@Valid @RequestBody QuoteDTO quoteDTO) throws URISyntaxException {
        log.debug("REST request to save Quote : {}", quoteDTO);
        if (quoteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("quote", "idexists", "A new quote cannot already have an ID")).body(null);
        }
        QuoteDTO result = quoteService.save(quoteDTO);
        return ResponseEntity.created(new URI("/api/quotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotes : Updates an existing quote.
     *
     * @param quoteDTO the quoteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quoteDTO,
     * or with status 400 (Bad Request) if the quoteDTO is not valid,
     * or with status 500 (Internal Server Error) if the quoteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/quotes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> updateQuote(@Valid @RequestBody QuoteDTO quoteDTO) throws URISyntaxException {
        log.debug("REST request to update Quote : {}", quoteDTO);
        if (quoteDTO.getId() == null) {
            return createQuote(quoteDTO);
        }
        QuoteDTO result = quoteService.save(quoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("quote", quoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotes : get all the quotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quotes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/quotes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<QuoteDTO>> getAllQuotes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Quotes");
        Page<QuoteDTO> page = quoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quotes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quotes/:id : get the "id" quote.
     *
     * @param id the id of the quoteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quoteDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/quotes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuoteDTO> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        QuoteDTO quoteDTO = quoteService.findOne(id);
        return Optional.ofNullable(quoteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quotes/:id : delete the "id" quote.
     *
     * @param id the id of the quoteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/quotes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        log.debug("REST request to delete Quote : {}", id);
        quoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quote", id.toString())).build();
    }

}
