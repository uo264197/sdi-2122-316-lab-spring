package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotaneitorApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
//    static String Geckodriver = "D:\\Trabajos\\5 Año\\Semestre 2\\SDI\\Laboratorio\\Sesion 5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\uo264197\\Desktop\\SDI\\Practicas\\Sesion 5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }


    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }
    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
                PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
    // Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH() );
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06B. Prueba del formulario de registro. Nombre corto.
    // Propiedad: Error.signup.dni.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH() );
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(9)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(10)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de Profesor
        PO_HomeView.enterAddMark(driver);
        String checkText2 = "Agregar nota";
        List<WebElement> result2 = PO_View.checkElementBy(driver, "text", checkText2);
        Assertions.assertEquals(checkText2, result2.get(0).getText());
    }

    @Test
    @Order(11)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Admin
        PO_HomeView.enterAddUser(driver);
        String checkText2 = "Role:";
        List<WebElement> result2 = PO_View.checkElementBy(driver, "text", checkText2);
        Assertions.assertEquals(checkText2, result2.get(0).getText());
    }

    @Test
    @Order(12)
    public void PR010() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
    }

    @Test
    @Order(13)
    public void PR011() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Desconexion
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        String checkText2 = "Identifícate";
        List<WebElement> result2 = PO_View.checkElementBy(driver, "text", checkText2);
        Assertions.assertEquals(checkText2, result2.get(0).getText());
    }

    //PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de estudiante
    @Test
    @Order(14)
    public void PR12() {
        //Vamos al formulario de logueo.
        PO_PrivateView.fillLoginForm(driver, "99999990A", "123456");
        //COmprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //Contamos el número de filas de notas
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(4, markList.size());
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        PO_PrivateView.logout(driver);
    }

    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test
    @Order(15)
    public void PR13() {
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_PrivateView.fillLoginForm(driver, "99999990A", "123456");
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);

        //SeleniumUtils.esperarSegundos(driver, 1);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Esperamos por la ventana de detalle
        checkText = "Detalles de la nota";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText,result.get(0).getText());

        //Ahora nos desconectamos comprobamas que aparece el menu de registrarse
        PO_PrivateView.logout(driver);
    }

    //P14. Loguearse como profesor y Agregar Nota A2.
    //P14. Esta prueba podría encapsularse mejor ...
    @Test
    @Order(16)
    public void PR14() {
        //Vamos al formulario de login.
        PO_PrivateView.fillLoginForm(driver, "99999993D", "123456");
        //Cmmprobamos que entramos en la pagina privada del Profesor
        PO_View.checkElementBy(driver, "text", "99999993D");

        List<WebElement> elements = PO_PrivateView.accessMarkOption(driver, "add");

        //Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
        String checkText = "Nota Nueva 1";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");
        //Nos vamos a la última página
        PO_PrivateView.goToPage(driver, "ultima");
        //Comprobamos que aparece la nota en la página
        elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());

        //Ahora nos desconectamos y comprobamos que aparece el menú de registrarse
        PO_PrivateView.logout(driver);
    }

    @Test
    @Order(17)
    public void PR15() {
        //Vamos al formulario de login.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");

        List<WebElement> elements = PO_PrivateView.accessMarkOption(driver, "list");

        //Nos vamos a la última página
        PO_PrivateView.goToPage(driver, "ultima");

        //Esperamos a que aparezca la Nueva nota en la última página
        //Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
        elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
        //Volvemos a la última página
        PO_PrivateView.goToPage(driver, "ultima");
        //Y esperamos a que NO aparezca la última "Nueva Nota 1"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota Nueva 1",PO_View.getTimeout());

        //Ahora nos desconectamos comprobamos que aparece el menú de registrarse
        PO_PrivateView.logout(driver);
    }

}
