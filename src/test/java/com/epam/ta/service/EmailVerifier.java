package com.epam.ta.service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class EmailVerifier {

    private static final String username = "dlqconsumer@gmail.com";
    private static final String password = "rrjw jnlg tgsp iklh";
    private static final String host = "imap.gmail.com";


    public static String getContent() throws Exception {

        Properties properties = new Properties();

        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            String emailContent = getMessages(inbox);

            inbox.close(false);
            store.close();

            return emailContent;
    }

    private static String getMessages(Folder inbox) throws MessagingException, IOException {

//        int messageCount = inbox.getMessageCount();
//        Awaitility.await().atMost(10, TimeUnit.SECONDS)
//                .pollInterval(500, TimeUnit.MILLISECONDS)
//                .until(() -> {
//
//                    try {
//                        Assertions.assertTrue(inbox.getMessageCount() > messageCount);
//                        return true;
//                    } catch (AssertionError e) {
//                        return false;
//                    }
//                });

        Message[] messages = inbox.getMessages();
        return getString(messages);
    }

    private static String getBody(Message[] messages) throws IOException, MessagingException {
        Message message = messages[messages.length - 1];
        Object content = message.getContent();
        return (String) content;
    }

    private static String getString(Message[] messages) throws MessagingException, IOException {
        String messageContent = getBody(messages);
        String[] result = messageContent.split("\r");
        String emailContent = "";
        StringBuilder stringBuilder1 = new StringBuilder();

        for (int i = 5; i < result.length - 10; i++) {
            emailContent = stringBuilder1.append(result[i]).toString();
        }
        return emailContent.substring(20);
    }
}
