package com.dita.xd.driver;

import com.dita.xd.controller.TranslationController;

import java.util.Locale;

public class TranslationDriver {
    private final TranslationController controller;

    public TranslationDriver() {
        controller = new TranslationController();
    }

    public static void main(String[] args) {
        TranslationDriver driver = new TranslationDriver();

//        System.out.println(driver.translate("모든 인간은 태어날 때부터 자유로우며 " +
//                "그 존엄과 권리에 있어 동등하다. 인간은 천부적으로 이성과 양심을 부여받았으며" +
//                "서로 형제애의 정신으로 행동하여야 한다.", "ja"));

        System.out.println(driver.localeToTargetString(new Locale("es_ES")));
    }

    public String translate(String text, String targetLang) {
        return controller.translate(text, targetLang);
    }

    public String localeToTargetString(Locale locale) {
        String lang = locale.getLanguage();

        if (lang.contains("es")) {
            lang = lang.substring(3);
        }
        return lang;
    }
}
