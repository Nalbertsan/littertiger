package com.bet.littertiger.controller;

import com.bet.littertiger.dto.EventDetailsDTO;
import com.bet.littertiger.dto.SendBlockChainDTO;
import com.bet.littertiger.dto.BettingEventDTO; // Supondo que você crie esse DTO para criar eventos de apostas
import com.bet.littertiger.service.blockchain.BlockchainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    // Endpoint para consultar o saldo de uma conta
    @GetMapping("/balance/{address}")
    public BigInteger getBalance(@PathVariable String address) {
        return blockchainService.getBalance(address);
    }

    // Endpoint para criar um evento de apostas
    @PostMapping("/create-betting-event")
    public String createBettingEvent(@RequestBody BettingEventDTO bettingEventDTO) {
        return blockchainService.createBettingEvent(
                bettingEventDTO.contractAddress(),
                bettingEventDTO.privateKey(),
                bettingEventDTO.eventName(),
                bettingEventDTO.outcomes()
        );
    }

    // Endpoint para realizar uma aposta
    @PostMapping("/place-bet")
    public String placeBet(@RequestBody BettingEventDTO bettingEventDTO) {
        return blockchainService.placeBet(
                bettingEventDTO.contractAddress(),
                bettingEventDTO.privateKey(),
                bettingEventDTO.eventId(),
                bettingEventDTO.outcome(),
                bettingEventDTO.amount()
        );
    }

    // Endpoint para realizar um depósito no contrato
    @PostMapping("/deposit")
    public String deposit(@RequestBody SendBlockChainDTO sendBlockChainDTO) {
        return blockchainService.deposit(
                sendBlockChainDTO.fromAddress(),
                sendBlockChainDTO.privateKey(),
                sendBlockChainDTO.value()
        );
    }

    // Endpoint para retirar valores do contrato
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody SendBlockChainDTO sendBlockChainDTO) {
        return blockchainService.withdraw(
                sendBlockChainDTO.fromAddress(),
                sendBlockChainDTO.privateKey()
        );
    }

    @GetMapping("/details/{contractAddress}")
    public List<EventDetailsDTO> getConsolidatedEventDetails(@PathVariable String contractAddress) {
        return blockchainService.getConsolidatedEventDetails(contractAddress);
    }

    @PostMapping("/{contractAddress}/{privateKey}/{eventId}/{result}/finalize")
    public ResponseEntity<String> finalizeEvent(
            @PathVariable String contractAddress,
            @PathVariable String privateKey,
            @PathVariable BigInteger eventId,
            @PathVariable String result
    ) {
        try {
            String response = blockchainService.finalizeEvent(contractAddress, privateKey, eventId, result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao finalizar o evento: " + e.getMessage());
        }
    }

    @GetMapping("/balance-contract/{contractAddress}/{privateKey}")
    public ResponseEntity<Map<String, Object>> getMyBalance(
            @PathVariable String contractAddress,
            @PathVariable String privateKey
    ) {
        try {
            // Obter saldo em Wei
            BigInteger balanceWei = blockchainService.getMyBalance(contractAddress, privateKey);

            // Converter saldo para Ether
            BigDecimal balanceEther = blockchainService.convertToEther(balanceWei);

            // Criar resposta
            Map<String, Object> response = new HashMap<>();
            response.put("balanceInWei", balanceWei);
            response.put("balanceInEther", balanceEther);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
