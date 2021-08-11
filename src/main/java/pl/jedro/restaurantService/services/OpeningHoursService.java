package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.repositories.HoursRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class OpeningHoursService {
  private HoursRepository openingHoursRepository;

  public OpeningHoursService(HoursRepository openingTimeRepository) {
    this.openingHoursRepository = openingTimeRepository;
  }

  public OpeningHour updateOpeningHours(String dayOfWeek, String fromHour, String toHour, Long openingHoursId) {
    if (openingHoursId == null || dayOfWeek == null || fromHour == null || toHour == null) {
      throw new IllegalArgumentException();
    }
    OpeningHour openingTime = openingHoursRepository.findById(openingHoursId).orElseThrow(IllegalArgumentException::new);
    openingTime.setDayOfWeek(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
    String timePattern = "HH:mm";
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);
    openingTime.setFromHour(LocalTime.parse(fromHour,timeFormatter));
    openingTime.setToHour(LocalTime.parse(toHour,timeFormatter));
    openingHoursRepository.save(openingTime);
    return openingTime;
  }
}
