package edu.pucmm.cr.utilidades;

import java.io.Serializable;

/**
 * Created by vacax on 18/11/16.
 */
public interface IObserver extends Serializable {

    public void update(Class clase, Object argumento, Enum anEnum);
}