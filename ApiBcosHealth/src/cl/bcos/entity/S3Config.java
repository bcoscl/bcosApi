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
public class S3Config {
    
    String ACCESS_KEY_ID;
    String ACCESS_SEC_KEY;
    String FOLDER_NAME_PROFILE;
    String FOLDER_NAME_EXAMENES;
    String POLICY_RULES;
    String BUCKETNAME;

    public void setBUCKETNAME(String BUCKETNAME) {
        this.BUCKETNAME = BUCKETNAME;
    }

    public String getBUCKETNAME() {
        return BUCKETNAME;
    }

    public void setACCESS_KEY_ID(String ACCESS_KEY_ID) {
        this.ACCESS_KEY_ID = ACCESS_KEY_ID;
    }

    public void setACCESS_SEC_KEY(String ACCESS_SEC_KEY) {
        this.ACCESS_SEC_KEY = ACCESS_SEC_KEY;
    }

    public void setFOLDER_NAME_PROFILE(String FOLDER_NAME_PROFILE) {
        this.FOLDER_NAME_PROFILE = FOLDER_NAME_PROFILE;
    }

    public void setFOLDER_NAME_EXAMENES(String FOLDER_NAME_EXAMENES) {
        this.FOLDER_NAME_EXAMENES = FOLDER_NAME_EXAMENES;
    }

    public void setPOLICY_RULES(String POLICY_RULES) {
        this.POLICY_RULES = POLICY_RULES;
    }

    public String getACCESS_KEY_ID() {
        return ACCESS_KEY_ID;
    }

    public String getACCESS_SEC_KEY() {
        return ACCESS_SEC_KEY;
    }

    public String getFOLDER_NAME_PROFILE() {
        return FOLDER_NAME_PROFILE;
    }

    public String getFOLDER_NAME_EXAMENES() {
        return FOLDER_NAME_EXAMENES;
    }

    public String getPOLICY_RULES() {
        return POLICY_RULES;
    }
    
    
    
}
