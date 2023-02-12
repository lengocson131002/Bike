package com.swd.bike.scheduler;

import com.swd.bike.config.SpringContext;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.service.interfaces.ITripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
public class RemindComingTripTask extends ScheduledTask {
    private Long tripId;

    private ITripService tripService;

    private PlatformTransactionManager transactionManager;

    public RemindComingTripTask(Long tripId, Instant time) {
        super(time);
        this.tripId = tripId;
        this.tripService = SpringContext.getBean(ITripService.class);
        this.transactionManager = SpringContext.getBean(PlatformTransactionManager.class);
    }

    @Override
    @Transactional
    public void run() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                doTask();
            }
        });
    }

    private void doTask() {
        try {
            Trip trip = tripService.getTrip(tripId);
            if (trip == null || !TripStatus.CREATED.equals(trip.getStatus())) {
                return;
            }

            log.info("[SCHEDULING]: Start remind coming trip. ID: {}", trip.getId());
            Account grabber = trip.getGrabber();
            Account passenger = trip.getPassenger();

            //Todo notification

            log.info("User {} has a coming trip at {}", grabber.getName(), trip.getPostedStartTime());
            log.info("User {} has a coming trip at {}", passenger.getName(), trip.getPostedStartTime());
        } catch (Exception ex) {
            log.error("[SCHEDULING] Remind trip with ID {} error: {}", tripId, ex.getMessage());
        }
    }
}
