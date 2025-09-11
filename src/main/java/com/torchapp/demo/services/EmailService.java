package com.torchapp.demo.services;

import com.torchapp.demo.models.User;
import jakarta.mail.MessagingException;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

public class EmailService {

    private static final String TEMPLATE_NAME = "registration";
    private static final String TORCH_LOGO_IMAGE = "templates/images/torch.png";
    private static final String PNG_MIME = "image.png";
    private static final String MAIL_SUBJECT = "Seja bem vindo ao Torch!";

    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public EmailService(Environment environment, JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
    this.environment = environment;
    this.mailSender = mailSender;
    this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public void sendMailWithInline(User user) throws MessagingException, UnsupportedOperationException {
        String confirmationUrl = "generated_confirmation_url";
        String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
        String mailFromName = environment.getProperty("mail.from.name", "Identity");
    }
}
