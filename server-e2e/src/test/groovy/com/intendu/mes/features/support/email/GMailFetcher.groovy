package com.intendu.mes.features.support.email

import com.sun.mail.util.MailSSLSocketFactory

import javax.mail.BodyPart
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store
import javax.mail.internet.MimeMultipart
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GMailFetcher {

    String tag = "Click for activate :"

    String username
    String password

    GMailFetcher(String username, String password) {
        this.username = username
        this.password = password
    }

    private String getLastEmail() {
        CountDownLatch latch = new CountDownLatch(1);
        Store store = createStoreFor(username, password)
        Folder folder = createInboxFolderFrom(store)
        int messageCount = folder.getMessageCount()
        String emailMessage
        Thread.start{
            while (true) {
                int currentMessageCount = folder.getMessageCount()
                if (currentMessageCount > messageCount) {
                    emailMessage = getTextFromMessage(
                            folder.getMessage(currentMessageCount))
                    latch.countDown();
                    break;
                }
            }
        }
        latch.await(20, TimeUnit.SECONDS);
        if(emailMessage == null){
            throw new Exception("Something wrong !!! Activation mail not arrived");
        }
        folder.close(false)
        store.close()
        return emailMessage
    }

    String getLinkFromLastEmail(){
        String link
        for(String line: getLastEmail().split("\n")){
            if(line != null && !line.isEmpty() && line.contains(tag)) {
                link = line.substring(line.indexOf(tag) + tag.length() + 1)
            }
        }
        return link
    }

    private String getTextFromMessage(Message message) throws Exception {
        String result = ""
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString()
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent()
            result = getTextFromMimeMultipart(mimeMultipart)
        }
        return result
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception{
        String result = ""
        int count = mimeMultipart.getCount()
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i)
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent()
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent()
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text()
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent())
            }
        }
        return result
    }

    private Store createStoreFor(String username, String password) {
        Session session = Session.getDefaultInstance(createImapProperties(), null)
        Store store = session.getStore("imaps")
        store.connect(username, password)
        store
    }

    private Properties createImapProperties() {
        final Properties props = new Properties();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.user", username);
        props.setProperty("mail.imaps.password", password);
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.auth", "true");
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.store.protocol", "imaps")
        props
    }

    private Folder createInboxFolderFrom(Store store) {
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        folder
    }

    private EmailMessage getLastMessageFrom(Folder folder) {
        Message m = folder.getMessage(folder.getMessageCount())
        def emailMessage = new EmailMessage([
                subject: m.getSubject(),
                from   : m.getFrom()[0],
                body   : m.getContent()
        ])
        emailMessage
    }
}
