import com.zeroc.Ice.Current;

import AppInterfaces.BrokerPrx;
import AppInterfaces.Integral;
import AppInterfaces.Server;
import model.MonteCarlo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ServerServent implements Server {

    private static final int NUM_TASKS = 10;

    private ForkJoinPool forkJoinPool;
    private double load;
    private BrokerPrx brokerPrx;

    private static MonteCarlo monteCarlo;

    public ServerServent(BrokerPrx brokerPrx) {
        load = 0;
        this.brokerPrx = brokerPrx;
        forkJoinPool = new ForkJoinPool(NUM_TASKS);
        monteCarlo = new MonteCarlo();
    }

    //Convertir la integral de Ice en la de nuestro modelo
    @Override
    public void solveIntegral(int requestID, Integral integral, Current current) {
        model.Integral modelIntegral = new model.Integral(
                integral.functionnString,
                integral.lowerRange,
                integral.upperRange
        );

        // Crear 10 nuevas integrales (del modelo) a partir de la que se recibe
        List<model.Integral> integralTasks = createIntegralTasks(modelIntegral);

        // Ejecutar las tareas en paralelo usando Fork/Join
        BigDecimal totalArea = BigDecimal.ZERO;
        try {
            totalArea = forkJoinPool.submit(() -> forkJoinPool.invoke(new IntegralTask(integralTasks))).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        
        String area = totalArea.setScale(5, RoundingMode.HALF_UP).toString();

        brokerPrx.join(requestID, area);
    }

    @Override
    public void printResponse(String res, Current current) {
        System.out.println(res);
    }

    @Override
    public double getLoad(Current current) {
        return load;
    }

    private List<model.Integral> createIntegralTasks(model.Integral integral) {
        double a = integral.getLowerRange();
        double b = integral.getUpperRange();
        double div = (b-a)/NUM_TASKS;

        List<model.Integral> integralTasks = new ArrayList<>();

        for (int i = 0; i < NUM_TASKS; i++) {
            integralTasks.add(new model.Integral(integral.getFunctionString(), a + (i*div), a + ((i+1)*div)));
        }
        return integralTasks;
    }

    // Clase interna para representar una tarea de integral
    private static class IntegralTask extends RecursiveTask<BigDecimal> {

        private List<model.Integral> integralTasks;

        IntegralTask(List<model.Integral> integralTasks) {
            this.integralTasks = integralTasks;
        }

        @Override
        protected BigDecimal compute() {
            if (integralTasks.size() == 1) {
                model.Integral integral = integralTasks.get(0);
                return monteCarlo.solve(integral);
            }

            int middle = integralTasks.size() / 2;
            List<model.Integral> leftTasks = integralTasks.subList(0, middle);
            List<model.Integral> rightTasks = integralTasks.subList(middle, integralTasks.size());

            IntegralTask leftTask = new IntegralTask(leftTasks);
            IntegralTask rightTask = new IntegralTask(rightTasks);

            leftTask.fork();
            BigDecimal rightResult = rightTask.compute();
            BigDecimal leftResult = leftTask.join();

            return leftResult.add(rightResult);
        }
    }
}
