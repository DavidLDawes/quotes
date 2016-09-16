package com.virtualsoundnw.quotes.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Quote entity.
 */
public class QuoteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 64)
    private String symbol;

    private Double last;

    private Double changeamount;

    private Double changepercent;

    private Double sharesoutstanding;

    private Double ask;

    private Float bid;

    private Float volume;

    private Double tendayaveragevolume;

    private ZonedDateTime lasttradetime;

    @NotNull
    @Size(min = 1, max = 128)
    private String xidsymbol;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }
    public Double getChangeamount() {
        return changeamount;
    }

    public void setChangeamount(Double changeamount) {
        this.changeamount = changeamount;
    }
    public Double getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(Double changepercent) {
        this.changepercent = changepercent;
    }
    public Double getSharesoutstanding() {
        return sharesoutstanding;
    }

    public void setSharesoutstanding(Double sharesoutstanding) {
        this.sharesoutstanding = sharesoutstanding;
    }
    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }
    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }
    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }
    public Double getTendayaveragevolume() {
        return tendayaveragevolume;
    }

    public void setTendayaveragevolume(Double tendayaveragevolume) {
        this.tendayaveragevolume = tendayaveragevolume;
    }
    public ZonedDateTime getLasttradetime() {
        return lasttradetime;
    }

    public void setLasttradetime(ZonedDateTime lasttradetime) {
        this.lasttradetime = lasttradetime;
    }
    public String getXidsymbol() {
        return xidsymbol;
    }

    public void setXidsymbol(String xidsymbol) {
        this.xidsymbol = xidsymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuoteDTO quoteDTO = (QuoteDTO) o;

        if ( ! Objects.equals(id, quoteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + id +
            ", symbol='" + symbol + "'" +
            ", last='" + last + "'" +
            ", changeamount='" + changeamount + "'" +
            ", changepercent='" + changepercent + "'" +
            ", sharesoutstanding='" + sharesoutstanding + "'" +
            ", ask='" + ask + "'" +
            ", bid='" + bid + "'" +
            ", volume='" + volume + "'" +
            ", tendayaveragevolume='" + tendayaveragevolume + "'" +
            ", lasttradetime='" + lasttradetime + "'" +
            ", xidsymbol='" + xidsymbol + "'" +
            '}';
    }
}
