package ar.com.kfgodel.sema.core.api;

import ar.com.kfgodel.nary.api.Nary;

/**
 * This type represents a persistent store for created versions
 * Created by kfgodel on 27/01/16.
 */
public interface VersionRepository {
  /**
   * Stores the given version in this repository
   * @param createdVersion
   */
  void store(Version createdVersion);

  /**
   * @return The nary of all persisted versions
   */
  Nary<Version> getVersions();
}
