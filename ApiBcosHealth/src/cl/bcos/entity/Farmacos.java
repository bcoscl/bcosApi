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
public class Farmacos {

    private String farmaco_n_id;
    private String farmaco_c_name;
    private String farmaco_c_obs;
    private String farmaco_numuser_paciente;
    private String farmaco_n_ultmod_numuser;
    private String farmaco_d_ultmod_date;
    private String farmaco_c_ultmod_username;

    public String getFarmaco_n_id() {
        return farmaco_n_id;
    }

    public String getFarmaco_c_name() {
        return farmaco_c_name;
    }

    public String getFarmaco_c_obs() {
        return farmaco_c_obs;
    }

    public String getFarmaco_numuser_paciente() {
        return farmaco_numuser_paciente;
    }

    public String getFarmaco_n_ultmod_numuser() {
        return farmaco_n_ultmod_numuser;
    }

    public String getFarmaco_d_ultmod_date() {
        return farmaco_d_ultmod_date;
    }

    public String getFarmaco_c_ultmod_username() {
        return farmaco_c_ultmod_username;
    }

    public void setFarmaco_n_id(String farmaco_n_id) {
        this.farmaco_n_id = farmaco_n_id;
    }

    public void setFarmaco_c_name(String farmaco_c_name) {
        this.farmaco_c_name = farmaco_c_name;
    }

    public void setFarmaco_c_obs(String farmaco_c_obs) {
        this.farmaco_c_obs = farmaco_c_obs;
    }

    public void setFarmaco_numuser_paciente(String farmaco_numuser_paciente) {
        this.farmaco_numuser_paciente = farmaco_numuser_paciente;
    }

    public void setFarmaco_n_ultmod_numuser(String farmaco_n_ultmod_numuser) {
        this.farmaco_n_ultmod_numuser = farmaco_n_ultmod_numuser;
    }

    public void setFarmaco_d_ultmod_date(String farmaco_d_ultmod_date) {
        this.farmaco_d_ultmod_date = farmaco_d_ultmod_date;
    }

    public void setFarmaco_c_ultmod_username(String farmaco_c_ultmod_username) {
        this.farmaco_c_ultmod_username = farmaco_c_ultmod_username;
    }

    
    
}
