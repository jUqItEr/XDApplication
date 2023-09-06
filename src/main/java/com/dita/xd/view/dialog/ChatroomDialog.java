package com.dita.xd.view.dialog;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ChatroomDialog extends JDialog implements LocaleChangeListener {
    private final ActivityController controller;
    private final Vector<UserBean> joinedMember;
    private final UserRepository repository;

    private Vector<ChatroomBean> beans;
    private ResourceBundle localeBundle;


    private JComboBox<String> cmbBox;
    private JTextField tfName;

    private JButton btnCancel;
    private JButton btnCreate;

    private JLabel lblComboBox;
    private JLabel lblName;

    private String errorMessage;
    private String okMessage;

    private int mode;

    private boolean result;

    public ChatroomDialog(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new ActivityController();
        repository = UserRepository.getInstance();

        btnCancel = new JButton();
        btnCreate = new JButton();

        cmbBox = new JComboBox<>();
        tfName = new JTextField();

        joinedMember = new Vector<>();
        result = false;
        mode = 1;

        joinedMember.addElement(repository.getUserAccount());

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setSize(new Dimension(400, 500));

        JPanel buttonPane = new JPanel();
        JPanel hostPane = new JPanel();
        
        lblComboBox = new JLabel();
        lblName = new JLabel();

        buttonPane.add(btnCreate);
        buttonPane.add(btnCancel);

        hostPane.add(lblComboBox);
        hostPane.add(cmbBox);
        hostPane.add(lblName);
        hostPane.add(tfName);

        buttonPane.setLayout(new GridLayout(1, 2));
        hostPane.setLayout(new SpringLayout());

        loadText();

        SpringUtilities.makeCompactGrid(hostPane, 2, 2, 6, 6, 6, 6);

        btnCancel.addActionListener(e -> dispose());

        btnCreate.addActionListener(e -> {
            switch (mode) {
                case 1 -> {
                    String name = tfName.getText().trim();

                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(this, errorMessage);
                        tfName.setText("");
                        tfName.requestFocus();
                        return;
                    }
                    ChatroomBean bean = controller.createChatroom(name);

                    joinedMember.forEach(b -> controller.inviteChatroom(bean, b));
                    result = true;

                    JOptionPane.showMessageDialog(this, okMessage);
                    dispose();
                }
                case 2 -> {

                }
            }

        });

        add(buttonPane, BorderLayout.SOUTH);
        add(hostPane, BorderLayout.NORTH);
    }

    private void loadText() {
        setTitle(mode == 1 ? "채팅방 만들기" : "친구 초대");

        errorMessage = "채팅방 이름을 입력해주세요.";
        okMessage = "채팅방이 생성되었습니다.";

        btnCancel.setText("취소");
        btnCreate.setText(mode == 1 ? "생성" : "초대");

        lblComboBox.setText("채팅방 선택");
        lblName.setText("채팅방 이름");
    }

    public boolean showDialog() {

        setVisible(true);
        return result;
    }

    public void toggleComboBox(boolean flag) {
        mode = 1;
        cmbBox.setEnabled(flag);
        loadText();
    }

    public void toggleTextField(boolean flag) {
        mode = 2;
        tfName.setEnabled(flag);
        loadText();
    }

    public void loadChatroom(Vector<ChatroomBean> beans) {
        this.beans = beans;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        loadText();
    }

    static class SpringUtilities {
        /**
         * A debugging utility that prints to stdout the component's minimum,
         * preferred, and maximum sizes.
         */
        public static void printSizes(Component c) {
            System.out.println("minimumSize = " + c.getMinimumSize());
            System.out.println("preferredSize = " + c.getPreferredSize());
            System.out.println("maximumSize = " + c.getMaximumSize());
        }

        /**
         * Aligns the first <code>rows</code> * <code>cols</code> components of
         * <code>parent</code> in a grid. Each component is as big as the maximum
         * preferred width and height of the components. The parent is made just big
         * enough to fit them all.
         *
         * @param rows     number of rows
         * @param cols     number of columns
         * @param initialX x location to start the grid at
         * @param initialY y location to start the grid at
         * @param xPad     x padding between cells
         * @param yPad     y padding between cells
         */
        public static void makeGrid(Container parent, int rows, int cols, int initialX, int initialY,
                                    int xPad, int yPad) {
            SpringLayout layout;
            try {
                layout = (SpringLayout) parent.getLayout();
            } catch (ClassCastException exc) {
                System.err.println("The first argument to makeGrid must use SpringLayout.");
                return;
            }

            Spring xPadSpring = Spring.constant(xPad);
            Spring yPadSpring = Spring.constant(yPad);
            Spring initialXSpring = Spring.constant(initialX);
            Spring initialYSpring = Spring.constant(initialY);
            int max = rows * cols;

            // Calculate Springs that are the max of the width/height so that all
            // cells have the same size.
            Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
            Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
            for (int i = 1; i < max; i++) {
                SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

                maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
                maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
            }

            // Apply the new width/height Spring. This forces all the
            // components to have the same size.
            for (int i = 0; i < max; i++) {
                SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

                cons.setWidth(maxWidthSpring);
                cons.setHeight(maxHeightSpring);
            }

            // Then adjust the x/y constraints of all the cells so that they
            // are aligned in a grid.
            SpringLayout.Constraints lastCons = null;
            SpringLayout.Constraints lastRowCons = null;
            for (int i = 0; i < max; i++) {
                SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
                if (i % cols == 0) { // start of new row
                    lastRowCons = lastCons;
                    cons.setX(initialXSpring);
                } else { // x position depends on previous component
                    cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
                }

                if (i / cols == 0) { // first row
                    cons.setY(initialYSpring);
                } else { // y position depends on previous row
                    cons.setY(Spring.sum(Objects.requireNonNull(lastRowCons)
                            .getConstraint(SpringLayout.SOUTH), yPadSpring));
                }
                lastCons = cons;
            }

            // Set the parent's size.
            SpringLayout.Constraints pCons = layout.getConstraints(parent);
            pCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring.constant(yPad), Objects.requireNonNull(lastCons)
                    .getConstraint(SpringLayout.SOUTH)));
            pCons.setConstraint(SpringLayout.EAST, Spring.sum(Spring.constant(xPad), lastCons
                    .getConstraint(SpringLayout.EAST)));
        }

        /* Used by makeCompactGrid. */
        private static SpringLayout.Constraints getConstraintsForCell(int row, int col, Container parent,
                                                                      int cols) {
            SpringLayout layout = (SpringLayout) parent.getLayout();
            Component c = parent.getComponent(row * cols + col);
            return layout.getConstraints(c);
        }

        /**
         * Aligns the first <code>rows</code> * <code>cols</code> components of
         * <code>parent</code> in a grid. Each component in a column is as wide as
         * the maximum preferred width of the components in that column; height is
         * similarly determined for each row. The parent is made just big enough to
         * fit them all.
         *
         * @param rows     number of rows
         * @param cols     number of columns
         * @param initialX x location to start the grid at
         * @param initialY y location to start the grid at
         * @param xPad     x padding between cells
         * @param yPad     y padding between cells
         */
        public static void makeCompactGrid(Container parent, int rows, int cols, int initialX,
                                           int initialY, int xPad, int yPad) {
            SpringLayout layout;
            try {
                layout = (SpringLayout) parent.getLayout();
            } catch (ClassCastException exc) {
                System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
                return;
            }

            // Align all cells in each column and make them the same width.
            Spring x = Spring.constant(initialX);
            for (int c = 0; c < cols; c++) {
                Spring width = Spring.constant(0);
                for (int r = 0; r < rows; r++) {
                    width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
                }
                for (int r = 0; r < rows; r++) {
                    SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                    constraints.setX(x);
                    constraints.setWidth(width);
                }
                x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
            }

            // Align all cells in each row and make them the same height.
            Spring y = Spring.constant(initialY);
            for (int r = 0; r < rows; r++) {
                Spring height = Spring.constant(0);
                for (int c = 0; c < cols; c++) {
                    height = Spring.max(height, getConstraintsForCell(r, c, parent, cols).getHeight());
                }
                for (int c = 0; c < cols; c++) {
                    SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                    constraints.setY(y);
                    constraints.setHeight(height);
                }
                y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
            }

            // Set the parent's size.
            SpringLayout.Constraints pCons = layout.getConstraints(parent);
            pCons.setConstraint(SpringLayout.SOUTH, y);
            pCons.setConstraint(SpringLayout.EAST, x);
        }
    }
}
