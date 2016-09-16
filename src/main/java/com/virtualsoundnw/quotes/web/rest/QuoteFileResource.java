package com.virtualsoundnw.quotes.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.virtualsoundnw.quotes.service.QuoteService;
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
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing QuoteFile.
 */
@Controller
@RequestMapping("/api/V1")
public class QuoteFileResource {

    private final Logger log = LoggerFactory.getLogger(QuoteFileResource.class);
        
    @Inject
    private QuoteService quoteService;

    /**
     * GET  /QuoteFile : get all the quotes, mimic MOD API.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quotes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/QuoteFile",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_XML_VALUE)
    @Timed
    public ResponseEntity<List<QuoteDTO>> getQuoteFile(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Quotes using MOD immitation QuoteFile API");
        Page<QuoteDTO> page = quoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/V1/QuoteFile");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}

