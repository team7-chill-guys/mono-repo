package com.sparta.logistics.hub_service.global.utils;

import com.sparta.logistics.hub_service.global.exception.CommonExceptionCode;
import com.sparta.logistics.hub_service.global.exception.CustomException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

  private static final int FIRST_PAGE = 1;
  private static final int DEFAULT_PAGE_SIZE = 10;

  public static Pageable validatePageable(Pageable pageable) {
    int validPageNumber = pageable.getPageNumber();
    validatePageNumber(validPageNumber);

    int pageSize = pageable.getPageSize();
    int validPageSize = isValidPageSize(pageSize) ? pageSize : DEFAULT_PAGE_SIZE;

    return PageRequest.of(validPageNumber - 1, validPageSize, pageable.getSort());
  }

  private static void validatePageNumber(int pageNumber) {
    if (pageNumber < FIRST_PAGE) {
      throw new CustomException(CommonExceptionCode.INVALID_PAGE_NUMBER);
    }
  }

  private static boolean isValidPageSize(int pageSize) {
    if (!(pageSize == 10 || pageSize == 30 || pageSize == 50)) {
      throw new CustomException(CommonExceptionCode.INVALID_PAGE_SIZE);
    }
    return true;
  }

}
