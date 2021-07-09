package ru.micron;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VkDeep {

  private final Integer srcId;
  private final Integer destId;
  private final Integer deep;
}
