import com.zeroc.Ice.Current;
import AppInterfaces.Integral;
import AppInterfaces.Server;
import model.ModelIntegral;
import model.MonteCarlo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ServerServant implements Server {

    private static final int NUM_TASKS = 10;

    private ForkJoinPool forkJoinPool;

    private static MonteCarlo monteCarlo;

    public ServerServant() {
        forkJoinPool = new ForkJoinPool(NUM_TASKS);
        monteCarlo = new MonteCarlo();
    }

    //Convertir la integral de Ice en la de nuestro modelo
    @Override
    public String solveIntegral(Integral integral, Current current) {
        ModelIntegral modelIntegral = new ModelIntegral(
                integral.functionnString,
                integral.lowerRange,
                integral.upperRange
        );

        // Crear 10 nuevas integrales (del modelo) a partir de la que se recibe
        List<ModelIntegral> integralTasks = createIntegralTasks(modelIntegral);

        // Ejecutar las tareas en paralelo usando Fork/Join
        BigDecimal totalArea = BigDecimal.ZERO;
        try {
            totalArea = forkJoinPool.submit(() -> forkJoinPool.invoke(new IntegralTask(integralTasks))).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Convertir el BigDecimal en String y retornarlo
        return totalArea.setScale(5, RoundingMode.HALF_UP).toString();
    }

    @Override
    public void printResponse(String res, Current current) {
        System.out.println(res);
    }

    private List<ModelIntegral> createIntegralTasks(ModelIntegral integral) {
        List<ModelIntegral> integralTasks = new ArrayList<>();
        for (int i = 0; i < NUM_TASKS; i++) {
            integralTasks.add(new ModelIntegral(integral.toString(), integral.getLowerRange(), integral.getUpperRange()));
        }
        return integralTasks;
    }

    // Clase interna para representar una tarea de integral
    private static class IntegralTask extends RecursiveTask<BigDecimal> {

        private final List<ModelIntegral> integralTasks;

        IntegralTask(List<ModelIntegral> integralTasks) {
            this.integralTasks = integralTasks;
        }

        @Override
        protected BigDecimal compute() {
            if (integralTasks.size() == 1) {
                ModelIntegral integral = integralTasks.get(0);
                return monteCarlo.solve(integral);
            }

            int middle = integralTasks.size() / 2;
            List<ModelIntegral> leftTasks = integralTasks.subList(0, middle);
            List<ModelIntegral> rightTasks = integralTasks.subList(middle, integralTasks.size());

            IntegralTask leftTask = new IntegralTask(leftTasks);
            IntegralTask rightTask = new IntegralTask(rightTasks);

            leftTask.fork();
            BigDecimal rightResult = rightTask.compute();
            BigDecimal leftResult = leftTask.join();

            return leftResult.add(rightResult);
        }
    }
}
