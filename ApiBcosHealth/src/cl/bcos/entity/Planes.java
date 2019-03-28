/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.entity;

/**
 *
 * @author aacantero
 */
public class Planes {
    
    public String nombrePlan;
    public String numeroMax;
    public String fechaCreacion;
    public String nombreCreador;
   

    public String getNombrePlan() {
        return nombrePlan;
    }

    public String getNumeroMax() {
        return numeroMax;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }


    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public void setNumeroMax(String numeroMax) {
        this.numeroMax = numeroMax;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

   

    
}
