import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;
import java.util.Date;

public class offermanagement 
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the ids to be updated");
        String s[]=sc.nextLine().split(" ");
        int a[]=new int[s.length];
        int i=0;
        for(i=0;i<s.length;i++)
        {
            a[i]=Integer.parseInt(s[i]);
        }
        System.out.println("Enter date as dd - mm -yyyy");
        String sdate=sc.next();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= null;
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","raymedi_hq","raymedi_hq");
            SimpleDateFormat format=new SimpleDateFormat("dd-mm-yyyy");
            Date d=format.parse(sdate);
            java.sql.Date dt = new java.sql.Date(d.getTime());
            PreparedStatement pstmt;
            System.out.println(sdate);

            for(i=0;i<s.length;i++) {
                pstmt=con.prepareStatement("update offer set todate = ? where offerid in ?");
                pstmt.setDate(1, dt);
                pstmt.setInt(2, a[i]);
                pstmt.executeUpdate();
            }
            
            System.out.println();
            
            PreparedStatement ps =con.prepareStatement( "select * from offer");
            try (ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next())
                {
                    System.out.println(rs.getInt("offerid")+" "+rs.getString("offername")+" "
                            +rs.getFloat("price")+" "+rs.getDate("fromdate")+" "+rs.getDate("todate"));
                }
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
