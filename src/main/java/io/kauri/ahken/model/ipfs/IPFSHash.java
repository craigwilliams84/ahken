package io.kauri.ahken.model.ipfs;

import lombok.Getter;

public class IPFSHash {

    @Getter
    private String hash;

    private IPFSHash(String hash) {
        this.hash = hash;
    }

    public static IPFSHash of(String ipfsHash) {
        return new IPFSHash(ipfsHash);
    }
}
