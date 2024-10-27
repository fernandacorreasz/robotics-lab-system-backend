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

    // Notificar o autorizador sobre uma nova solicitação de empréstimo
    public void notifyAuthorizer(LoanComponent loan) {
        String message = "Há uma nova solicitação de empréstimo para o componente: " + loan.getComponent().getName()
                + " do usuário: " + loan.getBorrower().getEmail();
        String recipientEmail = loan.getAuthorizer().getEmail();

        sendNotification(message, recipientEmail);
    }

    // Notificar o autorizador e o solicitante sobre um empréstimo atrasado
    public void notifyOverdueLoan(LoanComponent loan) {
        String message = "O empréstimo do componente: " + loan.getComponent().getName() 
                + " está atrasado. Data prevista de devolução: " + loan.getExpectedReturnDate();
        
        // Notificar o solicitante
        sendNotification(message, loan.getBorrower().getEmail());

        // Notificar o autorizador
        if (loan.getAuthorizer() != null) {
            sendNotification(message, loan.getAuthorizer().getEmail());
        }
    }

    // Enviar a notificação via repositório (ou qualquer outro meio, e-mail, etc.)
    private void sendNotification(String message, String recipientEmail) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipientEmail(recipientEmail);
        notification.setSentAt(new Date());

        notificationRepository.save(notification);
    }

     // Buscar todas as notificações de um usuário
     public List<Notification> getNotificationsByUserEmail(String email) {
        return notificationRepository.findByRecipientEmail(email);
    }

    // Marcar uma notificação como lida (opcional)
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada."));
        
        notification.setRead(true);  // Adicione um campo `read` na entidade Notification, se necessário
        notificationRepository.save(notification);
    }
}
