package com.dita.xd.view.dialog;

import com.dita.xd.controller.MessageController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.view.base.JHintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

public class MessageDialog extends JDialog implements ActionListener, LocaleChangeListener {
    private static final Dimension THUMB_SIZE = new Dimension(170, 100);
    private final JPanel objectPane = new JPanel(new GridLayout(0, 1, 5, 5));

    private ResourceBundle localeBundle;

    private JButton btnSend;
    private JHintTextField htfMessage;

    protected MessageController controller;

    protected BufferedReader inputStream;
    protected PrintWriter outputStream;
    protected int chatroomId;
    protected String userId;

    public MessageDialog(Locale locale, BufferedReader in, PrintWriter out, int chatroomId, String userId) {
        controller = new MessageController();

        this.inputStream = in;
        this.outputStream = out;
        this.chatroomId = chatroomId;
        this.userId = userId;

        initialize();
        onLocaleChanged(locale);
    }

    public MessageDialog(Locale locale, BufferedReader in, PrintWriter out, ChatroomBean bean, String userId) {
        this(locale, in, out, bean.getChatroomId(), userId);
    }

    private void initialize() {
        setSize(600, 800);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel holderPane = new JPanel();
        JPanel userPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane(holderPane);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        btnSend = new JButton();
        htfMessage = new JHintTextField();

        holderPane.setLayout(new BorderLayout());
        userPane.setLayout(new BorderLayout());

        holderPane.add(objectPane, BorderLayout.NORTH);
        holderPane.add(Box.createGlue(), BorderLayout.CENTER);

        userPane.add(btnSend, BorderLayout.EAST);
        userPane.add(htfMessage);

        btnSend.setIcon(new ImageIcon("resources/icons/send.png"));

        loadText();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(scrollBar);

        add(scrollPane);
        add(userPane, BorderLayout.SOUTH);

        btnSend.addActionListener(this);
        htfMessage.addActionListener(this);

        Optional.ofNullable(controller.getMessages(this.chatroomId)).orElse(new Vector<>()).forEach(bean -> {
            createThumb(bean.getContent());
        });
        revalidate();
        repaint();
    }

    protected void createThumb(String text) {
        JButton thumb = new JButton(text);
        thumb.setPreferredSize(THUMB_SIZE);
        objectPane.add(thumb);
    }

    protected void createMessage(String message) {

        revalidate();
        repaint();
    }

    private void loadText() {
        setTitle("Message Tester");
        htfMessage.setHint("Type message");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object src = actionEvent.getSource();

        if (src == btnSend || src == htfMessage) {
            String msg = htfMessage.getText().trim();

            if (!msg.isEmpty()) {
            }
            htfMessage.setText("");     // clear
        }
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        loadText();
    }
}
