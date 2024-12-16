package com.bet.littertiger.service.blockchain;

import com.bet.littertiger.dto.EventDetailsDTO;
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
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlockchainService {

    private final BlockChainConfig blockchainConfig;
    private final Web3j web3j;

    // Define o provedor de gas
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_000_000L);

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

    public String finalizeEvent(String contractAddress, String privateKey, BigInteger eventId, String result) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Finaliza o evento no contrato
            bettingSystem.finalizeEvent(eventId, result).send();

            return "Evento finalizado com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao finalizar o evento", e);
        }
    }


    // Método para consultar as odds de um evento
    public BigInteger getOdds(String contractAddress, BigInteger eventId, String outcome) {
        try {
            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")), new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Consulta as odds
            return bettingSystem.getOdds(eventId, outcome).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter as odds", e);
        }
    }

    public List<BigInteger> getAllEventIds(String contractAddress) {
        try {
            // Configura o TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48"));

            // Carrega o contrato inteligente BettingSystemDTO
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(contractAddress, web3j, txManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

            // Chama o método getAllEventIds no contrato
            return bettingSystem.getAllEventIds().send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter os IDs dos eventos", e);
        }
    }

    public BigInteger getMyBalance(String contractAddress, String privateKey) {
        try {
            // Configurar TransactionManager com a chave privada
            TransactionManager txManager = new RawTransactionManager(web3j, Credentials.create(privateKey));

            // Carregar o contrato inteligente
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    txManager,
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );

            // Chamar o método getMyBalance do contrato
            return bettingSystem.getMyBalance().send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o saldo do contrato", e);
        }
    }

    /**
     * Converte Wei para Ether.
     *
     * @param balance Saldo em Wei.
     * @return Saldo em Ether.
     */
    public BigDecimal convertToEther(BigInteger balance) {
        return Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
    }

    public List<String> getEventOutcomes(String contractAddress, BigInteger eventId) {
        try {
            // Carrega o contrato
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );

            // Chama a função getEventOutcomes no contrato
            return bettingSystem.getEventOutcomes(eventId).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter os outcomes do evento", e);
        }
    }

    // Serviço para obter o nome do evento
    public String getEventName(String contractAddress, BigInteger eventId) {
        try {
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
            return bettingSystem.getEventName(eventId).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o nome do evento", e);
        }
    }


    // Serviço para obter o total apostado em um resultado específico
    public BigInteger getEventTotalBets(String contractAddress, BigInteger eventId, String outcome) {
        try {
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
            return bettingSystem.getEventTotalBets(eventId, outcome).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o total apostado no evento", e);
        }
    }

    // Serviço para verificar se o evento está finalizado
    public boolean isEventFinalized(String contractAddress, BigInteger eventId) {
        try {
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
            return bettingSystem.isEventFinalized(eventId).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar se o evento está finalizado", e);
        }
    }

    // Serviço para obter o resultado de um evento finalizado
    public String getEventResult(String contractAddress, BigInteger eventId) {
        try {
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
            return bettingSystem.getEventResult(eventId).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o resultado do evento", e);
        }
    }

    // Serviço para obter o total do pool de um evento
    public BigInteger getEventTotalPool(String contractAddress, BigInteger eventId) {
        try {
            BettingSystemDTO bettingSystem = BettingSystemDTO.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, Credentials.create("0xa03cf99a5ad99c123677f256effb1af8daef3cc2b00d50e22da5ed15ceb33a48")),
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
            return bettingSystem.getEventTotalPool(eventId).send();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o total do pool do evento", e);
        }
    }

    public List<EventDetailsDTO> getConsolidatedEventDetails(String contractAddress) {
        try {
            // Lista final para armazenar os detalhes consolidados
            List<EventDetailsDTO> eventDetailsList = new ArrayList<>();

            // 1. Obter todos os IDs dos eventos
            List<BigInteger> eventIds = getAllEventIds(contractAddress);

            // 2. Iterar sobre cada evento para obter seus dados
            for (BigInteger eventId : eventIds) {
                EventDetailsDTO eventDetails = new EventDetailsDTO();
                eventDetails.setEventId(eventId);

                // Obter o nome do evento
                String eventName = getEventName(contractAddress, eventId);
                eventDetails.setEventName(eventName);

                // Obter o total do pool do evento
                BigInteger totalPool = getEventTotalPool(contractAddress, eventId);
                eventDetails.setTotalPool(totalPool);

                // Verificar se o evento está finalizado
                boolean isFinalized = isEventFinalized(contractAddress, eventId);
                eventDetails.setFinalized(isFinalized);

                // Obter os outcomes e seus respectivos totais apostados
                List<String> outcomes = getEventOutcomes(contractAddress, eventId);
                Map<String, BigInteger> outcomeBets = new HashMap<>();
                Map<String, BigInteger> odds = new HashMap<>();

                for (String outcome : outcomes) {
                    BigInteger totalBets = getEventTotalBets(contractAddress, eventId, outcome);
                    outcomeBets.put(outcome, totalBets);
                    BigInteger odd = getOdds(contractAddress, eventId, outcome);
                    odds.put(outcome, odd);
                }
                eventDetails.setOutcomes(outcomeBets);
                eventDetails.setOdds(odds);

                // Se o evento estiver finalizado, obter o resultado
                if (isFinalized) {
                    String eventResult = getEventResult(contractAddress, eventId);
                    eventDetails.setEventResult(eventResult);
                }

                // Adicionar os detalhes do evento à lista final
                eventDetailsList.add(eventDetails);
            }

            return eventDetailsList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consolidar os detalhes dos eventos", e);
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
