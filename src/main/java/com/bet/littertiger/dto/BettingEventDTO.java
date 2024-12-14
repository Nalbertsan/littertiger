package com.bet.littertiger.dto;


import java.math.BigInteger;
import java.util.List;

public record BettingEventDTO (  String contractAddress,
         String privateKey,
         String eventName,
         BigInteger eventId,
         List<String> outcomes,
         String outcome,
         BigInteger amount) {
}
