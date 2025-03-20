package com.sparta.logistics.hub_service.hubroute.application.service;

import com.sparta.logistics.hub_service.hub.domain.entity.Hub;
import com.sparta.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sparta.logistics.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.KakaoResponseDto;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.KakaoResponseDto.Route;
import com.sparta.logistics.hub_service.hubroute.application.dto.response.KakaoResponseDto.Section;
import com.sparta.logistics.hub_service.hubroute.domain.entity.HubRoute;
import com.sparta.logistics.hub_service.hubroute.domain.repository.HubRouteRepository;
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

  private final HubRepository hubRepository;
  private final HubRouteRepository hubRouteRepository;

  @Value("${kakao.rest.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;

  @Override
  public HubRouteCreateResponseDto autoCreateHubRoute(HubRouteCreateRequestDto requestDto, String userIdHeader) {

    Hub startHub = hubRepository.findById(requestDto.getStartHubId())
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));
    Hub endHub = hubRepository.findById(requestDto.getEndHubId())
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    headers.set("Authorization", "KakaoAK " + apiKey);

    String startLocation = startHub.getLongitude() + "," + startHub.getLatitude();
    String endLocation = endHub.getLongitude() + "," + endHub.getLatitude();

    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    String encodeOrigin = URLEncoder.encode(startLocation, StandardCharsets.UTF_8);
    String encodeDestination = URLEncoder.encode(endLocation, StandardCharsets.UTF_8);
    String rawURI =
        "https://apis-navi.kakaomobility.com/v1/directions?origin=" + encodeOrigin + "&destination="
            + encodeDestination + "&summary=" + true;
    URI uri = URI.create(rawURI);

    ResponseEntity<KakaoResponseDto> res = restTemplate.exchange(uri, HttpMethod.GET, entity,
        KakaoResponseDto.class);

    Integer time = 0;
    Double distance = 0.0;

    if (res.getBody() != null && res.getBody().getRoutes() != null && !res.getBody().getRoutes()
        .isEmpty()) {
      Route route = res.getBody().getRoutes().get(0);
      if (route.getSections() != null && !route.getSections().isEmpty()) {
        Section section = route.getSections().get(0);
        distance = section.getDistance();
        int duration = section.getDuration();
        time = duration / 60;
        log.info("Distance : " + distance);
        log.info("time : " + time);
      }
    }

    if (hubRouteRepository.existsByStartHubId(requestDto.getStartHubId())
        && hubRouteRepository.existsByEndHubId(
        requestDto.getEndHubId())) {
      throw new IllegalArgumentException("이미 등록된 경로 입니다.");
    }

    Long currentId = Long.valueOf(userIdHeader);

    HubRoute hubRoute = hubRouteRepository.save(
        HubRoute.builder()
            .startHubId(requestDto.getStartHubId())
            .endHubId(requestDto.getEndHubId())
            .startHubName(startHub.getHubName())
            .endHubName(endHub.getHubName())
            .deliveryTime(time)
            .deliveryDistance(distance)
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );
    return new HubRouteCreateResponseDto(hubRoute);  }

}
