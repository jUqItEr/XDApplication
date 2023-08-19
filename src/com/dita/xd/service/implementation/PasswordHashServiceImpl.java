package com.dita.xd.service.implementation;

/**
 * <p>
 *     The implementation for 'PasswordHashService'.
 * </p>
 *
 * @author      jUqItEr (Ki-seok Kang)
 * @version     1.0.0
 * */
public interface PasswordHashServiceImpl extends ServiceImpl {
    boolean isValidPassword(String pwd, String encodedString);
    String generatePassword(String pwd);
    String generatePassword(String pwd, String salt);
    String getSalt();
}
