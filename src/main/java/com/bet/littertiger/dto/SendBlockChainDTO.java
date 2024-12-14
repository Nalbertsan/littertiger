package com.bet.littertiger.dto;


import java.math.BigInteger;

public record SendBlockChainDTO (String fromAddress,String privateKey, String toAddress, BigInteger value) {
}
