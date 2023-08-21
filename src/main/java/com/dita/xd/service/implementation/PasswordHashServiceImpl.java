package com.dita.xd.service.implementation;

import com.dita.xd.service.PasswordHashService;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * <p>
 *     Service to generate SHA-256 hash password for storing database management systems.<br>
 *     This class should be implement to 'PasswordHashServiceImpl'.
 * </p>
 *
 * @author     jUqItEr (Ki-seok Kang)
 * @version    1.2.1
 * */
public class PasswordHashServiceImpl implements PasswordHashService {
    private static final int SALT_LENGTH = 20;
    private static final int KEY_STRETCH_COUNT = 10;

    private final Base64.Decoder decoder;
    private final Base64.Encoder encoder;

    public PasswordHashServiceImpl() {
        this.decoder = Base64.getDecoder();
        this.encoder = Base64.getEncoder();
    }   // -- End of constructor

    private boolean isValidBase64String(String text) {
        Pattern pattern = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }   // -- End of function (isValidBase64String)

    private String decodeToBase64String(String text) {
        byte[] decodedBytes = decoder.decode(text);
        return new String(decodedBytes);
    }   // -- End of function (decodeToBase64String)

    private String encodeToBase64String(String text) {
        return encoder.encodeToString(text.getBytes());
    }   // -- End of function (encodeToBase64String)

    private String getHashValue(String text, String algorithm) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(algorithm);

        md.update(text.getBytes());

        for (byte b : md.digest()) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private String getSha256HashValue(String text) throws NoSuchAlgorithmException {
        return getHashValue(text, "SHA-256");
    }

    private byte[] getSaltByteArray() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];

        sr.nextBytes(salt);
        return salt;
    }   // -- End of function (getSaltByteArray)

    private ByteArrayInputStream getSaltByteStream() {
        return new ByteArrayInputStream(getSaltByteArray());
    }   // -- End of function (getSaltByteStream)


    @Override
    public boolean isValidPassword(String pwd, String encodedString) {
        boolean flag = isValidBase64String(encodedString);

        if (flag) {
            String decodedString = decodeToBase64String(encodedString);
            String salt = decodedString.substring(0, 40);
            flag = encodedString.equals(generatePassword(pwd, salt));
        }   // -- End of if
        return flag;
    }   // -- End of function (isValidPassword)

    @Override
    public String generatePassword(String pwd) {
        String salt = getSalt();
        return generatePassword(pwd, salt);
    }   // -- End of function (generatePassword)

    @Override
    public String generatePassword(String pwd, String salt) {
        String newPassword = pwd + salt;

        try {
            /* 키 스트레칭 수행 */
            for (int i = 0; i < KEY_STRETCH_COUNT; ++i) {
                newPassword = getSha256HashValue(newPassword);
            }   // -- End of for-loop
            /* 다 끝났으면 base64로 인코딩 수행 */
            newPassword = encodeToBase64String(salt + newPassword);
        } catch (NoSuchAlgorithmException e) {
            /* 오류가 나면 비밀번호를 생성하면 안 됨 */
            newPassword = null;
            e.printStackTrace();
        }   // -- End of try-catch
        return newPassword;
    }   // -- End of function (generatePassword)

    @Override
    public String getSalt() {
        StringBuilder sb = new StringBuilder();
        ByteArrayInputStream saltStream = getSaltByteStream();
        IntStream stream = IntStream.generate(saltStream::read)
                .limit(saltStream.available());
        stream.forEach(value -> sb.append(String.format("%02x", value)));
        return sb.toString();
    }   // -- End of function (getSalt)
}   // -- End of class