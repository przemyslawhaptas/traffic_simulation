package aparapi;

import com.amd.aparapi.Kernel;

/**
 * Created by przemek on 07.05.16.
 */
public class MyKernel extends Kernel {
    int[] in = new int[16];
    int[] out = new int[16];

    @Override
    public void run() {
        int i = getGlobalId();
        out[i] = in[i] * in[i];
    }
}
