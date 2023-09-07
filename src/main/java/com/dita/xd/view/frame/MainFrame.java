package com.dita.xd.view.frame;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.dialog.ProfileDialog;
import com.dita.xd.view.manager.MainLayoutMgr;
import com.dita.xd.view.panel.main.MenuPanel;
import com.dita.xd.view.panel.main.chat.ChatPanel;
import com.dita.xd.view.panel.main.home.HomePagePanel;
import com.dita.xd.view.panel.main.profile.ProfilePanel;
import com.dita.xd.view.panel.main.search.SearchPanel;
import com.dita.xd.view.theme.XdMaterialTheme;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private final UserRepository repository;
    private final MainLayoutMgr mgr;

    private final HomePagePanel homePagePanel;
    private final MenuPanel menuPanel;
    private final ProfilePanel profilePanel;
    private final SearchPanel searchPanel;
    private final ChatPanel chatPanel;

    public MainFrame(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        repository = UserRepository.getInstance();

        homePagePanel = new HomePagePanel(locale);
        menuPanel = new MenuPanel(locale);
        searchPanel = new SearchPanel(locale);
        profilePanel = new ProfilePanel(locale);
        chatPanel = new ChatPanel(locale);

        mgr = MainLayoutMgr.getInstance();

        initialize();
        onLocaleChanged(locale);

        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new XdMaterialTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setLayout(new BorderLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(750, 900));
        setLocationRelativeTo(null);
        setResizable(false);

        if (repository.getUserNickname() == null) {
            ProfileDialog dialog = new ProfileDialog(currentLocale);
            System.out.println(dialog.showDialog());
        }

        CardLayout clMain = new CardLayout();

        JPanel mainPane = new JPanel();
        JPanel sidePane = new JPanel();
        JPanel logoPane = new JPanel();

        sidePane.setLayout(new BorderLayout());
        logoPane.setLayout(new BorderLayout());
        mainPane.setLayout(clMain);

//        lblLogo.add(tmp);
//        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblLogo.setPreferredSize(new Dimension(200, 80));
//        logoPane.add(lblLogo);

        logoPane.add(new JImageView("resources/images/logo.png"));

        sidePane.add(logoPane, BorderLayout.NORTH);
        sidePane.add(menuPanel);
        sidePane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2,
                Color.LIGHT_GRAY));

        mgr.setMainFrame(this);
        mgr.setMainLayout(clMain);
        mgr.setMainPane(mainPane);
        mgr.setChatPane(chatPanel);
        mgr.setProfilePane(profilePanel);


        mainPane.add(homePagePanel, "home");
        mainPane.add(searchPanel, "search");
        mainPane.add(profilePanel, "profile");
        mainPane.add(chatPanel, "chat");

        this.add(sidePane, BorderLayout.WEST);
        this.add(mainPane);
    }

    private void loadText() {

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, MainFrame.this);
    }
}
