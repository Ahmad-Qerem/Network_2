/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Ahmad-AL-Qerem
 */
public class User {
    String Name ;
    int ID;
    String Address;
    String Email;
    char Password[];
    boolean Admin;
    String PichPath;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public char[] getPassword() {
        return Password;
    }

    public void setPassword(char Password[]) {
        this.Password = Password;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean Admin) {
        this.Admin = Admin;
    }

    public String getPichPath() {
        return PichPath;
    }

    public void setPichPath(String PichPath) {
        this.PichPath = PichPath;
    }
    
    
    
}
