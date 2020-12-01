/**
 * This services is use with validate, store and request for information related with Calls.
 * <p>
 * Implements the Singleton Pattern.
 */
package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.CallPersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.CallService;

import java.util.List;

public class CallBusinessService implements CallService<Call> {
    private static CallBusinessService instance;
    private final CallService<Call> callPersistenceService;
    /**
     * Constructor of the Class. Injects the persistence service.
     *
     * @param callPersistenceService CallService that manage the persistence of the ServerAdministrator's data.
     */
    private CallBusinessService(CallService<Call> callPersistenceService) {
        this.callPersistenceService = callPersistenceService;
    }
    /**
     * Get the instance of the Call Internal Service.
     *
     * @return {@code CallInternalService} instance of the service.
     */
    public static CallBusinessService getInstance() {
        if (instance == null) {
            instance = new CallBusinessService(CallPersistenceService.getInstance());
        }
        return instance;
    }
    /**
     * Search and returns a list of Calls.
     *
     * @return {@code ServerAdministrator} if have been found. {@code null} if doesn't exist
     * a Call.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to get the Call.
     */
    @Override
    public List<Call> get() throws BusinessException, PersistenceException {
        return callPersistenceService.get();
    }

    /**
     * Inserts a new Call to the repository. This also validates if the
     * Call is valid.
     *
     * @param call Call to be added.
     * @return {@code true} if the Call have been added, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              Call.
     */
    @Override
    public boolean insert(Call call) throws BusinessException, PersistenceException {
        validateCall(call);
        return callPersistenceService.insert(call);
    }

    @Override
    public boolean update(Call call) throws BusinessException, PersistenceException {
        validateCall(call);
        return callPersistenceService.update(call);
    }

    /**
     * Deletes an Call of the repository.
     *
     * @param Call Call to be deleted.
     * @return {@code true} if the Call have been deleted, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              Call.
     */
    @Override
    public boolean delete(Call Call) throws BusinessException, PersistenceException {
        if (Call == null) {
            throw new BusinessException("Call not provided.", BusinessException.CALL_OBJECT_NOT_PROVIDED);
        }
        if (Call.getTelephone_Number() < 0) {
            throw new BusinessException("Call's email not provided.", BusinessException.CALL_TELEPHONE_NUMBER_NOT_PROVIDED);
        }
        return callPersistenceService.delete(Call);
    }

    /**
     * Validates basic required information about the Call.
     *
     * @param Call Call to be validate.
     * @throws BusinessException Exception threw when the Call information is not valid.
     */
    private void validateCall(final Call Call) throws BusinessException {
        if (Call == null) {
            throw new BusinessException("Call not provided.", BusinessException.CALL_OBJECT_NOT_PROVIDED);
        }
        if (Call.getTelephone_Number() < 0) {
            throw new BusinessException("Call's email not provided.", BusinessException.CALL_TELEPHONE_NUMBER_NOT_PROVIDED);
        }
        if (Call.getDestination_Telephone_Number() < 0) {
            throw new BusinessException("Call's email not provided.", BusinessException.CALL_DESTINATION_TELEPHONE_NUMBER_NOT_PROVIDED);
        }
        if (Call.getEnd_Date().equals("")) {
            throw new BusinessException("Call's first name not provided.", BusinessException.CALL_END_DATE_NOT_PROVIDED);
        }
        if (Call.getStart_Date().equals("")) {
            throw new BusinessException("Call's last name not provided.", BusinessException.CALL_START_DATE_NOT_PROVIDED);
        }
    }
}
