package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ahmad-AL-Qerem
 */
public class  Srevers {
    
    int  CurrentServer=0;
    String getUserServer="http://127.0.0.1:80/HW2/getUser.php";
    String deleteServer="http://127.0.0.1:80/HW2/delete.php";
    String editServer="http://127.0.0.1:80/HW2/edit.php";
    String getImgServer="http://127.0.0.1:80/HW2/getImg.php";
    String getUsersServer="http://127.0.0.1:80/HW2/getUsers.php";
    String logInServer="http://127.0.0.1:80/HW2/logIn.php";
    String registerServer="http://127.0.0.1:80/HW2/register.php";
    String saveImgServer="http://127.0.0.1:80/HW2/saveImg.php";
    String searchIdServer="http://127.0.0.1:80/HW2/searchId.php";
    String searchNameServer="http://127.0.0.1:80/HW2/searchName.php";
    
    String getUserServerT = "http://127.0.0.1:8080/WebApplication1/getUser.java";
    String deleteServerT = "http://127.0.0.1:8080/WebApplication1/delete.java";
    String editServerT = "http://127.0.0.1:8080/WebApplication1/edit.java";
    String getImgServerT = "http://127.0.0.1:8080/WebApplication1/getImg.java";
    String getUsersServerT = "http://127.0.0.1:8080/WebApplication1/getUsers.java";
    String logInServerT = "http://127.0.0.1:8080/WebApplication1/logIn.java";
    String registerServerT = "http://127.0.0.1:8080/WebApplication1/register.java";
    String saveImgServerT = "http://127.0.0.1:8080/WebApplication1/saveImg.java";
    String searchIdServerT = "http://127.0.0.1:8080/WebApplication1/searchId.java";
    String searchNameServerT = "http://127.0.0.1:8080/WebApplication1/searchName.java";

    
    public String getGetUserServer() {
        if (CurrentServer==0){
            return getUserServer;
        }else {
            return getUserServerT;
        }
        
    }

    public String getDeleteServer() {
        if (CurrentServer == 0) {
            return deleteServer;
        } else {
            return deleteServerT;
        }
        
    }

    public String getEditServer() {
        if (CurrentServer == 0) {
            return editServer;
        } else {
            return editServerT;
        }
        
    }

    public String getGetImgServer() {
        if (CurrentServer == 0) {
            return getImgServer;
        } else {
            return getImgServerT;
        }
    }

    public String getGetUsersServer() {
        if (CurrentServer == 0) {
            return getUsersServer;
        } else {
            return getUsersServerT;
        }

    }

    public String getLogInServer() {
        if (CurrentServer == 0) {
            return logInServer;
        } else {
            return logInServerT;
        }

    }

    public String getRegisterServer() {
        if (CurrentServer == 0) {
            return registerServer;
        } else {
            return registerServerT;
        }
    }

    public String getSaveImgServer() {
        if (CurrentServer == 0) {
            return saveImgServer;
        } else {
            return saveImgServerT;
        }
    }

    public String getSearchIdServer() {
        if (CurrentServer == 0) {
            return searchIdServer;
        } else {
            return searchIdServerT;
        }
    }

    public String getSearchNameServer() {
        if (CurrentServer == 0) {
            return searchNameServer;
        } else {
            return searchNameServer;
        }
    } 
}