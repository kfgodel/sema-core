package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.sema.core.api.Version;
import ar.com.kfgodel.sema.core.api.VersionRepository;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Implementation that stores everything in memory
 *
 * Created by kfgodel on 27/01/16.
 */
public class InMemoryVersionRepository implements VersionRepository {


  private Deque<Version> versions;

  public static InMemoryVersionRepository create() {
    InMemoryVersionRepository repository = new InMemoryVersionRepository();
    repository.versions = new LinkedList<>();
    return repository;
  }

  @Override
  public void store(Version createdVersion) {
    versions.addFirst(createdVersion);
  }

  @Override
  public Nary<Version> getVersions() {
    return NaryFromNative.create(versions.stream());
  }
}
