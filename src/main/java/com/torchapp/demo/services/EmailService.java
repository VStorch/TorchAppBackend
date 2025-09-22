package com.torchapp.demo.services;

import com.torchapp.demo.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private static final String TEMPLATE_NAME = "registration";
    private static final String TORCH_LOGO_IMAGE = "templates/images/torch.png";
    private static final String PNG_MIME = "image/png";
    private static final String MAIL_SUBJECT = "Seja bem vindo ao Torch!";

    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public EmailService(Environment environment, JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
    this.environment = environment;
    this.mailSender = mailSender;
    this.htmlTemplateEngine = htmlTemplateEngine;
    }

    @SneakyThrows
    @Async
    public void sendMailWithInline(User user) {
        try {
            String confirmationUrl = "generated_confirmation_url";
            String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
            String mailFromName = environment.getProperty("mail.from.name", "Identity");

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper email;
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject(MAIL_SUBJECT);
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("email", user.getEmail());
            ctx.setVariable("name", user.getName());
            ctx.setVariable("torch", TORCH_LOGO_IMAGE);
            ctx.setVariable("url", confirmationUrl);

            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);

            email.setText(htmlContent, true);

            ClassPathResource clr = new ClassPathResource(TORCH_LOGO_IMAGE);

            email.addInline("torch", clr, PNG_MIME);

            mailSender.send(mimeMessage);
        }
        catch (Exception e) {
            System.err.println("Falha ao enviar email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @SneakyThrows
    @Async
    public void sendRedirectMail (User user) {
        try {
            String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
            String mailFromName = environment.getProperty("mail.from.name", "Identity");

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject("Recuperação de Senha");
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("email", user.getEmail());
            ctx.setVariable("name", user.getName());

            String htmlContent = this.htmlTemplateEngine.process("password-reset", ctx);
            email.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        }
        catch (Exception e) {
            System.err.println("Falha ao enviar email "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
