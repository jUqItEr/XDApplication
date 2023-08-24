package com.dita.xd.driver;

import com.dita.xd.controller.MailController;

/**
 *
 *
 * @author jUqItEr (Ki-seok Kang)
 * @deprecated For Testing
 */
public class MailDriver {
    public static void main(String[] args) {
        MailController controller = new MailController();

        // controller.sendRequestCode("dnjs1174@gmail.com");

        System.out.println(controller.checkRequestCode("dnjs1174@gmail.com", "332726"));
    }
}
