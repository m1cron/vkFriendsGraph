package ru.micron.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.micron.dto.BasicResponse;
import ru.micron.dto.VkFriendResponseDto;

@FeignClient(
    name = "vk-api",
    url = "${app.feign.vk-api.url}"
)
public interface VkApiFeignClient {

  @GetMapping("/friends.get?v=5.124")
  BasicResponse<VkFriendResponseDto> getUserFriendList(
      @RequestParam("access_token") String token,
      @RequestParam(value = "user_id", required = false) String userId
  );
}
