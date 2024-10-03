package com.programmingmukesh.notification;

import lombok.Data;

@Data
public class OrderPlacedEvent {
    private String orderNumber;
    private String email;
}
