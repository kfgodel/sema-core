package ar.com.kfgodel.sema.core.api;

/**
 * This type represents an error on sema usage
 * Created by kfgodel on 24/01/16.
 */
public class SemaException extends RuntimeException {

  public SemaException(String message) {
    super(message);
  }

  public SemaException(String message, Throwable cause) {
    super(message, cause);
  }

  public SemaException(Throwable cause) {
    super(cause);
  }
}
