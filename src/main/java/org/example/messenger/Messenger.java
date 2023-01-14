package org.example.messenger;

class Messenger {
    private TemplateEngine templateEngine;
    private MailServer mailServer;

    public Messenger(MailServer mailServer, TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    public void sendMessage(Client client, Template template) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be null");
        }

        if (template == null) {
            throw new IllegalArgumentException("Template should not be null");
        }

        String msgContent = templateEngine.prepareMessage(template, client);
        mailServer.send(client.getEmail(), msgContent);
    }
}
