package com.everon.carchargingsession.util;

import com.everon.carchargingsession.dto.AppCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class clearGuavaCache {
  @Scheduled(fixedDelay = 60000)
  public void clearCache() {
    AppCache.getAppCache().getInProgressSessions().cleanUp();
    AppCache.getAppCache().getStoppedSessions().cleanUp();
  }
}
