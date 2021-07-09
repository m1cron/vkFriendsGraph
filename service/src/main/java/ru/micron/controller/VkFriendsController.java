package ru.micron.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.micron.dto.BasicResponse;
import ru.micron.dto.SearchResultDto;
import ru.micron.service.VkGraphService;

@RequiredArgsConstructor
public class VkFriendsController {

  private final VkGraphService vkGraphService;

  @GetMapping("/search")
  public BasicResponse<SearchResultDto> getDepth(
      @RequestParam String vkToken,
      @RequestParam Long sourceId,
      @RequestParam List<Long> destinationIds,
      @RequestParam Byte searchDepth
  ) {
    return new BasicResponse<SearchResultDto>()
        .setResponse(vkGraphService.startSearch(vkToken, sourceId, destinationIds, searchDepth));
  }
}
