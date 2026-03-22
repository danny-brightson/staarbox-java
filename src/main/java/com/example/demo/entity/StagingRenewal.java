package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staging_renewal")
public class StagingRenewal {


      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "old_pack_id")
    private Long oldPackId;

    @Column(name = "new_pack_id")
    private Long newPackId;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "next_renewal_date")
    private LocalDateTime nextRenewalDate;

    @Column(name = "processed")
    private Boolean processed = false;

    public Long getId() { return id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getOldPackId() { return oldPackId; }
    public void setOldPackId(Long oldPackId) { this.oldPackId = oldPackId; }

    public Long getNewPackId() { return newPackId; }
    public void setNewPackId(Long newPackId) { this.newPackId = newPackId; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public LocalDateTime getNextRenewalDate() { return nextRenewalDate; }
    public void setNextRenewalDate(LocalDateTime nextRenewalDate) { this.nextRenewalDate = nextRenewalDate; }

    public Boolean getProcessed() { return processed; }
    public void setProcessed(Boolean processed) { this.processed = processed; }
}