package vn.rvbb.tf.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tensorflow.ConcreteFunction;
import org.tensorflow.Signature;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.math.Add;
import org.tensorflow.types.TInt32;

@Slf4j
@SpringBootApplication
public class TFApplication implements CommandLineRunner {

  public static void main(String[] args) {
      log.info(">>>>>>>>>>>>>>>>>>>>>STARTING TF app.... >>>>>>>>>>>>>>>>>>>>>>>>>>");
      SpringApplication.run(TFApplication.class, args);
      log.info(">>>>>>>>>>>>>>>>>>>>>Start TF app complete >>>>>>>>>>>>>>>>>>>>>>>>>>");
  }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello TensorFlow " + TensorFlow.version());

        try (ConcreteFunction dbl = ConcreteFunction.create(TFApplication::dbl);
             Tensor<TInt32> x = TInt32.scalarOf(10);
             Tensor<TInt32> dblX = dbl.call(x).expect(TInt32.DTYPE)) {
            System.out.println(x.data().getInt() + " doubled is " + dblX.data().getInt());
        }
    }

    private static Signature dbl(Ops tf) {
        Placeholder<TInt32> x = tf.placeholder(TInt32.DTYPE);
        Add<TInt32> dblX = tf.math.add(x, x);
        return Signature.builder().input("x", x).output("dbl", dblX).build();
    }
}
