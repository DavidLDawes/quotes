package com.virtualsoundnw.quotes.web.rest;

import com.virtualsoundnw.quotes.QuotesApp;
import com.virtualsoundnw.quotes.domain.Quote;
import com.virtualsoundnw.quotes.repository.QuoteRepository;
import com.virtualsoundnw.quotes.service.QuoteService;
import com.virtualsoundnw.quotes.service.dto.QuoteDTO;
import com.virtualsoundnw.quotes.service.mapper.QuoteMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuoteResource REST controller.
 *
 * @see QuoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuotesApp.class)
public class QuoteResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));
    private static final String DEFAULT_SYMBOL = "AA";
    private static final String UPDATED_SYMBOL = "BB";

    private static final Double DEFAULT_LAST = 1D;
    private static final Double UPDATED_LAST = 2D;

    private static final Double DEFAULT_CHANGEAMOUNT = 1D;
    private static final Double UPDATED_CHANGEAMOUNT = 2D;

    private static final Double DEFAULT_CHANGEPERCENT = 1D;
    private static final Double UPDATED_CHANGEPERCENT = 2D;

    private static final Double DEFAULT_SHARESOUTSTANDING = 1D;
    private static final Double UPDATED_SHARESOUTSTANDING = 2D;

    private static final Double DEFAULT_ASK = 1D;
    private static final Double UPDATED_ASK = 2D;

    private static final Float DEFAULT_BID = 1F;
    private static final Float UPDATED_BID = 2F;

    private static final Float DEFAULT_VOLUME = 1F;
    private static final Float UPDATED_VOLUME = 2F;

    private static final Double DEFAULT_TENDAYAVERAGEVOLUME = 1D;
    private static final Double UPDATED_TENDAYAVERAGEVOLUME = 2D;

    private static final ZonedDateTime DEFAULT_LASTTRADETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LASTTRADETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LASTTRADETIME_STR = dateTimeFormatter.format(DEFAULT_LASTTRADETIME);
    private static final String DEFAULT_XIDSYMBOL = "A";
    private static final String UPDATED_XIDSYMBOL = "B";

    @Inject
    private QuoteRepository quoteRepository;

    @Inject
    private QuoteMapper quoteMapper;

    @Inject
    private QuoteService quoteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuoteMockMvc;

    private Quote quote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuoteResource quoteResource = new QuoteResource();
        ReflectionTestUtils.setField(quoteResource, "quoteService", quoteService);
        this.restQuoteMockMvc = MockMvcBuilders.standaloneSetup(quoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createEntity(EntityManager em) {
        Quote quote = new Quote();
        quote = new Quote()
                .symbol(DEFAULT_SYMBOL)
                .last(DEFAULT_LAST)
                .changeamount(DEFAULT_CHANGEAMOUNT)
                .changepercent(DEFAULT_CHANGEPERCENT)
                .sharesoutstanding(DEFAULT_SHARESOUTSTANDING)
                .ask(DEFAULT_ASK)
                .bid(DEFAULT_BID)
                .volume(DEFAULT_VOLUME)
                .tendayaveragevolume(DEFAULT_TENDAYAVERAGEVOLUME)
                .lasttradetime(DEFAULT_LASTTRADETIME)
                .xidsymbol(DEFAULT_XIDSYMBOL);
        return quote;
    }

    @Before
    public void initTest() {
        quote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quotes.get(quotes.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testQuote.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testQuote.getChangeamount()).isEqualTo(DEFAULT_CHANGEAMOUNT);
        assertThat(testQuote.getChangepercent()).isEqualTo(DEFAULT_CHANGEPERCENT);
        assertThat(testQuote.getSharesoutstanding()).isEqualTo(DEFAULT_SHARESOUTSTANDING);
        assertThat(testQuote.getAsk()).isEqualTo(DEFAULT_ASK);
        assertThat(testQuote.getBid()).isEqualTo(DEFAULT_BID);
        assertThat(testQuote.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testQuote.getTendayaveragevolume()).isEqualTo(DEFAULT_TENDAYAVERAGEVOLUME);
        assertThat(testQuote.getLasttradetime()).isEqualTo(DEFAULT_LASTTRADETIME);
        assertThat(testQuote.getXidsymbol()).isEqualTo(DEFAULT_XIDSYMBOL);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setSymbol(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isBadRequest());

        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkXidsymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setXidsymbol(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(quote);

        restQuoteMockMvc.perform(post("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isBadRequest());

        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quotes
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
                .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
                .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.doubleValue())))
                .andExpect(jsonPath("$.[*].changeamount").value(hasItem(DEFAULT_CHANGEAMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].changepercent").value(hasItem(DEFAULT_CHANGEPERCENT.doubleValue())))
                .andExpect(jsonPath("$.[*].sharesoutstanding").value(hasItem(DEFAULT_SHARESOUTSTANDING.doubleValue())))
                .andExpect(jsonPath("$.[*].ask").value(hasItem(DEFAULT_ASK.doubleValue())))
                .andExpect(jsonPath("$.[*].bid").value(hasItem(DEFAULT_BID.doubleValue())))
                .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
                .andExpect(jsonPath("$.[*].tendayaveragevolume").value(hasItem(DEFAULT_TENDAYAVERAGEVOLUME.doubleValue())))
                .andExpect(jsonPath("$.[*].lasttradetime").value(hasItem(DEFAULT_LASTTRADETIME_STR)))
                .andExpect(jsonPath("$.[*].xidsymbol").value(hasItem(DEFAULT_XIDSYMBOL.toString())));
    }

    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.doubleValue()))
            .andExpect(jsonPath("$.changeamount").value(DEFAULT_CHANGEAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.changepercent").value(DEFAULT_CHANGEPERCENT.doubleValue()))
            .andExpect(jsonPath("$.sharesoutstanding").value(DEFAULT_SHARESOUTSTANDING.doubleValue()))
            .andExpect(jsonPath("$.ask").value(DEFAULT_ASK.doubleValue()))
            .andExpect(jsonPath("$.bid").value(DEFAULT_BID.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.tendayaveragevolume").value(DEFAULT_TENDAYAVERAGEVOLUME.doubleValue()))
            .andExpect(jsonPath("$.lasttradetime").value(DEFAULT_LASTTRADETIME_STR))
            .andExpect(jsonPath("$.xidsymbol").value(DEFAULT_XIDSYMBOL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        Quote updatedQuote = quoteRepository.findOne(quote.getId());
        updatedQuote
                .symbol(UPDATED_SYMBOL)
                .last(UPDATED_LAST)
                .changeamount(UPDATED_CHANGEAMOUNT)
                .changepercent(UPDATED_CHANGEPERCENT)
                .sharesoutstanding(UPDATED_SHARESOUTSTANDING)
                .ask(UPDATED_ASK)
                .bid(UPDATED_BID)
                .volume(UPDATED_VOLUME)
                .tendayaveragevolume(UPDATED_TENDAYAVERAGEVOLUME)
                .lasttradetime(UPDATED_LASTTRADETIME)
                .xidsymbol(UPDATED_XIDSYMBOL);
        QuoteDTO quoteDTO = quoteMapper.quoteToQuoteDTO(updatedQuote);

        restQuoteMockMvc.perform(put("/api/quotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
                .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quotes.get(quotes.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testQuote.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testQuote.getChangeamount()).isEqualTo(UPDATED_CHANGEAMOUNT);
        assertThat(testQuote.getChangepercent()).isEqualTo(UPDATED_CHANGEPERCENT);
        assertThat(testQuote.getSharesoutstanding()).isEqualTo(UPDATED_SHARESOUTSTANDING);
        assertThat(testQuote.getAsk()).isEqualTo(UPDATED_ASK);
        assertThat(testQuote.getBid()).isEqualTo(UPDATED_BID);
        assertThat(testQuote.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testQuote.getTendayaveragevolume()).isEqualTo(UPDATED_TENDAYAVERAGEVOLUME);
        assertThat(testQuote.getLasttradetime()).isEqualTo(UPDATED_LASTTRADETIME);
        assertThat(testQuote.getXidsymbol()).isEqualTo(UPDATED_XIDSYMBOL);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);
        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Get the quote
        restQuoteMockMvc.perform(delete("/api/quotes/{id}", quote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quote> quotes = quoteRepository.findAll();
        assertThat(quotes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
