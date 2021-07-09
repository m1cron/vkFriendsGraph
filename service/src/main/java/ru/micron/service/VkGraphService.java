package ru.micron.service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.micron.dto.SearchResultDto;
import ru.micron.feign.VkApiFeignClient;

@Service
@RequiredArgsConstructor
public class VkGraphService {

  private final ForkJoinPool threadPool = new ForkJoinPool(128);

  @Value("${app.feign.vk-api.token}")
  private String token;

  private final VkApiFeignClient vkClient;

  @PostConstruct
  public void test() {
    System.out.println(vkClient.getUserFriendList(token, ""));
  }

  public SearchResultDto startSearch(
      String vkToken, Long sourceId, List<Long> destinationIds, Byte searchDepth
  ) {
    return new SearchResultDto();
  }
}
