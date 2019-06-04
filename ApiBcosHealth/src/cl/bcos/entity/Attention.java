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
public class Attention {
    
    private String at_n_id;
    private String at_c_numuser_paciente;
    private String at_c_pacientename;
    private String at_c_mediconame;
    private String at_c_numuser_medico;
    private String at_c_obs;
    private String at_d_fechamod;
    private String at_d_fechacita;

    public void setAt_d_fechacita(String at_d_fechacita) {
        this.at_d_fechacita = at_d_fechacita;
    }

    public String getAt_d_fechacita() {
        return at_d_fechacita;
    }

    public String getAt_n_id() {
        return at_n_id;
    }

    public String getAt_c_numuser_paciente() {
        return at_c_numuser_paciente;
    }

    public String getAt_c_pacientename() {
        return at_c_pacientename;
    }

    public String getAt_c_mediconame() {
        return at_c_mediconame;
    }

    public String getAt_c_numuser_medico() {
        return at_c_numuser_medico;
    }

    public void setAt_n_id(String at_n_id) {
        this.at_n_id = at_n_id;
    }

    public void setAt_c_numuser_paciente(String at_c_numuser_paciente) {
        this.at_c_numuser_paciente = at_c_numuser_paciente;
    }

    public void setAt_c_pacientename(String at_c_pacientename) {
        this.at_c_pacientename = at_c_pacientename;
    }

    public void setAt_c_mediconame(String at_c_mediconame) {
        this.at_c_mediconame = at_c_mediconame;
    }

    public void setAt_c_numuser_medico(String at_c_numuser_medico) {
        this.at_c_numuser_medico = at_c_numuser_medico;
    }

    public String getAt_c_obs() {
        return at_c_obs;
    }

    public String getAt_d_fechamod() {
        return at_d_fechamod;
    }

    public void setAt_c_obs(String at_c_obs) {
        this.at_c_obs = at_c_obs;
    }

    public void setAt_d_fechamod(String at_d_fechamod) {
        this.at_d_fechamod = at_d_fechamod;
    }
    
    
    
}
