package com.programmingmukesh.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void orderPlaceEventListener(OrderPlacedEvent orderPlacedEvent) {
        try {
            log.info("Got message from order-placed topic: {}", orderPlacedEvent);
            // Send notification to the user
            sendOrderConfirmationEmail(orderPlacedEvent);
        } catch (Exception e) {
            log.error("Error processing order placed event: {}", e.getMessage(), e);
        }
    }

    private void sendOrderConfirmationEmail(OrderPlacedEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Order Confirmation: " + event.getOrderNumber());
        message.setText("Thank you for your order. Your order number is: " + event.getOrderNumber());

        javaMailSender.send(message);
        log.info("Order confirmation email sent to {}", event.getEmail());
    }
}
