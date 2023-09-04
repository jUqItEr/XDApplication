package com.dita.xd.service;

public interface TranslationService extends Service {
    String translate(String text, String targetLang);
}
