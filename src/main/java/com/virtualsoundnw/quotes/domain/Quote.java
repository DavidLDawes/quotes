package com.virtualsoundnw.quotes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 64)
    @Column(name = "symbol", length = 64, nullable = false)
    private String symbol;

    @Column(name = "last")
    private Double last;

    @Column(name = "changeamount")
    private Double changeamount;

    @Column(name = "changepercent")
    private Double changepercent;

    @Column(name = "sharesoutstanding")
    private Double sharesoutstanding;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "bid")
    private Float bid;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "tendayaveragevolume")
    private Double tendayaveragevolume;

    @Column(name = "lasttradetime")
    private ZonedDateTime lasttradetime;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "xidsymbol", length = 128, nullable = false)
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

    public Quote symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLast() {
        return last;
    }

    public Quote last(Double last) {
        this.last = last;
        return this;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getChangeamount() {
        return changeamount;
    }

    public Quote changeamount(Double changeamount) {
        this.changeamount = changeamount;
        return this;
    }

    public void setChangeamount(Double changeamount) {
        this.changeamount = changeamount;
    }

    public Double getChangepercent() {
        return changepercent;
    }

    public Quote changepercent(Double changepercent) {
        this.changepercent = changepercent;
        return this;
    }

    public void setChangepercent(Double changepercent) {
        this.changepercent = changepercent;
    }

    public Double getSharesoutstanding() {
        return sharesoutstanding;
    }

    public Quote sharesoutstanding(Double sharesoutstanding) {
        this.sharesoutstanding = sharesoutstanding;
        return this;
    }

    public void setSharesoutstanding(Double sharesoutstanding) {
        this.sharesoutstanding = sharesoutstanding;
    }

    public Double getAsk() {
        return ask;
    }

    public Quote ask(Double ask) {
        this.ask = ask;
        return this;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Float getBid() {
        return bid;
    }

    public Quote bid(Float bid) {
        this.bid = bid;
        return this;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getVolume() {
        return volume;
    }

    public Quote volume(Float volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Double getTendayaveragevolume() {
        return tendayaveragevolume;
    }

    public Quote tendayaveragevolume(Double tendayaveragevolume) {
        this.tendayaveragevolume = tendayaveragevolume;
        return this;
    }

    public void setTendayaveragevolume(Double tendayaveragevolume) {
        this.tendayaveragevolume = tendayaveragevolume;
    }

    public ZonedDateTime getLasttradetime() {
        return lasttradetime;
    }

    public Quote lasttradetime(ZonedDateTime lasttradetime) {
        this.lasttradetime = lasttradetime;
        return this;
    }

    public void setLasttradetime(ZonedDateTime lasttradetime) {
        this.lasttradetime = lasttradetime;
    }

    public String getXidsymbol() {
        return xidsymbol;
    }

    public Quote xidsymbol(String xidsymbol) {
        this.xidsymbol = xidsymbol;
        return this;
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
        Quote quote = (Quote) o;
        if(quote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, quote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Quote{" +
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
