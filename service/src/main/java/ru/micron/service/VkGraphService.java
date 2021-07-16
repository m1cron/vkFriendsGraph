package ru.micron.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.micron.dto.FriendDto;
import ru.micron.dto.SearchResultDto;
import ru.micron.feign.VkApiFeignClient;
import ru.micron.service.graph.Graph;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkGraphService {

  private final Graph<FriendDto> graph = new Graph<>();
  private final VkApiFeignClient vkClient;

  public SearchResultDto startSearch(
      String token, long sourceId, List<Long> destinationIds, byte searchDepth
  ) {
    addFriends(token, sourceId, 0, searchDepth);

    List<FriendDto> friendDtoList = new ArrayList<>(destinationIds.size() * searchDepth);

    for (var id : destinationIds) {
      for (byte i = 1; i <= searchDepth; i++) {
        var friendDto = new FriendDto().setId(id).setDepth(i);
        if (!graph.hasVertex(friendDto)) {
          friendDto.setDepth(-1);
        }
        friendDtoList.add(friendDto);
      }
    }
    return new SearchResultDto()
        .setSourceId(sourceId)
        .setFriends(friendDtoList);
  }

  private void addFriends(String token, long id, int curDepth, int depth) {
    if (curDepth - 1 == depth) {
      return;
    }
    log.info("id - {}\tdepth - {}", id, curDepth);

    var src = new FriendDto(id, curDepth);

    var response = vkClient.getUserFriendList(token, id);
    sleep();
    if (response.getError() != null) {
      return;
    }
    var friends = response.getResponse().getItems();
    friends.forEach(friend ->
        graph.addEdge(src, new FriendDto(friend, depth), true)
    );

    for (var friend : friends) {
      addFriends(token, friend, curDepth + 1, depth);
    }
  }

  private void sleep() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
