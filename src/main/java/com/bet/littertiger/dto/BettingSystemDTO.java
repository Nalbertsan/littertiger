package com.bet.littertiger.dto;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.TypeReference;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BettingSystemDTO extends Contract {

    public static final String ABI = "[\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": true,\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"user\",\n" +
            "          \"type\": \"address\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"BetPlaced\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": true,\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"user\",\n" +
            "          \"type\": \"address\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"Deposit\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"id\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"name\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"EventCreated\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"id\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"result\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"EventFinalized\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"odds\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"LogOddsUpdated\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": true,\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"participant\",\n" +
            "          \"type\": \"address\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": true,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"ParticipantRegistered\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"anonymous\": false,\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"indexed\": true,\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"user\",\n" +
            "          \"type\": \"address\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"indexed\": false,\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"Withdrawal\",\n" +
            "      \"type\": \"event\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"address\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"balances\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"name\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string[]\",\n" +
            "          \"name\": \"outcomes\",\n" +
            "          \"type\": \"string[]\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"createEvent\",\n" +
            "      \"outputs\": [],\n" +
            "      \"stateMutability\": \"nonpayable\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"deposit\",\n" +
            "      \"outputs\": [],\n" +
            "      \"stateMutability\": \"payable\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"eventCounter\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"eventParticipants\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"address\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"events\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"id\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"name\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"bool\",\n" +
            "          \"name\": \"finalized\",\n" +
            "          \"type\": \"bool\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"result\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"totalPool\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"result\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"finalizeEvent\",\n" +
            "      \"outputs\": [],\n" +
            "      \"stateMutability\": \"nonpayable\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"finalizedEvents\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"id\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"name\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"result\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"totalPool\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"getAllEventIds\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256[]\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256[]\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"getAllEvents\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"components\": [\n" +
            "            {\n" +
            "              \"internalType\": \"uint256\",\n" +
            "              \"name\": \"id\",\n" +
            "              \"type\": \"uint256\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"internalType\": \"string\",\n" +
            "              \"name\": \"name\",\n" +
            "              \"type\": \"string\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"internalType\": \"bool\",\n" +
            "              \"name\": \"finalized\",\n" +
            "              \"type\": \"bool\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"internalType\": \"uint256\",\n" +
            "              \"name\": \"totalPool\",\n" +
            "              \"type\": \"uint256\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"internalType\": \"struct BettingSystem.EventSummary[]\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"tuple[]\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventName\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventOdds\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventOutcomes\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"string[]\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"string[]\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventResult\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventTotalBets\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getEventTotalPool\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getOdds\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"user\",\n" +
            "          \"type\": \"address\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"getUserInfo\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"balance\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"components\": [\n" +
            "            {\n" +
            "              \"internalType\": \"uint256\",\n" +
            "              \"name\": \"eventId\",\n" +
            "              \"type\": \"uint256\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"internalType\": \"string\",\n" +
            "              \"name\": \"outcome\",\n" +
            "              \"type\": \"string\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"internalType\": \"uint256\",\n" +
            "              \"name\": \"amount\",\n" +
            "              \"type\": \"uint256\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"internalType\": \"struct BettingSystem.Bet[]\",\n" +
            "          \"name\": \"bets\",\n" +
            "          \"type\": \"tuple[]\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"isEventFinalized\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"bool\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"bool\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"placeBet\",\n" +
            "      \"outputs\": [],\n" +
            "      \"stateMutability\": \"payable\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"totalEvents\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"address\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"address\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"userBets\",\n" +
            "      \"outputs\": [\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"eventId\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"string\",\n" +
            "          \"name\": \"outcome\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"internalType\": \"uint256\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"type\": \"uint256\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"stateMutability\": \"view\",\n" +
            "      \"type\": \"function\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"inputs\": [],\n" +
            "      \"name\": \"withdraw\",\n" +
            "      \"outputs\": [],\n" +
            "      \"stateMutability\": \"nonpayable\",\n" +
            "      \"type\": \"function\"\n" +
            "    }\n" +
            "  ]";

    public static final String BIN = "0x6080604052348015600f57600080fd5b506139198061001f6000396000f3fe6080604052600436106101405760003560e01c80636fea5b94116100b6578063a9549f0a1161006f578063a9549f0a146104c9578063b6da0bea14610506578063ba87068614610543578063c27a500d1461056e578063c60d2d8f14610599578063d0e30db0146105d657610140565b80636fea5b941461036a578063734d0eea146103a757806377c87c09146103e75780638068aa68146104105780638d261dd11461044f5780639b539c821461048c57610140565b80634945fcc7116101085780634945fcc71461025457806352c55db9146102915780635e8af735146102bc5780636386c1c7146102d8578063682e54cd146103165780636a03942f1461034157610140565b80630b791430146101455780631e384b051461018657806327e235e3146101c3578063354ccc65146102005780633ccfd60b1461023d575b600080fd5b34801561015157600080fd5b5061016c6004803603810190610167919061229e565b6105e0565b60405161017d959493929190612385565b60405180910390f35b34801561019257600080fd5b506101ad60048036038101906101a8919061229e565b610733565b6040516101ba91906123e6565b60405180910390f35b3480156101cf57600080fd5b506101ea60048036038101906101e5919061245f565b610760565b6040516101f7919061248c565b60405180910390f35b34801561020c57600080fd5b506102276004803603810190610222919061229e565b610778565b60405161023491906125b3565b60405180910390f35b34801561024957600080fd5b50610252610867565b005b34801561026057600080fd5b5061027b6004803603810190610276919061229e565b610a31565b60405161028891906125d5565b60405180910390f35b34801561029d57600080fd5b506102a6610ad9565b6040516102b3919061248c565b60405180910390f35b6102d660048036038101906102d1919061272c565b610adf565b005b3480156102e457600080fd5b506102ff60048036038101906102fa919061245f565b610f51565b60405161030d9291906128a9565b60405180910390f35b34801561032257600080fd5b5061032b6110dc565b6040516103389190612988565b60405180910390f35b34801561034d57600080fd5b5061036860048036038101906103639190612a90565b611185565b005b34801561037657600080fd5b50610391600480360381019061038c9190612b08565b611358565b60405161039e9190612b57565b60405180910390f35b3480156103b357600080fd5b506103ce60048036038101906103c9919061229e565b6113a6565b6040516103de9493929190612b72565b60405180910390f35b3480156103f357600080fd5b5061040e6004803603810190610409919061272c565b6114e6565b005b34801561041c57600080fd5b5061043760048036038101906104329190612bc5565b6119bc565b60405161044693929190612c05565b60405180910390f35b34801561045b57600080fd5b506104766004803603810190610471919061272c565b611a8b565b604051610483919061248c565b60405180910390f35b34801561049857600080fd5b506104b360048036038101906104ae919061229e565b611ac8565b6040516104c0919061248c565b60405180910390f35b3480156104d557600080fd5b506104f060048036038101906104eb919061272c565b611ae8565b6040516104fd919061248c565b60405180910390f35b34801561051257600080fd5b5061052d6004803603810190610528919061272c565b611b25565b60405161053a919061248c565b60405180910390f35b34801561054f57600080fd5b50610558611b62565b604051610565919061248c565b60405180910390f35b34801561057a57600080fd5b50610583611b68565b6040516105909190612d77565b60405180910390f35b3480156105a557600080fd5b506105c060048036038101906105bb919061229e565b611ce4565b6040516105cd91906125d5565b60405180910390f35b6105de611d8c565b005b600260205280600052604060002060009150905080600001549080600101805461060990612dc8565b80601f016020809104026020016040519081016040528092919081815260200182805461063590612dc8565b80156106825780601f1061065757610100808354040283529160200191610682565b820191906000526020600020905b81548152906001019060200180831161066557829003601f168201915b5050505050908060050160009054906101000a900460ff16908060060180546106aa90612dc8565b80601f01602080910402602001604051908101604052809291908181526020018280546106d690612dc8565b80156107235780601f106106f857610100808354040283529160200191610723565b820191906000526020600020905b81548152906001019060200180831161070657829003601f168201915b5050505050908060070154905085565b60006002600083815260200190815260200160002060050160009054906101000a900460ff169050919050565b60046020528060005260406000206000915090505481565b606060026000838152602001908152602001600020600201805480602002602001604051908101604052809291908181526020016000905b8282101561085c5783829060005260206000200180546107cf90612dc8565b80601f01602080910402602001604051908101604052809291908181526020018280546107fb90612dc8565b80156108485780601f1061081d57610100808354040283529160200191610848565b820191906000526020600020905b81548152906001019060200180831161082b57829003601f168201915b5050505050815260200190600101906107b0565b505050509050919050565b6000600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050600081116108ee576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108e590612e45565b60405180910390fd5b6000600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060003373ffffffffffffffffffffffffffffffffffffffff168260405161095990612e96565b60006040518083038185875af1925050503d8060008114610996576040519150601f19603f3d011682016040523d82523d6000602084013e61099b565b606091505b50509050806109df576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109d690612ef7565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff167f7fcf532c15f0a6db0bd6d0e038bea71d30d808c7d98cb3bf7268a95bf5081b6583604051610a25919061248c565b60405180910390a25050565b6060600260008381526020019081526020016000206001018054610a5490612dc8565b80601f0160208091040260200160405190810160405280929190818152602001828054610a8090612dc8565b8015610acd5780601f10610aa257610100808354040283529160200191610acd565b820191906000526020600020905b815481529060010190602001808311610ab057829003601f168201915b50505050509050919050565b60065481565b60003411610b22576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b1990612f89565b60405180910390fd5b60006002600084815260200190815260200160002090508060050160009054906101000a900460ff1615610b8b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b8290612ff5565b60405180910390fd5b34600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610c0d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c0490613061565b60405180910390fd5b60008160030183604051610c2191906130bd565b908152602001604051809103902054905060008111610c75576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c6c90613120565b60405180910390fd5b348260040184604051610c8891906130bd565b90815260200160405180910390206000828254610ca5919061316f565b9250508190555034826007016000828254610cc0919061316f565b92505081905550600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518060600160405280868152602001858152602001348152509080600181540180825580915050600190039060005260206000209060030201600090919091909150600082015181600001556020820151816001019081610d69919061334f565b506040820151816002015550506000805b6001600087815260200190815260200160002080549050811015610e30573373ffffffffffffffffffffffffffffffffffffffff16600160008881526020019081526020016000208281548110610dd457610dd3613421565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610e235760019150610e30565b8080600101915050610d7a565b5080610eef5760016000868152602001908152602001600020339080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550843373ffffffffffffffffffffffffffffffffffffffff167fa48418fa780c651bef841ca082131ed20bc20c620dd0459bba9ab78550f5d62360405160405180910390a35b610ef885611e75565b3373ffffffffffffffffffffffffffffffffffffffff167f02337a2de26093979fec9e25c4d2c7b6a8bd0c2fd9b3214ded831ac4f3d391e7868634604051610f4293929190612c05565b60405180910390a25050505050565b60006060600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080805480602002602001604051908101604052809291908181526020016000905b828210156110cd57838290600052602060002090600302016040518060600160405290816000820154815260200160018201805461103290612dc8565b80601f016020809104026020016040519081016040528092919081815260200182805461105e90612dc8565b80156110ab5780601f10611080576101008083540402835291602001916110ab565b820191906000526020600020905b81548152906001019060200180831161108e57829003601f168201915b5050505050815260200160028201548152505081526020019060010190610ff5565b50505050905091509150915091565b6060600060065467ffffffffffffffff8111156110fc576110fb612601565b5b60405190808252806020026020018201604052801561112a5781602001602082028036833780820191505090505b50905060005b60065481101561117d57600260008281526020019081526020016000206000015482828151811061116457611163613421565b5b6020026020010181815250508080600101915050611130565b508091505090565b60018151116111c9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016111c0906134c2565b60405180910390fd5b600060026000600654815260200190815260200160002090506006548160000181905550828160010190816111fe919061334f565b5081816002019080519060200190611217929190612150565b5060008160050160006101000a81548160ff02191690831515021790555060005b82518110156112ff576718fae27693b400008260030184838151811061126157611260613421565b5b602002602001015160405161127691906130bd565b908152602001604051809103902081905550671bc16d674ec80000826004018483815181106112a8576112a7613421565b5b60200260200101516040516112bd91906130bd565b908152602001604051809103902081905550671bc16d674ec800008260070160008282546112eb919061316f565b925050819055508080600101915050611238565b507f9ce3d6db707bca4ba0eabed8fba1e2c013a1c0bc1132a764170a7854d198c73d600654846040516113339291906134e2565b60405180910390a16006600081548092919061134e90613512565b9190505550505050565b6001602052816000526040600020818154811061137457600080fd5b906000526020600020016000915091509054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60036020528060005260406000206000915090508060000154908060010180546113cf90612dc8565b80601f01602080910402602001604051908101604052809291908181526020018280546113fb90612dc8565b80156114485780601f1061141d57610100808354040283529160200191611448565b820191906000526020600020905b81548152906001019060200180831161142b57829003601f168201915b50505050509080600201805461145d90612dc8565b80601f016020809104026020016040519081016040528092919081815260200182805461148990612dc8565b80156114d65780601f106114ab576101008083540402835291602001916114d6565b820191906000526020600020905b8154815290600101906020018083116114b957829003601f168201915b5050505050908060030154905084565b60006002600084815260200190815260200160002090508060050160009054906101000a900460ff161561154f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611546906135a6565b60405180910390fd5b6000805b82600201805490508110156115ba5783805190602001208360020182815481106115805761157f613421565b5b90600052602060002001604051611597919061365e565b6040518091039020036115ad57600191506115ba565b8080600101915050611553565b50806115fb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016115f2906136c1565b60405180910390fd5b60018260050160006101000a81548160ff02191690831515021790555082826006019081611629919061334f565b506000826007015490506000836004018560405161164791906130bd565b9081526020016040518091039020549050600081111561187857600081670de0b6b3a76400008461167891906136e1565b6116829190613752565b905060005b6001600089815260200190815260200160002080549050811015611875576000600160008a815260200190815260200160002082815481106116cc576116cb613421565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060005b600560008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080549050811015611866576000600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811061179957611798613421565b5b906000526020600020906003020190508a81600001541480156117da57508980519060200120816001016040516117d0919061365e565b6040518091039020145b1561185857670de0b6b3a76400008582600201546117f891906136e1565b6118029190613752565b600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254611850919061316f565b925050819055505b5080806001019150506116fc565b50508080600101915050611687565b50505b604051806080016040528087815260200185600101805461189890612dc8565b80601f01602080910402602001604051908101604052809291908181526020018280546118c490612dc8565b80156119115780601f106118e657610100808354040283529160200191611911565b820191906000526020600020905b8154815290600101906020018083116118f457829003601f168201915b505050505081526020018681526020018381525060036000888152602001908152602001600020600082015181600001556020820151816001019081611957919061334f565b50604082015181600201908161196d919061334f565b50606082015181600301559050507f6114f784a71fbe68b067dbeb1d4dd3f1d2ad02dc6ced831a7c4f5168fa1c717186866040516119ac9291906134e2565b60405180910390a1505050505050565b600560205281600052604060002081815481106119d857600080fd5b906000526020600020906003020160009150915050806000015490806001018054611a0290612dc8565b80601f0160208091040260200160405190810160405280929190818152602001828054611a2e90612dc8565b8015611a7b5780601f10611a5057610100808354040283529160200191611a7b565b820191906000526020600020905b815481529060010190602001808311611a5e57829003601f168201915b5050505050908060020154905083565b60006002600084815260200190815260200160002060040182604051611ab191906130bd565b908152602001604051809103902054905092915050565b600060026000838152602001908152602001600020600701549050919050565b60006002600084815260200190815260200160002060030182604051611b0e91906130bd565b908152602001604051809103902054905092915050565b60006002600084815260200190815260200160002060030182604051611b4b91906130bd565b908152602001604051809103902054905092915050565b60005481565b6060600060065467ffffffffffffffff811115611b8857611b87612601565b5b604051908082528060200260200182016040528015611bc157816020015b611bae6121a9565b815260200190600190039081611ba65790505b50905060005b600654811015611cdc576000600260008381526020019081526020016000209050604051806080016040528082600001548152602001826001018054611c0c90612dc8565b80601f0160208091040260200160405190810160405280929190818152602001828054611c3890612dc8565b8015611c855780601f10611c5a57610100808354040283529160200191611c85565b820191906000526020600020905b815481529060010190602001808311611c6857829003601f168201915b505050505081526020018260050160009054906101000a900460ff16151581526020018260070154815250838381518110611cc357611cc2613421565b5b6020026020010181905250508080600101915050611bc7565b508091505090565b6060600260008381526020019081526020016000206006018054611d0790612dc8565b80601f0160208091040260200160405190810160405280929190818152602001828054611d3390612dc8565b8015611d805780601f10611d5557610100808354040283529160200191611d80565b820191906000526020600020905b815481529060010190602001808311611d6357829003601f168201915b50505050509050919050565b60003411611dcf576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611dc6906137f5565b60405180910390fd5b34600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254611e1e919061316f565b925050819055503373ffffffffffffffffffffffffffffffffffffffff167fe1fffcc4923d04b559f4d29a8bfc6cda04eb5b0d3c460751c2402c5c5cc9109c34604051611e6b919061248c565b60405180910390a2565b60006002600083815260200190815260200160002090506000816007015490506000805b8360020180549050811015611f025783600401846002018281548110611ec257611ec1613421565b5b90600052602060002001604051611ed99190613898565b90815260200160405180910390205482611ef3919061316f565b91508080600101915050611e99565b5060006005905060005b8460020180549050811015612148576000856002018281548110611f3357611f32613421565b5b906000526020600020018054611f4890612dc8565b80601f0160208091040260200160405190810160405280929190818152602001828054611f7490612dc8565b8015611fc15780601f10611f9657610100808354040283529160200191611fc1565b820191906000526020600020905b815481529060010190602001808311611fa457829003601f168201915b5050505050905060008660040182604051611fdc91906130bd565b908152602001604051809103902054905060008111156120b057600081670de0b6b3a76400008861200d91906136e1565b6120179190613752565b90506000606486606461202a91906138af565b8361203591906136e1565b61203f9190613752565b9050670e043da61725000081101561208357670e043da617250000896003018560405161206c91906130bd565b9081526020016040518091039020819055506120a9565b80896003018560405161209691906130bd565b9081526020016040518091039020819055505b50506120de565b671bc16d674ec8000087600301836040516120cb91906130bd565b9081526020016040518091039020819055505b7fa18319e08e87fbc9bc32261096e68265c97c77633b5f63f2306861ccc06580158883896003018560405161211391906130bd565b90815260200160405180910390205460405161213193929190612c05565b60405180910390a150508080600101915050611f0c565b505050505050565b828054828255906000526020600020908101928215612198579160200282015b82811115612197578251829081612187919061334f565b5091602001919060010190612170565b5b5090506121a591906121d3565b5090565b60405180608001604052806000815260200160608152602001600015158152602001600081525090565b5b808211156121f357600081816121ea91906121f7565b506001016121d4565b5090565b50805461220390612dc8565b6000825580601f106122155750612234565b601f0160209004906000526020600020908101906122339190612237565b5b50565b5b80821115612250576000816000905550600101612238565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61227b81612268565b811461228657600080fd5b50565b60008135905061229881612272565b92915050565b6000602082840312156122b4576122b361225e565b5b60006122c284828501612289565b91505092915050565b6122d481612268565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b838110156123145780820151818401526020810190506122f9565b60008484015250505050565b6000601f19601f8301169050919050565b600061233c826122da565b61234681856122e5565b93506123568185602086016122f6565b61235f81612320565b840191505092915050565b60008115159050919050565b61237f8161236a565b82525050565b600060a08201905061239a60008301886122cb565b81810360208301526123ac8187612331565b90506123bb6040830186612376565b81810360608301526123cd8185612331565b90506123dc60808301846122cb565b9695505050505050565b60006020820190506123fb6000830184612376565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061242c82612401565b9050919050565b61243c81612421565b811461244757600080fd5b50565b60008135905061245981612433565b92915050565b6000602082840312156124755761247461225e565b5b60006124838482850161244a565b91505092915050565b60006020820190506124a160008301846122cb565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600082825260208201905092915050565b60006124ef826122da565b6124f981856124d3565b93506125098185602086016122f6565b61251281612320565b840191505092915050565b600061252983836124e4565b905092915050565b6000602082019050919050565b6000612549826124a7565b61255381856124b2565b935083602082028501612565856124c3565b8060005b858110156125a15784840389528151612582858261251d565b945061258d83612531565b925060208a01995050600181019050612569565b50829750879550505050505092915050565b600060208201905081810360008301526125cd818461253e565b905092915050565b600060208201905081810360008301526125ef8184612331565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61263982612320565b810181811067ffffffffffffffff8211171561265857612657612601565b5b80604052505050565b600061266b612254565b90506126778282612630565b919050565b600067ffffffffffffffff82111561269757612696612601565b5b6126a082612320565b9050602081019050919050565b82818337600083830152505050565b60006126cf6126ca8461267c565b612661565b9050828152602081018484840111156126eb576126ea6125fc565b5b6126f68482856126ad565b509392505050565b600082601f830112612713576127126125f7565b5b81356127238482602086016126bc565b91505092915050565b600080604083850312156127435761274261225e565b5b600061275185828601612289565b925050602083013567ffffffffffffffff81111561277257612771612263565b5b61277e858286016126fe565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6127bd81612268565b82525050565b60006060830160008301516127db60008601826127b4565b50602083015184820360208601526127f382826124e4565b915050604083015161280860408601826127b4565b508091505092915050565b600061281f83836127c3565b905092915050565b6000602082019050919050565b600061283f82612788565b6128498185612793565b93508360208202850161285b856127a4565b8060005b8581101561289757848403895281516128788582612813565b945061288383612827565b925060208a0199505060018101905061285f565b50829750879550505050505092915050565b60006040820190506128be60008301856122cb565b81810360208301526128d08184612834565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600061291183836127b4565b60208301905092915050565b6000602082019050919050565b6000612935826128d9565b61293f81856128e4565b935061294a836128f5565b8060005b8381101561297b5781516129628882612905565b975061296d8361291d565b92505060018101905061294e565b5085935050505092915050565b600060208201905081810360008301526129a2818461292a565b905092915050565b600067ffffffffffffffff8211156129c5576129c4612601565b5b602082029050602081019050919050565b600080fd5b60006129ee6129e9846129aa565b612661565b90508083825260208201905060208402830185811115612a1157612a106129d6565b5b835b81811015612a5857803567ffffffffffffffff811115612a3657612a356125f7565b5b808601612a4389826126fe565b85526020850194505050602081019050612a13565b5050509392505050565b600082601f830112612a7757612a766125f7565b5b8135612a878482602086016129db565b91505092915050565b60008060408385031215612aa757612aa661225e565b5b600083013567ffffffffffffffff811115612ac557612ac4612263565b5b612ad1858286016126fe565b925050602083013567ffffffffffffffff811115612af257612af1612263565b5b612afe85828601612a62565b9150509250929050565b60008060408385031215612b1f57612b1e61225e565b5b6000612b2d85828601612289565b9250506020612b3e85828601612289565b9150509250929050565b612b5181612421565b82525050565b6000602082019050612b6c6000830184612b48565b92915050565b6000608082019050612b8760008301876122cb565b8181036020830152612b998186612331565b90508181036040830152612bad8185612331565b9050612bbc60608301846122cb565b95945050505050565b60008060408385031215612bdc57612bdb61225e565b5b6000612bea8582860161244a565b9250506020612bfb85828601612289565b9150509250929050565b6000606082019050612c1a60008301866122cb565b8181036020830152612c2c8185612331565b9050612c3b60408301846122cb565b949350505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b612c788161236a565b82525050565b6000608083016000830151612c9660008601826127b4565b5060208301518482036020860152612cae82826124e4565b9150506040830151612cc36040860182612c6f565b506060830151612cd660608601826127b4565b508091505092915050565b6000612ced8383612c7e565b905092915050565b6000602082019050919050565b6000612d0d82612c43565b612d178185612c4e565b935083602082028501612d2985612c5f565b8060005b85811015612d655784840389528151612d468582612ce1565b9450612d5183612cf5565b925060208a01995050600181019050612d2d565b50829750879550505050505092915050565b60006020820190508181036000830152612d918184612d02565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680612de057607f821691505b602082108103612df357612df2612d99565b5b50919050565b7f4e6f2062616c616e636520746f20776974686472617700000000000000000000600082015250565b6000612e2f6016836122e5565b9150612e3a82612df9565b602082019050919050565b60006020820190508181036000830152612e5e81612e22565b9050919050565b600081905092915050565b50565b6000612e80600083612e65565b9150612e8b82612e70565b600082019050919050565b6000612ea182612e73565b9150819050919050565b7f5769746864726177616c206661696c6564000000000000000000000000000000600082015250565b6000612ee16011836122e5565b9150612eec82612eab565b602082019050919050565b60006020820190508181036000830152612f1081612ed4565b9050919050565b7f42657420616d6f756e74206d7573742062652067726561746572207468616e2060008201527f7a65726f00000000000000000000000000000000000000000000000000000000602082015250565b6000612f736024836122e5565b9150612f7e82612f17565b604082019050919050565b60006020820190508181036000830152612fa281612f66565b9050919050565b7f4576656e7420697320616c72656164792066696e616c697a6564000000000000600082015250565b6000612fdf601a836122e5565b9150612fea82612fa9565b602082019050919050565b6000602082019050818103600083015261300e81612fd2565b9050919050565b7f496e73756666696369656e742062616c616e6365000000000000000000000000600082015250565b600061304b6014836122e5565b915061305682613015565b602082019050919050565b6000602082019050818103600083015261307a8161303e565b9050919050565b600081905092915050565b6000613097826122da565b6130a18185613081565b93506130b18185602086016122f6565b80840191505092915050565b60006130c9828461308c565b915081905092915050565b7f496e76616c6964206f7574636f6d650000000000000000000000000000000000600082015250565b600061310a600f836122e5565b9150613115826130d4565b602082019050919050565b60006020820190508181036000830152613139816130fd565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061317a82612268565b915061318583612268565b925082820190508082111561319d5761319c613140565b5b92915050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026132057fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826131c8565b61320f86836131c8565b95508019841693508086168417925050509392505050565b6000819050919050565b600061324c61324761324284612268565b613227565b612268565b9050919050565b6000819050919050565b61326683613231565b61327a61327282613253565b8484546131d5565b825550505050565b600090565b61328f613282565b61329a81848461325d565b505050565b5b818110156132be576132b3600082613287565b6001810190506132a0565b5050565b601f821115613303576132d4816131a3565b6132dd846131b8565b810160208510156132ec578190505b6133006132f8856131b8565b83018261329f565b50505b505050565b600082821c905092915050565b600061332660001984600802613308565b1980831691505092915050565b600061333f8383613315565b9150826002028217905092915050565b613358826122da565b67ffffffffffffffff81111561337157613370612601565b5b61337b8254612dc8565b6133868282856132c2565b600060209050601f8311600181146133b957600084156133a7578287015190505b6133b18582613333565b865550613419565b601f1984166133c7866131a3565b60005b828110156133ef578489015182556001820191506020850194506020810190506133ca565b8683101561340c5784890151613408601f891682613315565b8355505b6001600288020188555050505b505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4576656e74206d7573742068617665206174206c656173742074776f206f757460008201527f636f6d6573000000000000000000000000000000000000000000000000000000602082015250565b60006134ac6025836122e5565b91506134b782613450565b604082019050919050565b600060208201905081810360008301526134db8161349f565b9050919050565b60006040820190506134f760008301856122cb565b81810360208301526135098184612331565b90509392505050565b600061351d82612268565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361354f5761354e613140565b5b600182019050919050565b7f4576656e7420616c72656164792066696e616c697a6564000000000000000000600082015250565b60006135906017836122e5565b915061359b8261355a565b602082019050919050565b600060208201905081810360008301526135bf81613583565b9050919050565b60008190508160005260206000209050919050565b600081546135e881612dc8565b6135f28186612e65565b9450600182166000811461360d576001811461362257613655565b60ff1983168652811515820286019350613655565b61362b856135c6565b60005b8381101561364d5781548189015260018201915060208101905061362e565b838801955050505b50505092915050565b600061366a82846135db565b915081905092915050565b7f496e76616c696420726573756c74000000000000000000000000000000000000600082015250565b60006136ab600e836122e5565b91506136b682613675565b602082019050919050565b600060208201905081810360008301526136da8161369e565b9050919050565b60006136ec82612268565b91506136f783612268565b925082820261370581612268565b9150828204841483151761371c5761371b613140565b5b5092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b600061375d82612268565b915061376883612268565b92508261377857613777613723565b5b828204905092915050565b7f4465706f736974206d7573742062652067726561746572207468616e207a657260008201527f6f00000000000000000000000000000000000000000000000000000000000000602082015250565b60006137df6021836122e5565b91506137ea82613783565b604082019050919050565b6000602082019050818103600083015261380e816137d2565b9050919050565b6000815461382281612dc8565b61382c8186613081565b94506001821660008114613847576001811461385c5761388f565b60ff198316865281151582028601935061388f565b613865856131a3565b60005b8381101561388757815481890152600182019150602081019050613868565b838801955050505b50505092915050565b60006138a48284613815565b915081905092915050565b60006138ba82612268565b91506138c583612268565b92508282039050818111156138dd576138dc613140565b5b9291505056fea26469706673582212200c0f05bd066d1186490b4bf38aa880f0c6f375023453b86a0b20e7540d4e1e4b64736f6c634300081b0033";

    // Constructor to load the contract
    protected BettingSystemDTO(String contractAddress, Web3j web3j, org.web3j.tx.TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BIN, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static BettingSystemDTO load(String contractAddress, Web3j web3j, org.web3j.tx.TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BettingSystemDTO(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    // Deploy method
    public static RemoteCall<BettingSystemDTO> deploy(Web3j web3j, org.web3j.tx.TransactionManager transactionManager, ContractGasProvider gasProvider, BigInteger initialValue) {
        return deployRemoteCall(BettingSystemDTO.class, web3j, transactionManager, gasProvider, BIN, ABI, initialValue);
    }

    // Example method: createEvent
    public RemoteCall<TransactionReceipt> createEvent(String name, List<String> outcomes) {
        List<Type> inputParameters = Arrays.asList(
                new Utf8String(name),
                new org.web3j.abi.datatypes.DynamicArray<>(
                        Utf8String.class,
                        outcomes.stream().map(Utf8String::new).toList()
                )
        );
        return executeRemoteCallTransaction(createFunction("createEvent", inputParameters, Collections.emptyList()));
    }

    // Example method: getOdds
    public RemoteCall<BigInteger> getOdds(BigInteger eventId, String outcome) {
        List<Type> inputParameters = Arrays.asList(
                new Uint256(eventId),
                new Utf8String(outcome)
        );
        return executeRemoteCallSingleValueReturn(createFunction("getOdds", inputParameters, Collections.singletonList(new TypeReference<Uint256>() {})), BigInteger.class);
    }

    // Example method: placeBet
    public RemoteCall<TransactionReceipt> placeBet(BigInteger eventId, String outcome, BigInteger amount) {
        BigInteger amountInWei = Convert.toWei(amount.toString(), Convert.Unit.ETHER).toBigInteger();
        System.out.println("Amount in Wei: " + amountInWei); // Log para conferência
        Function function = new Function(
                "placeBet", // Nome da função no contrato
                Arrays.asList(
                        new Uint256(eventId),    // Parâmetro eventId (uint256)
                        new Utf8String(outcome), // Parâmetro outcome (string)
                        new Uint256(amountInWei)      // Parâmetro amount (uint256)
                ),
                Collections.emptyList() // Sem retorno
        );

        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getMyBalance() {
        Function function = new Function(
                "getMyBalance", // Nome da função no contrato
                Collections.emptyList(), // Sem parâmetros de entrada
                Arrays.asList(new TypeReference<Uint256>() {}) // Tipo de retorno: Uint256
        );

        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    // Example method: finalizeEvent
    public RemoteCall<TransactionReceipt> finalizeEvent(BigInteger eventId, String result) {
        List<Type> inputParameters = Arrays.asList(
                new Uint256(eventId),
                new Utf8String(result)
        );
        return executeRemoteCallTransaction(createFunction("finalizeEvent", inputParameters, Collections.emptyList()));
    }

    // Example method: deposit
    public RemoteCall<TransactionReceipt> deposit(BigInteger amount) {
        // Converter o valor em ETHER para WEI
        BigInteger valueInWei = Convert.toWei(amount.toString(), Convert.Unit.ETHER).toBigInteger();

        // Executar a transação enviando o valor em WEI
        return executeRemoteCallTransaction(
                new Function(
                        "deposit",
                        Collections.emptyList(), // A função deposit não tem parâmetros
                        Collections.emptyList()
                ),
                valueInWei // Envia o valor em Wei na transação
        );
    }


    // Example method: withdraw
    public RemoteCall<TransactionReceipt> withdraw() {
        return executeRemoteCallTransaction(createFunction("withdraw", Collections.emptyList(), Collections.emptyList()));
    }

    // Utility method to create a Solidity function
    private Function createFunction(String methodName, List<Type> inputParameters, List<TypeReference<?>> outputParameters) {
        return new Function(methodName, inputParameters, outputParameters);
    }

    public RemoteCall<List<String>> getEventOutcomes(BigInteger eventId) {
        List<Type> inputParameters = Collections.singletonList(new Uint256(eventId));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<DynamicArray<Utf8String>>() {});

        return new RemoteCall<>(() -> {
            List<Type> results = executeCallMultipleValueReturn(createFunction("getEventOutcomes", inputParameters, outputParameters));
            List<Utf8String> utf8Strings = (List<Utf8String>) results.get(0).getValue();

            // Convert Utf8String to List<String>
            return utf8Strings.stream()
                    .map(Utf8String::getValue)
                    .toList();
        });
    }

    public RemoteCall<List<BigInteger>> getAllEventIds() {
        // Definição dos parâmetros de entrada (vazio, pois getAllEventIds não recebe nenhum parâmetro)
        List<Type> inputParameters = Collections.emptyList();

        // Definição do tipo de retorno como DynamicArray de Uint256
        List<TypeReference<?>> outputParameters = Collections.singletonList(
                new TypeReference<DynamicArray<Uint256>>() {}
        );

        // Criação e execução do RemoteCall
        return new RemoteCall<>(() -> {
            // Chama a função Solidity e retorna os valores
            List<Type> results = executeCallMultipleValueReturn(
                    createFunction("getAllEventIds", inputParameters, outputParameters)
            );

            // Extrai os valores do DynamicArray<Uint256>
            List<Uint256> uint256List = (List<Uint256>) results.get(0).getValue();

            // Converte List<Uint256> para List<BigInteger>
            return uint256List.stream()
                    .map(Uint256::getValue)
                    .toList();
        });
    }

    // Função para retornar o nome do evento
    public RemoteCall<String> getEventName(BigInteger eventId) {
        // Definindo os parâmetros de entrada
        List<Type> inputParameters = Collections.singletonList(new Uint256(eventId));

        // Definindo os parâmetros de saída (retorno: string)
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Utf8String>() {});

        // Retornando o resultado da chamada remota
        return executeRemoteCallSingleValueReturn(
                createFunction("getEventName", inputParameters, outputParameters),
                String.class
        );
    }


    // Função para retornar as odds de um resultado específico
    public RemoteCall<BigInteger> getEventOdds(BigInteger eventId, String outcome) {
        List<Type> inputParameters = List.of(new Uint256(eventId), new Utf8String(outcome));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Uint256>() {});

        return executeRemoteCallSingleValueReturn(
                createFunction("getEventOdds", inputParameters, outputParameters),
                BigInteger.class
        );
    }


    // Função para retornar o total apostado em um resultado específico
    public RemoteCall<BigInteger> getEventTotalBets(BigInteger eventId, String outcome) {
        List<Type> inputParameters = List.of(new Uint256(eventId), new Utf8String(outcome));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Uint256>() {});

        return executeRemoteCallSingleValueReturn(
                createFunction("getEventTotalBets", inputParameters, outputParameters),
                BigInteger.class
        );
    }


    // Função para verificar se o evento está finalizado
    public RemoteCall<Boolean> isEventFinalized(BigInteger eventId) {
        List<Type> inputParameters = Collections.singletonList(new Uint256(eventId));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Bool>() {});

        return executeRemoteCallSingleValueReturn(
                createFunction("isEventFinalized", inputParameters, outputParameters),
                Boolean.class
        );
    }

    // Função para retornar o resultado de um evento finalizado
    public RemoteCall<String> getEventResult(BigInteger eventId) {
        List<Type> inputParameters = Collections.singletonList(new Uint256(eventId));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Utf8String>() {});

        return executeRemoteCallSingleValueReturn(
                createFunction("getEventResult", inputParameters, outputParameters),
                String.class
        );
    }


    // Função para retornar o total do pool de um evento
    public RemoteCall<BigInteger> getEventTotalPool(BigInteger eventId) {
        List<Type> inputParameters = Collections.singletonList(new Uint256(eventId));
        List<TypeReference<?>> outputParameters = Collections.singletonList(new TypeReference<Uint256>() {});

        return executeRemoteCallSingleValueReturn(
                createFunction("getEventTotalPool", inputParameters, outputParameters),
                BigInteger.class
        );
    }


}
