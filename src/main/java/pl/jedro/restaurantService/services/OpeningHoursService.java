package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.repositories.HoursRepository;

@Service
public class OpeningHoursService {
  private HoursRepository openingHoursRepository;

  public OpeningHoursService(HoursRepository openingTimeRepository) {
    this.openingHoursRepository = openingTimeRepository;
  }
  public OpeningHour updateOpeningHours(String dayOfWeek, String from, String to, Long openingHoursId) {
    if (openingHoursId == null || dayOfWeek == null || from == null || to == null) {
      throw new IllegalArgumentException();
    }
    OpeningHour openingTime = openingHoursRepository.findById(openingHoursId).orElseThrow(IllegalArgumentException::new);
    openingTime.setDayOfWeek(dayOfWeek);
    openingTime.setFromHour(from);
    openingTime.setToHour(to);
    openingHoursRepository.save(openingTime);
    return openingTime;
  }
}
