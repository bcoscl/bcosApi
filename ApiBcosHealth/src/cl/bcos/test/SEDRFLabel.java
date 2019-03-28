package cl.bcos.test;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class SEDRFLabel {

    private static final Logger Log = Logger.getLogger(SEDRFLabel.class);

    public static final int ID = 1;
    public static final int TEXTO = 2;
    public static final int DESCRIPCION = 3;

    

   

    public static void insertLabel(Connection con,String planName,String userMax) {

        StringBuilder qry = new StringBuilder();

        qry.append(" insert into health_plan  ");
        qry.append(" (plan_n_id,plan_c_name,plan_n_max)  ");
        
        qry.append(" values (nextval('health_seq_plan'), '");qry.append(planName);
        qry.append("',");qry.append(userMax);
        qry.append(") ");
        
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(qry.toString());

            int res = ps.executeUpdate();
        } catch (Exception e) {
            Log.error(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

   
    
}