package com.virtualsoundnw.quotes.service.impl;

import com.virtualsoundnw.quotes.service.QuoteService;
import com.virtualsoundnw.quotes.domain.Quote;
import com.virtualsoundnw.quotes.repository.QuoteRepository;
import com.virtualsoundnw.quotes.service.dto.QuoteDTO;
import com.virtualsoundnw.quotes.service.mapper.QuoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Quote.
 */
@Service
@Transactional
public class QuoteServiceImpl implements QuoteService{

    private final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);
    
    @Inject
    private QuoteRepository quoteRepository;

    @Inject
    private QuoteMapper quoteMapper;

    /**
     * Save a quote.
     *
     * @param quoteDTO the entity to save
     * @return the persisted entity
     */
    public QuoteDTO save(QuoteDTO quoteDTO) {
        log.debug("Request to save Quote : {}", quoteDTO);
        Quote quote = quoteMapper.quoteDTOToQuote(quoteDTO);
        quote = quoteRepository.save(quote);
        QuoteDTO result = quoteMapper.quoteToQuoteDTO(quote);
        return result;
    }

    /**
     *  Get all the quotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<QuoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quotes");
        Page<Quote> result = quoteRepository.findAll(pageable);
        return result.map(quote -> quoteMapper.quoteToQuoteDTO(quote));
    }

    /**
     *  Get one quote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public QuoteDTO findOne(Long id) {
        log.debug("Request to get Quote : {}", id);
        Quote quote = quoteRepository.findOne(id);
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);
        return quoteDTO;
    }

    /**
     *  Delete the  quote by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Quote : {}", id);
        quoteRepository.delete(id);
    }
}
