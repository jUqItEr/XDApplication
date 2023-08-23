package com.dita.xd.listener;

import com.dita.xd.view.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface LocaleChangeListener {
    void getLocaleString(Locale locale);

    void onLocaleChanged(Locale newLocale);

    static void broadcastLocaleChanged(final Locale locale, final Container container) {
        List<Component> components = getChildren(Component.class, container);
        components.stream().filter(LocaleChangeListener.class::isInstance)
                .map(LocaleChangeListener.class::cast)
                .forEach(lc -> lc.onLocaleChanged(locale));
    }

    static <T extends Component> java.util.List<T> getChildren(Class<T> classes, final Container container) {
        Component[] components;

        if (container instanceof JMenu) {
            components = ((JMenu) container).getMenuComponents();
        } else {
            components = container.getComponents();
        }
        List<T> compList = new ArrayList<>();

        for (Component component : components) {
            if (classes.isAssignableFrom(component.getClass())) {
                compList.add(classes.cast(component));
            }
            if (component instanceof Container) {
                compList.addAll(getChildren(classes, (Container) component));
            }
        }
        return compList;
    }   // -- End of function (getChildren)
}
