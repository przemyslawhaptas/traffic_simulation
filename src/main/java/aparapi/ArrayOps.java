package aparapi;

import com.amd.aparapi.*;

/**
 * Created by przemek on 07.05.16.
 */
public class ArrayOps {

    public static void main(String[] args) {
        Kernel kernel = new MyKernel();
        kernel.execute(16);
        if (!kernel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU)){
            System.out.println("Kernel did not execute on the GPU!");
        }
    }
}
