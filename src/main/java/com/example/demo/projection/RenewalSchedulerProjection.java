package com.example.demo.projection;


import java.math.BigDecimal;

public interface RenewalSchedulerProjection {

    Long getCustomerId();

    BigDecimal getWalletAmount();

    BigDecimal getMinAmount();

    BigDecimal getMaxAmount();

    Long getCancelledCount();
}
