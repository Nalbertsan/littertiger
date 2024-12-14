package com.bet.littertiger.controller;

import com.bet.littertiger.dto.SendBlockChainDTO;
import com.bet.littertiger.dto.BettingEventDTO; // Supondo que você crie esse DTO para criar eventos de apostas
import com.bet.littertiger.service.blockchain.BlockchainService;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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

    // Endpoint para enviar transação (depositar, realizar aposta, etc.)
    @PostMapping("/send")
    public String sendTransaction(@RequestBody SendBlockChainDTO sendBlockChainDTO) {
        return blockchainService.sendTransaction(sendBlockChainDTO.fromAddress(),
                sendBlockChainDTO.privateKey(), sendBlockChainDTO.toAddress(), sendBlockChainDTO.value());
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

    // Endpoint para consultar as odds de um evento
    @GetMapping("/odds/{contractAddress}/{eventId}/{outcome}")
    public BigInteger getOdds(@PathVariable String contractAddress, @PathVariable BigInteger eventId, @PathVariable String outcome) {
        return blockchainService.getOdds(contractAddress, eventId, outcome);
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
}
