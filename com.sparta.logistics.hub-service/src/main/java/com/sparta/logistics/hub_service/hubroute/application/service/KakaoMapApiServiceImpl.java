package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.KakaoResponseDto;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoMapApiServiceImpl implements KakaoMapService {

  @Value("${kakao.rest.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;

  @Override
  public HubRouteCreateResponseDto autoCreateHubRoute(HubRouteCreateRequestDto requestDto) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    headers.set("Authorization", "KakaoAK " + apiKey);

    String location1 = requestDto.getStartLongitude() + "," + requestDto.getStartLatitude();
    String location2 = requestDto.getEndLongitude() + "," + requestDto.getEndLatitude();

    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    String encodeOrigin = URLEncoder.encode(location1, StandardCharsets.UTF_8);
    String encodeDestination = URLEncoder.encode(location2, StandardCharsets.UTF_8);
    String rawURI =
        "https://apis-navi.kakaomobility.com/v1/directions?origin=" + encodeOrigin + "&destination="
            + encodeDestination + "&summary=" + true;
    URI uri = URI.create(rawURI);

    ResponseEntity<KakaoResponseDto> res = restTemplate.exchange(uri, HttpMethod.GET, entity,
        KakaoResponseDto.class);

    if (res.getBody() != null && res.getBody().getRoutes() != null && !res.getBody().getRoutes()
        .isEmpty()) {
      KakaoResponseDto.Route route = res.getBody().getRoutes().get(0);
      if (route.getSections() != null && !route.getSections().isEmpty()) {
        KakaoResponseDto.Section section = route.getSections().get(0);
        double distance = section.getDistance();
        int duration = section.getDuration();
        log.info("Distance : " + distance);
        log.info("Duration : " + duration);
      }
    }

    // TODO : 데이터 저장하기 (그러려면 hub_id 값으로 정보를 가져와야 한다)

    return null;
  }

}
