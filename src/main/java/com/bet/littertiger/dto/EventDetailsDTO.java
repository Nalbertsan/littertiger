package com.bet.littertiger.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


public class EventDetailsDTO {
    private BigInteger eventId;
    private String eventName;
    private BigInteger totalPool;
    private boolean isFinalized;
    private String eventResult;
    private Map<String, BigInteger> outcomes; // Outcome -> Total Apostado
    private Map<String, BigInteger> odds;    // Outcome -> Odds

    // Getters e Setters
    public BigInteger getEventId() { return eventId; }
    public void setEventId(BigInteger eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public BigInteger getTotalPool() { return totalPool; }
    public void setTotalPool(BigInteger totalPool) { this.totalPool = totalPool; }

    public boolean isFinalized() { return isFinalized; }
    public void setFinalized(boolean finalized) { isFinalized = finalized; }

    public String getEventResult() { return eventResult; }
    public void setEventResult(String eventResult) { this.eventResult = eventResult; }

    public Map<String, BigInteger> getOutcomes() { return outcomes; }
    public void setOutcomes(Map<String, BigInteger> outcomes) { this.outcomes = outcomes; }

    public Map<String, BigInteger> getOdds() { return odds; }
    public void setOdds(Map<String, BigInteger> odds) { this.odds = odds; }
}

