package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.sema.core.api.EntityStateObserver;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;

/**
 * This class implements the configuration to version a string variable value history
 * Created by kfgodel on 22/01/16.
 */
public class StringVariableConfiguration implements SemaConfiguration {

  private Variable<String> variable;

  public static StringVariableConfiguration create(Variable<String> variable) {
    StringVariableConfiguration configuration = new StringVariableConfiguration();
    configuration.variable = variable;
    return configuration;
  }

  @Override
  public EntityStateObserver getWorldObserver() {
    return ()-> variable.get();
  }
}
