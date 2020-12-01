package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.LinePersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineService;

import java.util.List;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException.*;

public class LineBusinessService implements LineService<Line> {
    /* Instance */
    private static LineBusinessService instance;
    private LineService<Line> linePersistenceService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     */
    private LineBusinessService( final LineService<Line> linePersistenceService) {
        this.linePersistenceService = linePersistenceService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static LineBusinessService getInstance() {
        if (instance == null) {
            instance = new LineBusinessService(LinePersistenceService.getInstance());
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
    public List<Line> get() throws BusinessException, PersistenceException {
        return linePersistenceService.get();
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line Line.
     */
    @Override
    public void insert(Line line) throws BusinessException, PersistenceException {
        validateLine(line);
        linePersistenceService.insert(line);
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line Line.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     */
    @Override
    public void update(Line line) throws BusinessException, PersistenceException {
        validateLine(line);
        linePersistenceService.update(line);
    }

    private void validateLine(Line line) throws BusinessException{
        if (line.getTelephoneNumber() <= 0){
            throw new BusinessException("Telephone number not valid.", LINE_TELEPHONE_NUMBER_NOT_VALID);
        }
        if (line.getPointsQuantity() < 0){
            throw new BusinessException("Line points quantity not valid.", LINE_POINT_QUANTITY_NOT_PROVIDED);
        }
        if (!(line.getStatus().equals("A") || line.getStatus().equals("I"))){
            throw new BusinessException("Line status not valid.", LINE_STATUS_NOT_VALID);
        }
        if (line.getType()<0 || line.getType()>255){
            throw new BusinessException("Line type not valid.", LINE_TYPE_NOT_PROVIDED);
        }
    }

    /**
     * Deletes an User of the repository.
     *
     * @param line User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    @Override
    public boolean delete(Line line) throws BusinessException, PersistenceException {
        if(line.getTelephoneNumber() <= 0){
            throw new BusinessException("Telephone number not valid.", LINE_TELEPHONE_NUMBER_NOT_VALID);
        }
        return linePersistenceService.delete(line);
    }
}
