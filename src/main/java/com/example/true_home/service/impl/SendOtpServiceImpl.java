package com.example.true_home.service.impl;

import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.service.SendOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
@Qualifier("JavaEmailServiceImpl")
public class SendOtpServiceImpl implements SendOtpService {

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${company.name}")
    private String companyName;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtp(User user, String otp) {
        String toAddress = user.getEmail();
        String fromAddress = new String(Base64.getDecoder().decode(fromEmail));
        String senderName = companyName;
        String subject = "OTP to reset your Password for TrueHome";
        String content = "Dear [[name]],<br>"
                + "<br>Your OTP for login is <strong style=\"color:red;font-size:18px;\">[[OTP]]</strong><br>"
                + "<br>Please use this to validate your account and NEVER share this with anyone <br>"
                + "Note: <i>The OTP expires in 5 minutes</i><br>"
                + "<br></br>If you have received this message in error, please notify sender support@truehome.io of the error and delete the message.<br>"
                + "<br>Thank you,<br>"
                + "Team " + companyName;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            content = content.replace("[[name]]", user.getFirstname() + "" + user.getLastname());
            content = content.replace("[[OTP]]", otp);
            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new TrueHomException(null, "Email not sent", "OS_500");
        }
        mailSender.send(message);
    }
}
