

package com.example.dao;

import com.example.model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EmployeeDAOJDBCImpl implements EmployeeDAO
{
    private Connection con= null;

      public EmployeeDAOJDBCImpl()
      {
            String url = "jdbc:derby://localhost:1527/EmployeeDB";
        String username = "public";
        String password = "tiger";
        try
        {
            con= DriverManager.getConnection(url, username, password);

        }
        catch(SQLException e)
        {
            System.out.println("Error Obtaining Connection With The Database: "+e);
            System.exit(-1);
        }


      }
    @Override
    public void add(Employee emp) throws DAOException
    {

            try {
                Statement stmt = con.createStatement();
                String query="INSERT into APP.EMP values("+emp.getId()+",'"+emp.getFirstName()+"' ,"+"'"+new java.sql.Date(emp.getBirthDate().getTime())+"',"+emp.getSalary()+")";
                if(stmt.executeUpdate(query)!=1)
                {
                    throw new DAOException("Error adding Employee!!!");
                }

            }



        catch(SQLException e)
        {
            throw new DAOException("Error adding Employee!!!",e);
        }

    }

    @Override
    public void update(Employee emp) throws DAOException 
    {
        try
        {
         Statement stmt=con.createStatement();
         String query="Update APP.EMP SET NAME="+"'emp.getFirstName()',"+"LASTNAME='"+"emp.getLastName()',"+"BDATE='"+new java.sql.Date(emp.getBirthDate().getTime())+"',"+"SAL="+emp.getSalary()+"WHERE ID="+emp.getId();
         if(stmt.executeUpdate(query)!=1)
         {
             throw new DAOException("UPDATE COULD NOT BE DONE");
         }
         System.out.println("DATA UPDATED SUCCESSFULLY");
        }
         catch(SQLException e)
         {
             throw new DAOException("Error adding Employee!!!",e);
         }

    }

    @Override
    public void delete(int id) throws DAOException
    {
        try
        {
            
              Statement stmt=con.createStatement();
           String query="delete from APP.EMP where EMPID="+id;
           if(stmt.executeUpdate(query)!=1)
           {
               throw new DAOException("Error Deleting Employee");
           }
           else
               System.out.println("Employee ID no: "+id+" Deleted successfully");
        }
        catch(SQLException e)
        {
            System.out.println("Error deleting employee, Exception"+e);
        }

    }

    @Override
    public Employee findById(int id) throws DAOException
    {
       try
       {
           Statement stmt=con.createStatement();
           String query="Select * from APP.EMP where EMPID="+id;
           ResultSet rs=stmt.executeQuery(query);
           if(!rs.next())
           {return null;}


       return(new Employee(rs.getInt("EMPID"),rs.getString("LASTNAME"),rs.getString("NAME"),rs.getDate("BDATE"),rs.getInt("SAL")));

        }

        catch(SQLException e)
        {
              throw new DAOException("Error adding Employee!!!",e);
        }



    }

    @Override
    public Employee[] getAllEmployees() throws DAOException
    {
        try
        {
             Statement stmt=con.createStatement();
           String query="Select * from APP.EMP ";
           ResultSet rs=stmt.executeQuery(query);
           ArrayList<Employee>emps=new ArrayList<>();
           
           while(rs.next())
           {
               emps.add(new Employee(rs.getInt("EMPID"),rs.getString("NAME"),rs.getString("LASTNAME"),rs.getDate("BDATE"),rs.getInt("SAL")));
               
           }
           return emps.toArray(new Employee[0]);
          
        }    
        
        catch(SQLException e)
        {
            throw new DAOException("Error Getting Employees in DAO"+e);
        }
        

    }


}
