package com.dita.xd.view.manager;

import javax.swing.*;
import java.awt.*;

public class MainLayoutMgr {
    private static volatile MainLayoutMgr instance = null;

    /*
     * mainLayout: JPanel을 교환하기 위해 전역 관리
     * mainFrame: JFrame을 닫을 일 (종료)을 대비해 전역 관리
     * mainPane: CardLayout을 가지는 JPanel을 가리킴
     *
     * !! 중요 !!
     * 1. 해당 JFrame의 생성자에서 MainTransitionMgr Singleton instance를 생성
     * 2. initialize 함수에서 mgr내부 mainLayout, mainFrame, mainPane 등의 필드를 setter로 기입
     * */
    private CardLayout mainLayout;
    private JFrame mainFrame;
    private JPanel mainPane;

    private MainLayoutMgr() {
    }

    /* Singleton class */
    public static MainLayoutMgr getInstance() {
        if (instance == null) {
            synchronized (MainLayoutMgr.class) {
                if (instance == null) {
                    instance = new MainLayoutMgr();
                }
            }
        }
        return instance;
    }

    public void setMainLayout(CardLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setMainPane(JPanel mainPane) {
        this.mainPane = mainPane;
    }

    public void dispose() {
        mainFrame.dispose();
    }

    public void show(String name) {
        mainLayout.show(mainPane, name);
    }
}
