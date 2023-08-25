package com.dita.xd.view.dialog;

import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>Plain Dialog</p>
 *
 * @author DelynMk2 (Hyeong-won Park)
 * @see JDialog
 */
public class PlainDialog extends JDialog implements LocaleChangeListener {
    private ResourceBundle localeBundle;
    private Locale currentLocale;

    private JButton btnOK;

    private JLabel lblMain;

    private JPanel pnlMain;
    private JPanel pnlFooter;

    private MessageType type;

    private String msg;

    public PlainDialog(Locale locale, String msg, MessageType type) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        this.msg = msg;
        this.type = type;

        initialize();
        changeLocale(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setModalityType(DEFAULT_MODALITY_TYPE);
        setSize(250, 150);

        switch (type) {
            case ERROR -> setTitle(localeBundle.getString("dialog.plain.title.error"));
            case WARNING -> setTitle(localeBundle.getString("dialog.plain.title.warning"));
            case INFORMATION -> setTitle(localeBundle.getString("dialog.plain.title.information"));
        }

        /* Variables declaration */
        btnOK = new JButton();

        lblMain = new JLabel();

        pnlFooter = new JPanel();
        pnlMain = new JPanel();

        /* Set the texts of components. */
        loadText();

        /* Set the properties of components. */
        lblMain.setHorizontalAlignment(JLabel.CENTER);

        /* Set the layout of sub-panels */
        pnlMain.setLayout(new BorderLayout());
        pnlFooter.setLayout(new BorderLayout());

        /* Add the components to sub-panels */
        pnlMain.add(lblMain, BorderLayout.CENTER);
        pnlFooter.add(btnOK);

        /* Add the listeners to components */
        btnOK.addActionListener(e -> {
            setVisible(false);
            dispose();      // Dispose 잊지 말 것
        });

        /* Add the sub-panels to main pane */
        this.add(pnlMain);
        this.add(pnlFooter, BorderLayout.SOUTH);
    }

    void loadText() {
        btnOK.setText(localeBundle.getString("dialog.plain.ok"));
        lblMain.setText(String.format(localeBundle.getString("dialog.plain.message"), msg));
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
        LocaleChangeListener.broadcastLocaleChanged(newLocale, PlainDialog.this);
    }

    public enum MessageType {
        ERROR(0), WARNING(1), INFORMATION(3);

        private final int value;

        MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}