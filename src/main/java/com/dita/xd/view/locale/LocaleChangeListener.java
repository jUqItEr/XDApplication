package com.dita.xd.view.locale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface LocaleChangeListener {
    void onLocaleChange(Locale newLocale);

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
