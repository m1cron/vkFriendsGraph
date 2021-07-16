package ru.micron.controller;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.micron.dto.BasicResponse;
import ru.micron.dto.SearchResultDto;
import ru.micron.service.VkGraphService;

@RestController
@RequiredArgsConstructor
public class VkFriendsController {

  private final VkGraphService vkGraphService;

  @GetMapping("/search")
  public BasicResponse<SearchResultDto> getDepth(
      @RequestParam(name = "VK API Token") String token,
      @RequestParam(name = "Source ID") Long sourceId,
      @RequestParam(name = "Destination ID list") List<Long> destinationIds,
      @RequestParam(name = "Search depth", defaultValue = "1") @Min(1) @Max(6) Byte searchDepth
  ) {
    return new BasicResponse<SearchResultDto>()
        .setResponse(vkGraphService.startSearch(token, sourceId, destinationIds, searchDepth));
  }
}
