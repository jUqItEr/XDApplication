package com.dita.xd.view.dialog;

import com.dita.xd.controller.MailController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.frame.LoginFrame;

import javax.mail.Quota;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MailCodeDialog extends JDialog implements LocaleChangeListener {
    private final MailController controller;
    private final String email;

    private ResourceBundle localeBundle;
    private Locale currentLocale;

    private JButton btnCancel;
    private JLabel lblHeader;
    private JLabel lblSeperator;

    private JPanel pnlCode;
    private JPanel pnlMain;


    private JTextField[] textFields = new JTextField[6];

    public MailCodeDialog(Locale locale, String email) {
        this.localeBundle = ResourceBundle.getBundle("language", locale);
        this.controller = new MailController();
        this.email = email;

        initialize();
    }

    private void initialize() {
        int index = 0;

        setLayout(new BorderLayout());
        setSize(new Dimension(500, 350));

        btnCancel = new JButton();

        lblHeader = new JLabel();
        lblSeperator = new JLabel("-");

        pnlCode = new JPanel();
        pnlMain = new JPanel();

        pnlCode.setLayout(new BoxLayout(pnlCode, BoxLayout.X_AXIS));
        pnlCode.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        lblSeperator.setMaximumSize(new Dimension(50, 50));
        lblSeperator.setPreferredSize(new Dimension(50, 50));
        lblSeperator.setHorizontalAlignment(JLabel.CENTER);
        lblSeperator.setVerticalAlignment(JLabel.CENTER);

        pnlCode.add(Box.createVerticalGlue());

        for (int i = 0; i < textFields.length; ++i) {
            textFields[i] = new JTextField();
            textFields[i].setMaximumSize(new Dimension(50, 50));
            textFields[i].setPreferredSize(new Dimension(50, 50));
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    final Object obj = e.getSource();
                    final char ch = e.getKeyChar();
                    final int code = e.getKeyCode();
                    final int length = textFields.length;
                    int index = 0;

                    if (Character.isDigit(ch)) {
                        for (int i = 0; i < length; ++i) {
                            if (obj == textFields[i]) {
                                index = i;
                                i = length;
                            }
                        }
                        if (index < length - 1) {
                            textFields[index + 1].requestFocus();
                        } else {
                            String resultCode = getCode() + ch;

                            if (controller.checkRequestCode(email, resultCode)) {
                                System.out.println("dispose");
                                dispose();
                            } else {
                                System.out.println(textFields[index].getText());
                                if (textFields[index].getText().length() > 0) {
                                    e.consume();
                                }
                                System.out.println("will not dispose");
                            }
                        }
                    } else if (code == 8) {
                        /* If user press the backspace key */
                        for (int i = 0; i < length; ++i) {
                            if (obj == textFields[i]) {
                                index = i;
                                i = length;
                            }
                        }
                        if (index != 0) {
                            textFields[index - 1].setText("");
                            textFields[index - 1].requestFocus();
                        }
                    } else {
                        e.consume();
                    }
                }
            });

            pnlCode.add(textFields[i]);

            if (++index == (textFields.length >> 1)) {
                pnlCode.add(lblSeperator);
            }
            pnlCode.add(Box.createVerticalGlue());
        }

        pnlMain.add(pnlCode);


        this.add(pnlMain);
    }

    private void loadText() {

    }

    private String getCode() {
        StringBuilder sb = new StringBuilder();

        for (JTextField textField : textFields) {
            sb.append(textField.getText());
        }
        return sb.toString();
    }

    @Override
    public void changeLocale(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);
        onLocaleChanged(locale);
        loadText();
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, MailCodeDialog.this);
    }
}
