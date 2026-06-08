/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import utility.DataBase;

public class LoginController {

    public static boolean loginHRD(String username, String password) {
        DataBase.sambung();
        return DataBase.autentikasiHRD(username, password);
    }
}
