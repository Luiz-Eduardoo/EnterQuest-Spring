package com.startup.enterquest.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoEmailService {

    private final JavaMailSender javaMailSender;

    public NotificacaoEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmailAtualizacaoChamado(
            String emailDestino,
            String tituloChamado,
            String novoStatus) {

        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();

            mensagem.setTo(emailDestino);
            mensagem.setSubject("Atualização do seu chamado - EnterQuest");
            mensagem.setText(
                    "Olá!\n\n" +
                    "O status do seu chamado foi atualizado.\n\n" +
                    "Chamado: " + tituloChamado + "\n" +
                    "Novo status: " + novoStatus + "\n\n" +
                    "Acesse o aplicativo EnterQuest para visualizar mais detalhes."
            );

            javaMailSender.send(mensagem);

        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail de atualização do chamado: " + e.getMessage());
        }
    }
}