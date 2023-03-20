package com.example.library_sber.scheduler;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.service.AbonementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final AbonementService abonementService;

    @Scheduled(cron = "0 0 1 * * *")
    public void sendNotificationsByBookDelay() {

        List<Abonement> abonements = abonementService.getAllAbonements();
        for (Abonement abonement : abonements) {
            Long abonementId = abonement.getAbonementId();
            AtomicBoolean expired = abonementService.checkBookExpiration(abonementId);

            if(expired.equals(true)){
                // вызов метода mail sender - не реализован
            }
        }
    }
}
