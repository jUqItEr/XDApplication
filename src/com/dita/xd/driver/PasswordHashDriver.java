package com.dita.xd.driver;

import com.dita.xd.service.implementation.PasswordHashServiceImpl;

import java.io.*;

/**
 * <p>
 *     Driver to generate SHA-256 hash password for storing database management systems.
 * </p>
 *
 * @author     jUqItEr (Ki-seok Kang)
 * @deprecated For testing
 * @version    1.0.1
 * */
public class PasswordHashDriver {
    public static void main(String[] args) {
        driveHash();
    }

    public static void driveHash() {
        PasswordHashServiceImpl svc = new PasswordHashServiceImpl();
        String path = "src/db.txt";
        String desiredPwd = "1234";

        /*
         * The database system is not constructed yet, so we are test this module to file processing.
         * */
        if (new File(path).exists()) {
            /*
             * If the file 'src/db.txt' is exists, it'll check the validation between generated hash password
             * and new generated hash password.
             * */
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String cmpPwd = "1234";
                String pwd = br.readLine();
                System.out.println(cmpPwd + " is equals to database password: "
                        + svc.isValidPassword(cmpPwd, pwd));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            /*
             * If the file not exists, the system will create new file on 'src/db.txt'.
             * */
            try (FileWriter fw = new FileWriter(path)) {
                String newPwd = svc.generatePassword(desiredPwd);
                System.out.println("Generate New Password!! " + newPwd);
                fw.write(newPwd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
