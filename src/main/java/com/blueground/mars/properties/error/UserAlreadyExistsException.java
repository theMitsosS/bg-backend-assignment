package com.blueground.mars.properties.error;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(Class clazz, String... searchParamsMap) {
    super(UserAlreadyExistsException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
  }

  private static String generateMessage(String entity, Map<String, String> searchParams) {
    return StringUtils.capitalize(entity) +
            " This username already exists in the database. The e-mail must be unique. Please chose another one. " +
            searchParams;
  }

  private static <K, V> Map<K, V> toMap(
          Class<K> keyType, Class<V> valueType, Object... entries) {
    if (entries.length % 2 == 1)
      throw new IllegalArgumentException("Invalid entries");
    return IntStream.range(0, entries.length / 2).map(i -> i * 2)
            .collect(HashMap::new,
                    (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                    Map::putAll);
  }

}