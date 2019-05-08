/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.entity;

import java.util.List;

/**
 *
 * @author aacantero
 */
public class ExportarFichas {
    
    Paciente Paciente;
    List<Consultas> Consultas;
  

    public void setPaciente(Paciente paciente) {
        this.Paciente = paciente;
    }

    public void setConsultas(List<Consultas> Consultas) {
        this.Consultas = Consultas;
    }

 

    public Paciente getPaciente() {
        return Paciente;
    }

    public List<Consultas> getConsultas() {
        return Consultas;
    }

   
    
}
