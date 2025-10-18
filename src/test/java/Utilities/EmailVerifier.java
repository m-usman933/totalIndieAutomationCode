package Utilities;

import TotalIndieLoginTests.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;

import java.util.Properties;

public class EmailVerifier
{
    public static boolean checkEmailReceived(String expectedSubject) throws Exception {
        ConfigReader config = new ConfigReader();
        String host = "imap.gmail.com";
        String user = config.getEmail();
        String appPassword = config.getGmailPassword();

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore();
        store.connect(host, user, appPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        int maxAttempts = 6;           // retry 6 times
        int waitBetweenAttempts = 10;  // seconds

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("📩 Attempt " + attempt + " to find email...");

            // Refresh folder
            if (!inbox.isOpen()) {
                inbox.open(Folder.READ_ONLY);
            } else {
                inbox.close(false);
                inbox.open(Folder.READ_ONLY);
            }

            FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(unseenFlagTerm);

            for (int i = messages.length - 1; i >= 0; i--) {
                String subject = messages[i].getSubject();
                if (subject != null && subject.contains(expectedSubject)) {
                    System.out.println("✅ Email found with subject: " + subject);
                    inbox.close(false);
                    store.close();
                    return true;
                }
            }

            if (attempt < maxAttempts) {
                System.out.println("⏳ Email not found yet, waiting " + waitBetweenAttempts + " seconds...");
                Thread.sleep(waitBetweenAttempts * 1000L);
            }
        }


        System.out.println("❌ Email with subject '" + expectedSubject + "' not received after retries.");
        inbox.close(false);
        store.close();
        return false;
    }
}


