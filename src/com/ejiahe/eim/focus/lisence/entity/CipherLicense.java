package com.ejiahe.eim.focus.lisence.entity;

public  class CipherLicense {

    private String nonce;			// 证书对应某一企业的唯一标识
    private String strategy;		// 策略：解密策略&签名策略，,秘钥
    private String msgSign;			// 签名
    private String lisence; 		// 内容

    public String getNonce() {
        return nonce;
    }
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    public String getStrategy() {
        return strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
    public String getMsgSign() {
        return msgSign;
    }
    public void setMsgSign(String msgSign) {
        this.msgSign = msgSign;
    }
    public String getLisence() {
        return lisence;
    }
    public void setLisence(String lisence) {
        this.lisence = lisence;
    }


}