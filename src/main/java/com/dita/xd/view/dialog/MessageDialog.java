package com.dita.xd.view.dialog;

import com.dita.xd.controller.ChatroomController;
import com.dita.xd.controller.MessageController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.ChatroomRepository;
import com.dita.xd.util.server.MessageProtocol;
import com.dita.xd.util.server.ServerObject;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.base.JVerticalScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.*;

public class MessageDialog extends JDialog implements ActionListener, LocaleChangeListener, Runnable {
    private final JPanel bubblePane = new JPanel(new GridLayout(0, 1, 2, 0));

    private ResourceBundle localeBundle;

    private final ChatroomRepository repository;

    private JButton btnSend;
    private JHintTextField htfMessage;
    private JScrollBar scrollBar;

    protected ChatroomController chatroomController;
    protected MessageController messageController;

    protected BufferedReader inputStream;
    protected PrintWriter outputStream;
    protected Thread mainThread;
    protected int chatroomId;
    protected String userId;

    protected HashMap<String, UserBean> joinedUser;

    public MessageDialog(Locale locale, int chatroomId, String chatroomName, String userId) {
        chatroomController = new ChatroomController();
        messageController = new MessageController();

        repository = ChatroomRepository.getInstance();

        ChatroomBean bean = new ChatroomBean();
        bean.setChatroomId(chatroomId);
        ServerObject obj = repository.getUser(bean);

        this.inputStream = obj.getInputStream();
        this.outputStream = obj.getOutputStream();

        this.chatroomId = chatroomId;
        this.userId = userId;

        this.joinedUser = new HashMap<>();

        setTitle(chatroomName);

        initialize();
        onLocaleChanged(locale);
    }

    public MessageDialog(Locale locale, ChatroomBean bean, String userId) {
        this(locale, bean.getChatroomId(), bean.getName(), userId);
    }

