pragma solidity ^0.4.24;

contract EventEmitter {

    function emitEvent(string ipfsHash) external {
        emit TestEvent(msg.sender, ipfsHash);
    }

    event TestEvent(address indexed sender, string ipfsHash);
}