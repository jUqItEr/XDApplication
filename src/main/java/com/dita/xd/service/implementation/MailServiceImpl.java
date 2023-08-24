package com.dita.xd.service.implementation;

import com.dita.xd.service.MailService;
import com.dita.xd.util.mail.MailSender;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.stream.IntStream;

public class MailServiceImpl implements MailService {
    DBConnectionServiceImpl pool = null;

    public MailServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public boolean appendCode(String email, String code) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO code_tbl VALUES (?, ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, code);
            flag =  pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean checkValidation(String email, String code) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM code_tbl WHERE email = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String dbCode = rs.getString(2);
                flag = dbCode.equals(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        /* If the validation check is successful, It should be deleted code from temporary database. */
        if (flag) {
            flag = revokeCode(email);
        }
        return flag;
    }

    @Override
    public boolean isValidEmail(String email) {
        boolean result = true;

        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        }  catch (AddressException e) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean revokeCode(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM code_tbl WHERE email = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean sendRequestCode(String email) {
        MailSender sender = new MailSender("xdditaa@gmail.com", "ssmskghgvfvuufjc", email);
        String code = generateCode();
        boolean result = false;

        /* Check the validation of e-mail address */
        if (isValidEmail(email)) {
            result = sender.sendMail(code);
            result |= appendCode(email, code);
        }
        return result;
    }

    @Override
    public String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, 6)
                .forEach(value -> sb.append(random.nextInt(10)));
        return sb.toString();
    }
}
