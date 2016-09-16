package com.virtualsoundnw.quotes.service.mapper;

import com.virtualsoundnw.quotes.domain.*;
import com.virtualsoundnw.quotes.service.dto.QuoteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Quote and its DTO QuoteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuoteMapper {

    QuoteDTO quoteToQuoteDTO(Quote quote);

    List<QuoteDTO> quotesToQuoteDTOs(List<Quote> quotes);

    Quote quoteDTOToQuote(QuoteDTO quoteDTO);

    List<Quote> quoteDTOsToQuotes(List<QuoteDTO> quoteDTOs);
}
