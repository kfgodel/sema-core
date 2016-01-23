package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.sema.core.api.EntityStateObserver;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.SemaCore;

/**
 * This type implementes the core
 * Created by kfgodel on 22/01/16.
 */
public class SemaCoreImpl implements SemaCore {

  private SemaConfiguration config;

  @Override
  public Object getCurrentState() {
    EntityStateObserver worldObserver = config.getWorldObserver();
    Object observedState = worldObserver.describeState();
    return observedState;
  }

  public static SemaCoreImpl create(SemaConfiguration configuration) {
    SemaCoreImpl core = new SemaCoreImpl();
    core.config = configuration;
    return core;
  }

}
