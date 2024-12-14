package com.bet.littertiger.service.blockchain;

import com.bet.littertiger.infra.blockchain.BlockChainConfig;
import com.bet.littertiger.dto.BettingSystemDTO;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.List;

@Service
public class BlockchainService {

    private final BlockChainConfig blockchainConfig;
    private final Web3j web3j;

    // Define o provedor de gas
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(2000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(3000000L);

    public BlockchainService(BlockChainConfig blockchainConfig) {
        this.blockchainConfig = blockchainConfig;

        // Conecta ao nó RPC
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:5050"));
    }

    // Método para enviar uma transação para a blockchain (por exemplo, depositar ou realizar aposta)
    public String sendTransaction(String fromAddress, String privateKey, String toAddress, BigInteger value) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Envia a transação
            EthSendTransaction transactionResponse = txManager.sendTransaction(
                    GAS_PRICE,
                    GAS_LIMIT,
                    toAddress,
                    "",
                    value
            );

            return transactionResponse.getTransactionHash();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar a transação", e);
        }
    }

    // Método para obter o saldo de uma conta
    public BigInteger getBalance(String address) {
        try {
            EthGetBalance balanceResponse = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return balanceResponse.getBalance();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o saldo", e);
        }
    }

    // Método para interagir com o contrato e criar um evento de apostas
    public String createBettingEvent(String contractAddress, String privateKey, String eventName, List<String> outcomes) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Cria o evento no contrato
            bettingSystem.createEvent(eventName, outcomes).send();

            return "Evento de apostas criado com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o evento de apostas", e);
        }
    }

    // Método para realizar uma aposta em um evento
    public String placeBet(String contractAddress, String privateKey, BigInteger eventId, String outcome, BigInteger amount) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Realiza a aposta
            bettingSystem.placeBet(eventId, outcome, amount).send();

            return "Aposta realizada com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a aposta", e);
        }
    }

    // Método para consultar as odds de um evento
    public BigInteger getOdds(String contractAddress, BigInteger eventId, String outcome) {
        try {
            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, new RawTransactionManager(web3j, Credentials.create("privateKey")), new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Consulta as odds
            return bettingSystem.getOdds(eventId, outcome).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter as odds", e);
        }
    }

    // Método para depositar valores no contrato
    public String deposit(String contractAddress, String privateKey, BigInteger amount) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Realiza o depósito
            bettingSystem.deposit(amount).send();

            return "Depósito realizado com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar o depósito", e);
        }
    }

    // Método para retirar valores do contrato
    public String withdraw(String contractAddress, String privateKey) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Realiza a retirada
            bettingSystem.withdraw().send();

            return "Retirada realizada com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a retirada", e);
        }
    }
}
