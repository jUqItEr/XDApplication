package com.dita.xd.driver;

import com.dita.xd.util.mail.MailSender;

import java.util.Random;

/**
 *
 *
 * @author jUqItEr (Ki-seok Kang)
 * @deprecated For Testing
 */
public class MailDriver {
    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        MailSender sender = new MailSender("pyt773924@gmail.com", "fafdtbxevivmelgx", "kayato8703@gmail.com");

        for (int i = 0; i < 6; ++i) {
            int x = random.nextInt(10);
            sb.append(x);
        }

        sender.sendMail(sb.toString());
    }
}
