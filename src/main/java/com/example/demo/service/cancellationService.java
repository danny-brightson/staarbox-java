package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CancelledDate;
import com.example.demo.repo.cancelledDateRepo;

@Service
public class cancellationService {

    @Autowired
    private cancelledDateRepo cancelledDateRepository;

    public String handleCancellation(Long custId,
                                     List<LocalDate> cancelledDates,
                                     Boolean isCancelled) {

        for (LocalDate date : cancelledDates) {

            LocalDate cancelledDateValue = date;

            // 🔹 Check if record already exists for this customer + date
            Optional<CancelledDate> existing =
                    cancelledDateRepository
                            .findByCustomerIdAndCancelledDate(custId, cancelledDateValue);

            if (Boolean.TRUE.equals(isCancelled)) {

                // ======================================================
                // CANCEL REQUEST
                // ======================================================

                // ✅ Already cancelled → skip duplicate insert
                if (existing.isPresent() && existing.get().getStatusId() == 1L) {
                    continue;
                }

                // ✅ If revoked before, reactivate same row
                if (existing.isPresent()) {
                    CancelledDate cancel = existing.get();
                    cancel.setStatusId(1L);
                    cancel.setModefiedBy("User");
                    cancel.setModefiedTime(LocalDate.now());

                    cancelledDateRepository.save(cancel);
                }

                // ✅ Fresh insert
                else {
                    CancelledDate cancel = new CancelledDate();
                    cancel.setCustomerId(custId);
                    cancel.setCancelledDate(cancelledDateValue);
                    cancel.setStatusId(1L); // Cancelled
                    cancel.setCreatedBy("User");
                    cancel.setCreatedTime(LocalDateTime.now());

                    cancelledDateRepository.save(cancel);
                }

            } else {

                // ======================================================
                // REVOKE REQUEST
                // ======================================================

                if (existing.isPresent() && existing.get().getStatusId() == 1L) {

                    CancelledDate cancel = existing.get();
                    cancel.setStatusId(2L); // Revoked
                    cancel.setModefiedBy("User");
                    cancel.setModefiedTime(LocalDate.now());

                    cancelledDateRepository.save(cancel);
                }
            }
        }

        return Boolean.TRUE.equals(isCancelled)
                ? "Cancelled Successfully"
                : "Revoked Successfully";
    }

    public List<LocalDate> getCancelledDates(Long customerId) {
        return cancelledDateRepository.findActiveCancelledDates(customerId);
    }
}