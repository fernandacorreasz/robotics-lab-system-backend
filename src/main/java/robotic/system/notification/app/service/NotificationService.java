package robotic.system.notification.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.notification.domain.model.Notification;
import robotic.system.notification.repository.NotificationRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Notificar sobre empréstimos em atraso
    public void notifyOverdueLoan(LoanComponent loan) {
        String message = "O empréstimo do componente: " + loan.getComponent().getName() 
                + " está atrasado. Data prevista de devolução: " + loan.getExpectedReturnDate();

        // Notificar o solicitante (usuário que pegou o empréstimo)
        sendNotification(message, loan.getBorrower().getEmail());

        // Notificar o autorizador
        if (loan.getAuthorizer() != null) {
            sendNotification(message, loan.getAuthorizer().getEmail());
        }
    }

    // Enviar a notificação para o destinatário
    private void sendNotification(String message, String recipientEmail) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipientEmail(recipientEmail);
        notification.setSentAt(new Date());
        notificationRepository.save(notification);
    }

    // Buscar todas as notificações por e-mail do usuário
    public List<Notification> getNotificationsByUserEmail(String email) {
        return notificationRepository.findByRecipientEmail(email);
    }

    // Excluir notificação por e-mail do usuário e ID
    public boolean deleteNotificationByUserEmailAndId(String email, UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .filter(notification -> notification.getRecipientEmail().equals(email))
                .map(notification -> {
                    notificationRepository.delete(notification);
                    return true;
                })
                .orElse(false);
    }
}
