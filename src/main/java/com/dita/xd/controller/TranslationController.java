package com.dita.xd.controller;

import com.dita.xd.service.implementation.TranslationServiceImpl;

public class TranslationController {
    private final TranslationServiceImpl svc;

    public TranslationController() {
        svc = new TranslationServiceImpl();
    }

    public String translate(String text, String targetLang) {
        return svc.translate(text, targetLang);
    }
}
