package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Service;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.LinePersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.ServicePersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.ServiceService;

import java.util.List;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException.*;

public class ServiceBusinessService implements ServiceService<Service> {
    /* Instance */
    private static ServiceBusinessService instance;
    private ServiceService<Service> servicePersistenceService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     */
    private ServiceBusinessService(final ServiceService<Service> servicePersistenceService) {
        this.servicePersistenceService = servicePersistenceService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static ServiceBusinessService getInstance() {
        if (instance == null) {
            instance = new ServiceBusinessService(ServicePersistenceService.getInstance());
        }
        return instance;
    }
    /**
     * Search and returns an User by it's username.
     *
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     */
    @Override
    public List<Service> get() throws BusinessException, PersistenceException {
        return servicePersistenceService.get();
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param service Service.
     */
    @Override
    public void insert(Service service) throws BusinessException, PersistenceException {
        validateLine(service);
        servicePersistenceService.insert(service);
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param service Service.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     */
    @Override
    public void update(Service service) throws BusinessException, PersistenceException {
        validateLine(service);
        servicePersistenceService.update(service);
    }

    private void validateLine(Service service) throws BusinessException{
        if (service.getService_Code() <= 0){
            throw new BusinessException("Code number not valid.", SERVICE_CODE_NOT_VALID);
        }
        if (service.getCost() < 0){
            throw new BusinessException("Cost not valid.", SERVICE_COST_NOT_PROVIDED);
        }
        if (!(service.getStatus().equals("A") || service.getStatus().equals("I"))){
            throw new BusinessException("Line status not valid.", SERVICE_CODE_NOT_VALID);
        }

    }

    /**
     * Deletes an User of the repository.
     *
     * @param service User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    @Override
    public boolean delete(Service service) throws BusinessException, PersistenceException {
        if(service.getService_Code() <= 0){
            throw new BusinessException("Code number not valid.", SERVICE_CODE_NOT_VALID);
        }
        return servicePersistenceService.delete(service);
    }
}
