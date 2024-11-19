package moum.project.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

//@Component
public class MultiPatternDateTimeConverter implements Converter<String, LocalDateTime> {
  private final List<DateTimeFormatter> formatters = Arrays.asList(
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH"),
      DateTimeFormatter.ofPattern("yyyy-MM-dd"),
      DateTimeFormatter.ofPattern("yyyy-MM"),
      DateTimeFormatter.ofPattern("yyyy")
  );

  @Override
  public LocalDateTime convert(String source) {
    for (DateTimeFormatter formatter : formatters) {
      try {
        return LocalDateTime.parse(source, formatter);
      } catch (Exception ignored) {
      }
    }

    if (source.length() == 4) {
      return LocalDateTime.parse(source + "-01-01T00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    throw new IllegalArgumentException("Invalid date format: " + source);
  }
}
