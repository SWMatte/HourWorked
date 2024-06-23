package assegnazione.ore;

public abstract class BaseService {


    protected String getCurrentClassName() {
        return this.getClass().getName();
    }

    protected String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }


}
