package com.example.carsharingservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Rental rental;
    @Column(nullable = false)
    private String paymentUrl;
    @Column(nullable = false)
    private String paymentSessionId;
    @Column(nullable = false)
    private BigDecimal paymentAmount;

    @Getter
    public enum PaymentStatus {
        PENDING("Pending"),
        PAID("Paid");
        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }

    @Getter
    public enum PaymentType {
        PAYMENT("Payment"),
        FINE("Fine");
        private final String value;

        PaymentType(String value) {
            this.value = value;
        }

        @JsonCreator
        public static PaymentType fromValue(String value) {
            for (PaymentType type : PaymentType.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid PaymentType value: " + value);
        }
    }
}
