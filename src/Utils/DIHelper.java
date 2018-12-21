package Utils;

import javafx.fxml.FXMLLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DIHelper {

    /// This DI dummy supports injection of one service and one vm to a single controller
    /// In addition, it supports injection of one service into the view model
    /// The injected service will be taken from the services list passed as a parameter
    public static void injectServiceAndVM(FXMLLoader loader, String VMName, Object[] services) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        // Get VM name and inject it with the themes service into the given loader view
        Object vm = prepareVM(VMName, services);
        if (vm == null ) return;
        loader.setControllerFactory((Class<?> type) -> {
            try {
                // look for constructor taking the service and the vm as a parameters
                for (Constructor<?> c : type.getConstructors()) {
                    switch (c.getParameterCount()) {
                        case 0:
                            return type.newInstance();
                        case 1:{
                            // in case we want only the service
                            for (Object service: services) {
                                if (c.getParameterTypes()[0] == service.getClass()) {
                                    return c.newInstance(service);
                                }
                            }
                            // in case we want only the vm
                            if (c.getParameterTypes()[0] == vm.getClass()) {
                                return c.newInstance(vm);
                            }
                        }
                        case 2:
                        {
                            for (Object service: services) {
                                if (c.getParameterTypes()[0]==service.getClass() && c.getParameterTypes()[1]==vm.getClass()) {
                                    return c.newInstance(service, vm);
                                }
                            }
                        }
                        // Stopped at 3 since this is a DI for this project only.
                        // In any other scenario I would be using an actual DI mechanism.
                        case 3:
                        {
                            if (c.getParameterTypes()[0] == services[0].getClass() &&
                                    c.getParameterTypes()[1] == services[1].getClass() &&
                                    c.getParameterTypes()[2] == vm.getClass()) {
                                return c.newInstance(services[0], services[1], vm);
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

    private static Object prepareVM(String VMName, Object[] services) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String fullClassName = "ViewModels." + VMName;
        Class vmClassType = Class.forName(fullClassName);
        for (Constructor<?> c : vmClassType.getConstructors()) {
            switch (c.getParameterCount()) {
                case 0:
                    return vmClassType.newInstance();
                case 1: {
                    // in case we want only the service
                    for (Object service : services) {
                        if (c.getParameterTypes()[0] == service.getClass()) {
                            return c.newInstance(service);
                        }
                    }
                }
            }
        }
        return null;
    }
}
