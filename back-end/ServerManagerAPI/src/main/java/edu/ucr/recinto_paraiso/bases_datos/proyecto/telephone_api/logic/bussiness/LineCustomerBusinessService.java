package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.LineCustomerPersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineCustomerService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;

import java.util.List;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException.*;

public class LineCustomerBusinessService implements LineCustomerService<LineCustomer> {
    /* Instance */
    private static LineCustomerBusinessService instance;
    private LineCustomerService<LineCustomer> persistenceService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     */
    private LineCustomerBusinessService(final LineCustomerService<LineCustomer> linePersistenceService) {
        this.persistenceService = linePersistenceService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static LineCustomerBusinessService getInstance() {
        if (instance == null) {
            instance = new LineCustomerBusinessService(LineCustomerPersistenceService.getInstance());
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
    public List<LineCustomer> get() throws BusinessException, PersistenceException {
        return persistenceService.get();
    }

    /**
     * Search and returns an User by it's username.
     *
     * @param telephoneNumber
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     */
    @Override
    public LineCustomer getByTelephoneNumber(int telephoneNumber) throws BusinessException, PersistenceException {
        if(telephoneNumber <= 0){
            throw new BusinessException("Telephone number not valid.", LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_VALID);
        }
        return persistenceService.getByTelephoneNumber(telephoneNumber);
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param lineCustomer Line.
     */
    @Override
    public void insert(LineCustomer lineCustomer) throws BusinessException, PersistenceException {
        validateLineCustomer(lineCustomer);
        persistenceService.insert(lineCustomer);
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param lineCustomer Line.
     */
    @Override
    public void update(LineCustomer lineCustomer) throws BusinessException, PersistenceException {
        validateLineCustomer(lineCustomer);
        persistenceService.update(lineCustomer);
    }

    private void validateLineCustomer(LineCustomer lineCustomer) throws BusinessException{
        if (lineCustomer.getTelephoneNumber() <= 0){
            throw new BusinessException("Telephone number not valid.", LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_VALID);
        }
        if (lineCustomer.getId() < 0){
            throw new BusinessException("Customer id not valid.", LINE_CUSTOMER_ID_NOT_PROVIDED);
        }
        if (lineCustomer.getFirstName() == null || lineCustomer.getFirstName().equals("")){
            throw new BusinessException("Customer first name not provided.", LINE_CUSTOMER_FIRST_NAME_NOT_PROVIDED);
        }
        if (lineCustomer.getLastName() == null || lineCustomer.getLastName().equals("")){
            throw new BusinessException("Customer last name not provided.", LINE_CUSTOMER_LAST_NAME_NOT_PROVIDED);
        }
        if (lineCustomer.getEmail() == null || lineCustomer.getEmail().equals("")){
            throw new BusinessException("Customer email not provided.", LINE_CUSTOMER_EMAIL_NOT_PROVIDED);
        }
        if (!Utility.isEmailValid(lineCustomer.getEmail())){
            throw new BusinessException("Customer email not valid.", LINE_CUSTOMER_EMAIL_NOT_VALID);
        }
    }

    /**
     * Deletes an User of the repository.
     *
     * @param lineCustomer User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    @Override
    public boolean deleteByTelephoneNumber(LineCustomer lineCustomer) throws BusinessException, PersistenceException {
        if(lineCustomer.getTelephoneNumber() <= 0){
            throw new BusinessException("Customer id not valid.", LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_VALID);
        }
        return persistenceService.deleteByTelephoneNumber(lineCustomer);
    }
}
