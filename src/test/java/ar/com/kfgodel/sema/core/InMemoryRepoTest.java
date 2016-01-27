package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.sema.core.impl.InMemoryStateRepository;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the behavior of an in-memory repository
 * Created by kfgodel on 24/01/16.
 */
@RunWith(JavaSpecRunner.class)
public class InMemoryRepoTest extends JavaSpec<SemaTestContext> {
  @Override
  public void define() {
    describe("an in-memory state repo", () -> {
      context().repo(InMemoryStateRepository::create);

      it("uses a sequential integer to identify the stored state (starting with 1)",()->{
          assertThat(context().repo().store("one")).isEqualTo(1);
          assertThat(context().repo().store("two")).isEqualTo(2);
      });

      it("returns an empty optional if the state for an identifier is not found",()->{
          assertThat(context().repo().retrieve(1).isPresent()).isFalse();
      });
      
      it("retrieves the state for a valid identifier",()->{
        Object identifier = context().repo().store("one");

        Object retrievedState = context().repo().retrieve(identifier).get();

        assertThat(retrievedState).isEqualTo("one");
      });   



    });

  }
}