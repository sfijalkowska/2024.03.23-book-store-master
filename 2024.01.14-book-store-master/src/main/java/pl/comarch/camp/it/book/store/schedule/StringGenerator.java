package pl.comarch.camp.it.book.store.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StringGenerator {

    @Scheduled(cron = "0/10 * * ? * *")
    public void generateString() {
        if(LocalDateTime.now().getYear() > 2024) {
            return;
        }
        System.out.println("jakas informacja");
        System.out.println(LocalDateTime.now());
    }
}