    private void initialize() {
        setBackground(Color.WHITE);
        setSize(560, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel holderPane = new JPanel();
        JPanel userPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(holderPane));
        btnSend = new JButton();
        htfMessage = new JHintTextField();
        scrollBar = scrollPane.getVerticalScrollBar();

        holderPane.setLayout(new BorderLayout());
        userPane.setLayout(new BorderLayout());

        holderPane.add(bubblePane, BorderLayout.NORTH);
        holderPane.add(Box.createGlue(), BorderLayout.CENTER);

        userPane.add(btnSend, BorderLayout.EAST);
        userPane.add(htfMessage);

        btnSend.setIcon(new ImageIcon("resources/icons/send.png"));

        loadText();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBar(scrollBar);

        add(scrollPane);
        add(userPane, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ChatroomBean tmp = new ChatroomBean();
                tmp.setChatroomId(chatroomId);

                sendMessage(MessageProtocol.BYE + MessageProtocol.SEPARATOR + userId + ';' + chatroomId);

                try {
                    ServerObject obj = repository.getUser(tmp);
                    obj.getSock().close();
                } catch (IOException ex) {
                    System.err.println("Socket end");
                }
                repository.removeUser(tmp);
            }
        });

        btnSend.addActionListener(this);
        htfMessage.addActionListener(this);

        chatroomController.getUsers(chatroomId).forEach(bean -> joinedUser.put(bean.getUserId(), bean));

        /* Get message from databases. */
        loadBubbles(Optional.ofNullable(messageController.getMessages(this.chatroomId)).orElse(new Vector<>()));
        sendMessage(MessageProtocol.ID + MessageProtocol.SEPARATOR + userId + ';' + chatroomId);

        mainThread = new Thread(this);
        mainThread.start();
    }

    protected void createBubble(ChatMessageBean bean) {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel bubbleHolderPane = new JPanel();
        JLabel lblName = new JLabel();
        BubbleLabel bubble = new BubbleLabel(bubbleHolderPane, bean, userId);

        bubblePane.add(bubbleHolderPane);

        bubbleHolderPane.setBackground(Color.WHITE);
        bubbleHolderPane.setBorder(new EmptyBorder(0, 8, 0, 8));
        bubbleHolderPane.setLayout(new GridBagLayout());

        lblName.setText(joinedUser.get(bean.getUserId()).getNickname());

        if (userId.equals(bean.getUserId())) {
            gbc.anchor = GridBagConstraints.EAST;
        } else {
            gbc.anchor = GridBagConstraints.WEST;
        }
        gbc.insets.set(0, 0, 0, 0);
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        bubbleHolderPane.add(lblName, gbc);

        if (gbc.anchor == GridBagConstraints.WEST) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets.set(0, 0, 8, 80);
            bubbleHolderPane.add(bubble, gbc);
        }
        else {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets.set(0, 80, 8, 0);
            bubbleHolderPane.add(bubble, gbc);
        }

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                Adjustable adjustable = adjustmentEvent.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                scrollBar.removeAdjustmentListener(this);
            }
        });
        revalidate();
        repaint();
    }

    protected void loadBubbles(Vector<ChatMessageBean> beans) {
        beans.forEach(bean -> {
            GridBagConstraints gbc = new GridBagConstraints();
            JPanel bubbleHolderPane = new JPanel();
            JLabel lblName = new JLabel();
            BubbleLabel bubble = new BubbleLabel(bubbleHolderPane, bean, userId);

            bubblePane.add(bubbleHolderPane);

            bubbleHolderPane.setBackground(Color.WHITE);
            bubbleHolderPane.setBorder(new EmptyBorder(0, 8, 0, 8));
            bubbleHolderPane.setLayout(new GridBagLayout());

            lblName.setText(joinedUser.get(bean.getUserId()).getNickname());

            if (userId.equals(bean.getUserId())) {
                gbc.anchor = GridBagConstraints.EAST;
            } else {
                gbc.anchor = GridBagConstraints.WEST;
            }
            gbc.insets.set(0, 0, 0, 0);
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;
            bubbleHolderPane.add(lblName, gbc);

            if (gbc.anchor == GridBagConstraints.WEST) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets.set(0, 0, 8, 80);
                bubbleHolderPane.add(bubble, gbc);
            }
            else {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets.set(0, 80, 8, 0);
                bubbleHolderPane.add(bubble, gbc);
            }
        });
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                Adjustable adjustable = adjustmentEvent.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                scrollBar.removeAdjustmentListener(this);
            }
        });
        revalidate();
        repaint();
    }

    private void loadText() {
        htfMessage.setHint("Type message");
    }

    private void process(String line) {
        String[] tokens = line.split(MessageProtocol.SEPARATOR);
        String cmd = tokens[0];
        String data = tokens[1];

        if (cmd.equals(MessageProtocol.CHAT_ALL)) {
            tokens = data.split(";");
            String userId = tokens[0];
            String message = tokens[2];
            int chatroomId = Integer.parseInt(tokens[1]);

            if (this.chatroomId == chatroomId) {
                ChatMessageBean bean = new ChatMessageBean();

                bean.setChatroomId(chatroomId);
                bean.setContent(message);
                bean.setUserId(userId);

                createBubble(bean);
            }
        } else if (cmd.equals(MessageProtocol.DISMISS)) {
            tokens = data.split(";");
            String userId = tokens[0];
            int chatroomId = Integer.parseInt(tokens[1]);

            if (this.chatroomId == chatroomId) {
                joinedUser.remove(userId);
            }
        }
    }

    private void sendMessage(String msg) {
        outputStream.println(msg);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object src = actionEvent.getSource();

        if (src == btnSend || src == htfMessage) {
            String msg = htfMessage.getText().trim();

            if (!msg.isEmpty()) {
                sendMessage(MessageProtocol.CHAT_ALL + MessageProtocol.SEPARATOR +
                        userId + ';' + chatroomId + ';' + msg);
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

    @Override
    public void run() {
        try {
            while (true) {
                String line = inputStream.readLine();

                if (line == null) {
                    break;
                } else {
                    process(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Socket close!!");
        }
    }

    private static class BubbleLabel extends JTextArea {
        private final ChatMessageBean bean;
        private final String loginUserId;

        private int radius = 12;
        private int strokeThickness = 4;
        private int padding = strokeThickness / 2;
        private JPanel mParent;

        public BubbleLabel(JPanel parent, ChatMessageBean bean, String loginUserId) {
            super(bean.getContent());
            this.bean = bean;
            this.loginUserId = loginUserId;
            this.mParent = parent;

            initialize();
        }

        private void initialize() {
            setBorder(new EmptyBorder(12, 12, 12, 12));
            setEditable(false);
            setFocusable(false);
            setLineWrap(true);
            setOpaque(false);
            setWrapStyleWord(true);

            if (loginUserId.equals(bean.getUserId())) {
                setBackground(new Color(0x00_1D_9B_F0));
                setForeground(Color.WHITE);
            } else {
                setBackground(new Color(0x00_D0_D3_D3));
            }
        }

        private int countLines(JTextArea textArea) {
            AttributedString text = new AttributedString(textArea.getText());
            FontRenderContext frc = textArea.getFontMetrics(textArea.getFont())
                    .getFontRenderContext();
            AttributedCharacterIterator charIt = text.getIterator();
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
            float formatWidth = (float) textArea.getSize().width;
            lineMeasurer.setPosition(charIt.getBeginIndex());

            int noLines = 0;
            while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
                lineMeasurer.nextLayout(formatWidth);
                noLines++;
            }

            return noLines;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            int x = padding + strokeThickness;
            int width = getWidth() - (strokeThickness * 2);
            int bottomLineY = getHeight() - strokeThickness;
            g2d.fillRect(x, padding, width, bottomLineY);
            g2d.setRenderingHints(new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));
            g2d.setStroke(new BasicStroke(strokeThickness));
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding,
                    width, bottomLineY, radius, radius);
            Area area = new Area(rect);
            g2d.draw(area);

            int lc = countLines(this);
            GridBagLayout gbl = (GridBagLayout) mParent.getLayout();
            GridBagConstraints constraints = gbl.getConstraints(this);

            if (lc == 1) {
                if (constraints.fill == GridBagConstraints.HORIZONTAL) {
                    constraints.fill = GridBagConstraints.NONE;
                    gbl.setConstraints(this, constraints);

                    this.setSize(
                            getFontMetrics(getFont()).stringWidth(getText()) +
                                    this.getBorder().getBorderInsets(this).left +
                                    this.getBorder().getBorderInsets(this).right + 10,
                            getHeight() +
                                    this.getBorder().getBorderInsets(this).top +
                                    this.getBorder().getBorderInsets(this).bottom);
                }
            } else {
                if (constraints.fill == GridBagConstraints.NONE) {
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    gbl.setConstraints(this, constraints);

                }
            }
            super.paintComponent(g);
        }
    }
}
