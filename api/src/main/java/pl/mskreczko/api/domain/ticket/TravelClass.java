package pl.mskreczko.api.domain.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TravelClass {
    @JsonProperty("FIRST CLASS")
    FIRST_CLASS,
    @JsonProperty("BUSINESS CLASS")
    BUSINESS_CLASS,
    @JsonProperty("ECONOMY CLASS")
    ECONOMY_CLASS,
    @JsonProperty("PREMIUM CLASS")
    PREMIUM_CLASS
}
