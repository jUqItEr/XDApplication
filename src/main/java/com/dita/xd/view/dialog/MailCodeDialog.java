package com.dita.xd.view.dialog;

import com.dita.xd.controller.MailController;
import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MailCodeDialog extends JDialog implements LocaleChangeListener {
    private final MailController controller;
    private final String email;

    private ResourceBundle localeBundle;

    private JButton btnCancel;

    private JLabel lblError;
    private JLabel lblHeader;
    private JLabel lblSeperator;


    private JPanel pnlCode;
    private JPanel pnlMain;

    private boolean result;


    private final JTextField[] textFields = new JTextField[6];

    public MailCodeDialog(Locale locale, String email) {
        this.localeBundle = ResourceBundle.getBundle("language", locale);
        this.controller = new MailController();
        this.email = email;

        this.result = false;
        this.setModalityType(DEFAULT_MODALITY_TYPE);

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 250));
        setLocationRelativeTo(null);
        setResizable(false);

        btnCancel = new JButton();

        lblError = new JLabel();
        lblHeader = new JLabel();
        lblSeperator = new JLabel("-");

        pnlCode = new JPanel();
        pnlMain = new JPanel();

        pnlCode.setLayout(new BoxLayout(pnlCode, BoxLayout.X_AXIS));
        pnlCode.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlCode.setMaximumSize(new Dimension(400, 50));

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblError.setForeground(Color.RED);
        lblError.setFont(lblError.getFont().deriveFont(18f));

        lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSeperator.setMaximumSize(new Dimension(50, 50));
        lblSeperator.setPreferredSize(new Dimension(50, 50));
        lblSeperator.setHorizontalAlignment(JLabel.CENTER);
        lblSeperator.setVerticalAlignment(JLabel.CENTER);
        lblSeperator.setFont(lblSeperator.getFont().deriveFont(24f));

        pnlCode.add(Box.createHorizontalGlue());

        for (int i = 0; i < textFields.length; ++i) {
            textFields[i] = new JTextField();
            textFields[i].setMaximumSize(new Dimension(50, 50));
            textFields[i].setPreferredSize(new Dimension(50, 50));
            textFields[i].setHorizontalAlignment(JTextField.CENTER);
            textFields[i].setFont(textFields[i].getFont().deriveFont(32f));
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
                                result = true;
                                setVisible(false);
                                dispose();
                            } else {
                                lblError.setText(localeBundle.getString("dialog.mail.error"));
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

                @Override
                public void keyTyped(KeyEvent e) {
                    JTextField textField = (JTextField) e.getSource();

                    if (textField.getText().length() > 0) {
                        textField.setText(String.valueOf(e.getKeyChar()));
                        e.consume();
                    }
                }
            });

            pnlCode.add(textFields[i]);

            if ((i + 1) == (textFields.length >> 1)) {
                pnlCode.add(lblSeperator);
            } else {
                pnlCode.add(Box.createHorizontalGlue());
            }
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.revokeCode(email);
            }
        });

        btnCancel.addActionListener(e -> {
            controller.revokeCode(email);
            setVisible(false);
            dispose();
        });

        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(lblHeader);
        pnlMain.add(Box.createVerticalStrut(15));
        pnlMain.add(pnlCode);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(lblError);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(btnCancel);
        pnlMain.add(Box.createVerticalGlue());

        this.add(pnlMain);
    }

    private void loadText() {
        setTitle(localeBundle.getString("dialog.mail.title"));

        btnCancel.setText(localeBundle.getString("dialog.mail.cancel"));
        lblError.setText(" ");
        lblHeader.setText(String.format(localeBundle.getString("dialog.mail.header"), email));
    }


    private String getCode() {
        StringBuilder sb = new StringBuilder();

        for (JTextField textField : textFields) {
            sb.append(textField.getText());
        }
        return sb.toString();
    }

    public boolean showDialog() {
        setVisible(true);
        return result;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
