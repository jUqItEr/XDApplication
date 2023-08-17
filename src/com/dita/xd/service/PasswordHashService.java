package com.dita.xd.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * <p>단방향 암복호화 모듈</p>
 *
 * @author 강기석
 * */
public class PasswordHashService {
    private static final UUID SERVICE_UUID = UUID.randomUUID();
    private static final int SALT_LENGTH = 20;
    private static final int KEY_STRETCH_COUNT = 10;

    private final Base64.Decoder decoder;
    private final Base64.Encoder encoder;


    public PasswordHashService() {
        this.decoder = Base64.getDecoder();
        this.encoder = Base64.getEncoder();
    }   // -- Constructor


    public String encryptPassword(String pwd) {
        String salt = getSalt();
        String newPassword = pwd + salt;

        try {
            for (int i = 0; i < KEY_STRETCH_COUNT; ++i) {
                newPassword = getSHA256HashValue(newPassword);
            }   // -- for
        } catch (NoSuchAlgorithmException ex) {
            newPassword = null;
            ex.printStackTrace();
        }   // -- try-catch
        newPassword = getBase64EncodedText(salt + newPassword);

        return newPassword;
    }   // -- encryptPassword

    /**
     * <p>Base64 암호기로 암호회된 평문을 되돌려 놓는 메소드입니다.</p>
     *
     * @param  msg Base64로 암호화된 문자열
     * @return     Base64 복호기로 해제된 평문
     * */
    public String getBase64DecodedText(String msg) {
        byte[] decodedBytes = decoder.decode(msg);
        return new String(decodedBytes);
    }   // --getBase64DecodedText

    public String getBase64EncodedText(String msg) {
        return encoder.encodeToString(msg.getBytes());
    }   // --getBase64EncodedText

    public String getHashValue(String msg, String algorithm) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(algorithm);

        md.update(msg.getBytes());

        for (byte b : md.digest()) {
            sb.append(String.format("%02x", b));
        }   // -- for
        return sb.toString();
    }

    public byte[] getSaltByteArray() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];

        // Generate random bytes.
        sr.nextBytes(salt);

        return salt;
    }   // -- getSaltByteArray

    public String getSalt() {
        StringBuilder sb = new StringBuilder();

        for (byte b : getSaltByteArray()) {
            sb.append(String.format("%02x", b));
        }   // -- for
        return sb.toString();
    }   // -- getSalt

    public String getSHA256HashValue(String msg) throws NoSuchAlgorithmException {
        return this.getHashValue(msg, "SHA-256");
    }
}