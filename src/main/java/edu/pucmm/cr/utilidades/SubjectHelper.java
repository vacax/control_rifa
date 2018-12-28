package edu.pucmm.cr.utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vacax on 18/11/16.
 */
public class SubjectHelper implements ISubject {

    private List<IObserver> listaObservadores=new ArrayList();

    public void addObserver(IObserver observador) {
        listaObservadores.add(observador);
    }

    public void removeObserver(IObserver observador) {
        listaObservadores.remove(observador);
    }

    public void notify(Class clase, Object argumento, Enum anEnum){
        for(IObserver observador : listaObservadores){
            observador.update(clase, argumento, anEnum);
        }
    }
}
