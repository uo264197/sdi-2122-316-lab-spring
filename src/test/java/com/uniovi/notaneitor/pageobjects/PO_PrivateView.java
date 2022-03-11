package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void fillLoginForm(WebDriver driver, String username, String password)
    {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, username, password);
    }

    static public void logout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    static public List<WebElement> accessMarkOption(WebDriver driver, String option) {
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        elements.get(0).click();
        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/"+option+"')]");
        //Pinchamos en agregar Nota.
        elements.get(0).click();
        return elements;
    }

    static public List<WebElement> goToPage(WebDriver driver, int numeroDePagina) {
        List<WebElement> elements = new ArrayList<WebElement>();

        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-item active')]/following-sibling::*/a[contains(@class, 'page-link')]");
        int paginaActual = Integer.parseInt(elements.get(0).getText());

        while(paginaActual!=numeroDePagina) {
            if (paginaActual<numeroDePagina) {
                elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
                elements.get(4).click();
            }
            else if (paginaActual>numeroDePagina) {
                elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
                elements.get(2).click();
            }

            elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-item active')]/following-sibling::*/a[contains(@class, 'page-link')]");
            paginaActual = Integer.parseInt(elements.get(0).getText());
        }

        return elements;
    }

    static public List<WebElement> goToPage(WebDriver driver, String ultimaOPrimera) {
        List<WebElement> elements = new ArrayList<WebElement>();
        switch (ultimaOPrimera) {
            case "primera":
                //Esperamos a que se muestren los enlaces de paginación la lista de notas
                elements = PO_View.checkElementBy(driver, "free", "//a[contains(text(), 'Primera')]");
                //Nos vamos a la primera página
                elements.get(0).click();
                break;
            case "ultima":
                //Esperamos a que se muestren los enlaces de paginación la lista de notas
                elements = PO_View.checkElementBy(driver, "free", "//a[contains(text(), 'Última')]");
                //Nos vamos a la última página
                elements.get(0).click();
                break;
        }

        return elements;
    }

}