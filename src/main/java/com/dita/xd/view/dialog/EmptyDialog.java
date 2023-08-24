package com.dita.xd.view.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmptyDialog extends JDialog {


    public EmptyDialog(String title){

        initialize(title);

    }
    private void initialize(String title) {


        JLabel lblMain = new JLabel();

        JPanel pnlMain = new JPanel();
        JPanel pnlFooter = new JPanel();

        JButton btnOk = new JButton("확인");

        this.setLayout(new BorderLayout());
        this.setBounds(100,100,250, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);

        lblMain.setText(title + "가 비어있습니다.");

        lblMain.setHorizontalAlignment(JLabel.CENTER);

        pnlMain.setLayout(new BorderLayout());
        pnlFooter.setLayout(new BorderLayout());

        pnlMain.add(lblMain, BorderLayout.CENTER);
        pnlFooter.add(btnOk);

        this.add(pnlMain);
        this.add(pnlFooter, BorderLayout.SOUTH);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
