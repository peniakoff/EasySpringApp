package pl.tomaszmiller.AppNo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Peniakoff on 03.06.2017.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toWho, String message, String title) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); //MimeMessageHelper - it's Spring's class; message creating is simpler
            helper.setTo(toWho);
            helper.setFrom(""); //set your e-mail address
            helper.setSubject(title);
            helper.setText(message, true);
            helper.setReplyTo("");  //set your e-mail address
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);

    }

}
