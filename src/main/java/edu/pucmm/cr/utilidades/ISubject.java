package edu.pucmm.cr.utilidades;

import java.io.Serializable;

/**
 * Created by vacax on 18/11/16.
 */
public interface ISubject extends Serializable {

    public void addObserver(IObserver observador);
    public void removeObserver(IObserver observador);
    public void notify(Class clase, Object argumento, Enum anEnum);
}