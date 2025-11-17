package com.torchapp.demo.services;

import com.torchapp.demo.exceptions.EmailSendException;
import com.torchapp.demo.models.User;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class EmailService {

    private static final String TORCH_LOGO_IMAGE = "images/torch.png";
    private static final String PNG_MIME = "image/png";

    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public EmailService(Environment environment, JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
        this.environment = environment;
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    @Async
    public void sendMailWithInline(User user) {
        try {
            String confirmationUrl = "generated_confirmation_url";
            String mailFrom = getMailFrom();
            String mailFromName = getMailFromName();

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject("Seja bem vindo(a) ao Torch!");
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = createContext();
            ctx.setVariable("email", user.getEmail());
            ctx.setVariable("name", user.getName());
            ctx.setVariable("torch", TORCH_LOGO_IMAGE);
            ctx.setVariable("url", confirmationUrl);

            final String htmlContent = this.htmlTemplateEngine.process("registration", ctx);
            email.setText(htmlContent, true);

            ClassPathResource clr = new ClassPathResource(TORCH_LOGO_IMAGE);
            email.addInline("torch", clr, PNG_MIME);

            mailSender.send(mimeMessage);
            log.info("Email de boas-vindas enviado com sucesso para: {}", user.getEmail());
        }
        catch (Exception e) {
            log.error("Falha ao enviar email de boas-vindas para {}: {}", user.getEmail(), e.getMessage(), e);
            throw new EmailSendException("Falha ao enviar email de boas-vindas", e);
        }
    }

    @Async
    public void sendRedirectMail(User user) {
        try {
            String mailFrom = getMailFrom();
            String mailFromName = getMailFromName();

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject("Recuperação de Senha - Torch");
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = createContext();
            ctx.setVariable("email", user.getEmail());
            ctx.setVariable("name", user.getName());

            String resetUrl = "torchapp://reset-password?token=" + user.getResetToken(); // Deep Link
            String fallbackUrl = "https://vstorch.github.io/PaginaRedirecionamento?token=" + user.getResetToken();

            ctx.setVariable("resetUrl", resetUrl);
            ctx.setVariable("fallbackUrl", fallbackUrl);
            ctx.setVariable("name", user.getName());

            String htmlContent = this.htmlTemplateEngine.process("password-reset", ctx);
            email.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Email de recuperação de senha enviado com sucesso para: {}", user.getEmail());
        }
        catch (Exception e) {
            log.error("Falha ao enviar email de recuperação para {}: {}", user.getEmail(), e.getMessage(), e);
            throw new EmailSendException("Falha ao enviar email de recuperação de senha", e);
        }
    }

    @Async
    public void sendVerificationCodeMail(String email, String code) {
        try {
            String mailFrom = getMailFrom();
            String mailFromName = getMailFromName();

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setTo(email);
            message.setSubject("Código de Verificação - Torch");
            message.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = createContext();
            ctx.setVariable("email", email);
            ctx.setVariable("code", code);

            String htmlContent = this.htmlTemplateEngine.process("verification-code", ctx);
            message.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Email de verificação enviado com sucesso para: {}", email);
        } catch (Exception e) {
            log.error("Falha ao enviar email de verificação para {}: {}", email, e.getMessage(), e);
            throw new EmailSendException("Falha ao enviar email de verificação", e);
        }
    }

    private String getMailFrom() {
        return environment.getProperty("spring.mail.properties.mail.smtp.from");
    }

    private String getMailFromName() {
        return environment.getProperty("mail.from.name", "Identity");
    }

    private Context createContext() {
        return new Context(LocaleContextHolder.getLocale());
    }
}
