package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.Version;

/**
 * This type implements a version
 * Created by kfgodel on 27/01/16.
 */
public class VersionImpl implements Version {

  private Optional<Object> metadata;
  private Object stateId;

  @Override
  public Optional<Object> metadata() {
    return metadata;
  }

  @Override
  public Object getStateId() {
    return stateId;
  }

  public static VersionImpl create(Object stateId, Optional<Object> metadata) {
    VersionImpl version = new VersionImpl();
    version.metadata = metadata;
    version.stateId = stateId;
    return version;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "[" + this.stateId + "]";
  }
}
