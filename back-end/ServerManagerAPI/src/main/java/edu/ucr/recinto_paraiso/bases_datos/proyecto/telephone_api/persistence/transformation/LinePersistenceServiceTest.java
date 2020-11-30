package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;


import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import java.util.List;

public class LinePersistenceServiceTest {

   public static void main(String...args){
       try {
           //LinePersistenceService.getInstance().insert(new LineBuilder().setTelephone_Number(85426413).setPoints_Quantity(0).setStatus('A').setType(1).build());

           System.out.println("Select test");
           List<Line> list = LinePersistenceService.getInstance().get();
           for (int i =0; i < list.size(); i++){
               System.out.println(i + ". " + list.get(i));
           }


       } catch (PersistenceException e) {
           System.err.println(e.getMessage());
       }
   }
}