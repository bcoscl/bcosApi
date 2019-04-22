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
public class EnfermedadesCronicas {

    private String cronica_n_id;
    private String cronica_c_name;
    private String cronica_c_obs;
    private String cronica_c_numuser_paciente;
    private String cronica_n_ultmod_numuser;
    private String cronica_d_ultmod_date;
    private String cronica_c_ultmod_username;

    public void setCronica_n_id(String cronica_n_id) {
        this.cronica_n_id = cronica_n_id;
    }

    public void setCronica_c_name(String cronica_c_name) {
        this.cronica_c_name = cronica_c_name;
    }

    public void setCronica_c_obs(String cronica_c_obs) {
        this.cronica_c_obs = cronica_c_obs;
    }

    public void setCronica_c_numuser_paciente(String cronica_c_numuser_paciente) {
        this.cronica_c_numuser_paciente = cronica_c_numuser_paciente;
    }

    public void setCronica_n_ultmod_numuser(String cronica_n_ultmod_numuser) {
        this.cronica_n_ultmod_numuser = cronica_n_ultmod_numuser;
    }

    public void setCronica_d_ultmod_date(String cronica_d_ultmod_date) {
        this.cronica_d_ultmod_date = cronica_d_ultmod_date;
    }

    public void setCronica_c_ultmod_username(String cronica_c_ultmod_username) {
        this.cronica_c_ultmod_username = cronica_c_ultmod_username;
    }

    public String getCronica_n_id() {
        return cronica_n_id;
    }

    public String getCronica_c_name() {
        return cronica_c_name;
    }

    public String getCronica_c_obs() {
        return cronica_c_obs;
    }

    public String getCronica_c_numuser_paciente() {
        return cronica_c_numuser_paciente;
    }

    public String getCronica_n_ultmod_numuser() {
        return cronica_n_ultmod_numuser;
    }

    public String getCronica_d_ultmod_date() {
        return cronica_d_ultmod_date;
    }

    public String getCronica_c_ultmod_username() {
        return cronica_c_ultmod_username;
    }

}
