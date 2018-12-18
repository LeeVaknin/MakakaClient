import Services.ThemeManager;
import Services.ThemeManagerService;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.lang.reflect.Constructor;

public class DIHelper {

    public static void injectServiceAndVM(FXMLLoader loader, String VMName, Object service) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        // Get VM name and inject it with the themes service into the given loader view
        String fullClassName = "ViewModels." +  VMName;
        Class vmClassType = Class.forName(fullClassName);
        Object vm = (vmClassType).newInstance();
        loader.setControllerFactory((Class<?> type) -> {
            try {
                // look for constructor taking the service and the vm as a parameters
                for (Constructor<?> c : type.getConstructors()) {
                    switch (c.getParameterCount()) {
                        case 0:
                            return type.newInstance();
                        case 1:{
                            // in case we want only the service
                            if (c.getParameterTypes()[0] == service.getClass()) {
                                return c.newInstance(service);
                            }
                            // in case we want only the vm
                            if (c.getParameterTypes()[0] == vmClassType) {
                                return c.newInstance(vm);
                            }
                        }
                        case 2:
                        {
                            if (c.getParameterTypes()[0]==service.getClass() && c.getParameterTypes()[1]==vmClassType) {
                                return c.newInstance(service, vm);
                            }
                        }
                        // didn't find appropriate constructor, just use default constructor:
                        default:
                            return type.newInstance();
                    }
                }
                return type.newInstance();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        });
    }

}
