package com.blueground.mars.properties.util;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;

public class PaginationUtil {

  public static HttpHeaders generateSliceHttpHeaders(Slice<?> slice) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Has-Next-Page", "" + slice.hasNext());
    return headers;
  }
}
