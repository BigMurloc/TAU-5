package org.example.messenger;

interface TemplateEngine {
    String prepareMessage(Template template, Client client);
}

