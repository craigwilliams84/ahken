#!/usr/bin/env bash
rm -rf compiled
mkdir compiled
solc EventEmitter.sol --overwrite --bin --abi --optimize -o compiled/
web3j solidity generate --javaTypes compiled/EventEmitter.bin compiled/EventEmitter.abi -o ../java -p io.kauri.ahken.integrationtest