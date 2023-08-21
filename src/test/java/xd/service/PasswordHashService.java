package xd.service;

/**
 * <p>
 *     The implementation for 'PasswordHashService'.
 * </p>
 *
 * @author      jUqItEr (Ki-seok Kang)
 * @version     1.0.0
 * */
public interface PasswordHashService extends Service {
    boolean isValidPassword(String pwd, String encodedString);
    String generatePassword(String pwd);
    String generatePassword(String pwd, String salt);
    String getSalt();
}
