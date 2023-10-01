/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.reservas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro Lazaro
 */
@WebServlet(name = "Reservas", urlPatterns = { "/Reservas" })
public class Reservas extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<p>Tienes que pasar por el formulario primero</p>");

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Recupera los parámetros del formulario de la solicitud
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String telefonoStr = request.getParameter("telefono");
            String email = request.getParameter("email");
            String diaStr = request.getParameter("dia");
            String mesStr = request.getParameter("mes");

            // Variables para la comprobación de errores
            String[] errores = { "nombre", "apellidos", "telefono", "fecha", "email" };
            StringBuilder tipoError = new StringBuilder();

            String regexTelefono = "^[6-9]\\d{8}$";
            String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";

            if (request.getParameter("btnEnviar") != null) {

                // Comprueba si el campo 'nombre' está vacío
                if (nombre.length() == 0) {
                    tipoError.append(errores[0]);
                }

                // Comprueba si el campo 'apellidos' está vacío
                if (apellidos.length() == 0) {
                    tipoError.append(errores[1]);
                }

                // Comprueba si el campo 'telefono' coincide con el patrón regex para números de teléfono en España
                try {
                    Pattern patternTelefono = Pattern.compile(regexTelefono);
                    Matcher matcherTelefono = patternTelefono.matcher(telefonoStr);
                    if (!matcherTelefono.matches()) {
                        tipoError.append(errores[2]);
                    }
                } catch (PatternSyntaxException e) {
                    tipoError.append(errores[2]);
                }

                // Comprueba si el campo 'email' coincide con el patrón regex para direcciones de correo electrónico
                try {
                    Pattern patternEmail = Pattern.compile(regexEmail);
                    Matcher matcherEmail = patternEmail.matcher(email);
                    if (!matcherEmail.matches()) {
                        tipoError.append(errores[4]);
                    }
                } catch (PatternSyntaxException e) {
                    tipoError.append(errores[4]);
                }

                // Valida la fecha (día y mes) y comprueba si está en el pasado
                boolean errorDia = false;
                boolean errorMes = false;
                try {
                    int dia = Integer.parseInt(diaStr);
                    int mes = Integer.parseInt(mesStr);

                    if (dia == 0) {
                        errorDia = true;
                    }

                    if (mes == 0) {
                        errorMes = true;
                    }

                    Calendar fechaActual = Calendar.getInstance();
                    Calendar fechaEntrada = Calendar.getInstance();

                    fechaEntrada.set(Calendar.MONTH, mes);
                    fechaEntrada.set(Calendar.DAY_OF_MONTH, dia);

                    if (fechaEntrada.compareTo(fechaActual) < 0) {
                        tipoError.append(errores[3]);
                    }
                } catch (Exception e) {
                    tipoError.append(errores[3]);
                }

                // Llama a la función Formulario para generar la respuesta del formulario
                Formulario(out, request, tipoError, errorDia, errorMes);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // Función para generar el formulario HTML y mostrar la respuesta
    private void Formulario(PrintWriter out, HttpServletRequest request, StringBuilder tipoError, boolean errorDia,
            boolean errorMes) {

        // Obtiene los parámetros del formulario para construir la respuesta        
        String n_noches = request.getParameter("noches");
        String tipoHabitacion = request.getParameter("tipohab");
        String telefono = request.getParameter("telefono");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String comentarios = request.getParameter("comentarios");

        // Genera el formulario HTML y la respuesta
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Reservas</title>");
        out.println("        <meta charset='UTF-8' />");
        out.println("        <meta name='viewport' content='width=device-width, initial-scale=1.0' />");
        out.println("        <link rel='stylesheet' href='./CSS/styles.css' />");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <div class=\"main-container\">");
        out.println("            <!-- FORMULARIO -->");
        out.println("            <div class=\"formulario-contenedor\">");
        out.println("                <div class=\"titular\">");
        out.println("                    <h2>Reservas Hotel</h2>");
        out.println("                </div>");
        out.println("                <form method=\"post\" action='" + request.getContextPath()
                + "/Reservas'\">");
        out.println("                    <div class=\"informacion-personal\">");
        out.println("                        <h3>Datos Personales</h3>");
        out.println("                        <div class=\"nombre\">");
        out.println("                            <label for=\"nb\">Nombre*: </label>");
        out.println("                            <input type=\"text\" id=\"nb\" name=\"nombre\""
                + (tipoError.length() != 0 ? " " : " readonly ") + " value=\""
                + nombre + "\" />");
        out.println("                            <img class=\"imagen\" src=\"./IMG/"
                + (tipoError.indexOf("nombre") != -1 ? "cancelar.png" : "cheque.png") + "\" width=\"30px\">");
        out.println("                        </div>");
        out.println("                        <br>");
        out.println("                        <div class=\"apellidos\">");
        out.println("                            <label for=\"ap\">Apellidos: </label>");
        out.println("                            <input type=\"text\" id=\"ap\" name=\"apellidos\""
                + (tipoError.length() != 0 ? " " : " readonly ") + " value=\""
                + apellidos + "\" />");
        out.println("                            <img class=\"imagen\" src=\"./IMG/"
                + (tipoError.indexOf("apellidos") != -1 ? "cancelar.png" : "cheque.png") + "\" width=\"30px\">");
        out.println("                        </div>");
        out.println("                        <br>");
        out.println("                        <div class=\"telefono\">");
        out.println("                            <label for=\"telefono\">Teléfono de Contacto*: </label>");
        out.println("                            <input id=\"telefono\" type=\"number\" name=\"telefono\""
                + (tipoError.length() != 0 ? " " : " readonly ") + " value=\""
                + telefono + "\" />");
        out.println("                            <img class=\"imagen\" src=\"./IMG/"
                + (tipoError.indexOf("telefono") != -1 ? "cancelar.png" : "cheque.png") + "\" width=\"30px\">");
        out.println("                        </div>");
        out.println("                        <br>");
        out.println("                        <div class=\"email\">");
        out.println("                            <label for=\"email\">Email*: </label>");
        out.println("                            <input type=\"text\" id=\"email\" name=\"email\""
                + (tipoError.length() != 0 ? "" : " readonly") + " value=\"" + email + "\" />");
        out.println("                            <img class=\"imagen\" src=\"./IMG/"
                + (tipoError.indexOf("email") != -1 ? "cancelar.png" : "cheque.png") + "\" width=\"30px\">");
        out.println("                        </div>");
        out.println("                    </div>");
        out.println("                    <div class=\"datos-reserva\">");
        out.println("                        <h3 class=\"titular\">Información de la Reserva</h3>");
        out.println("                        <br>");
        out.println("                        <label for=\"noches\">Número de Noches*: </label>");
        out.println(
                "                        <input type=\"number\" id=\"noches\" name=\"noches\" min=\"1\""
                + (tipoError.length() != 0 ? "" : " readonly") + " value=\"" + n_noches + "\" />");
        out.println("                        <br>");
        out.println("                        <br>");
        out.println("                        <div class=\"fecha\">");
        out.println("                            <label for=\"dia\">Fecha de Entrada*: </label>");

        String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
        out.println(
                "                            <select name=\"dia\" id=\"dia\"" 
                + (tipoError.length() != 0 ? " " : " disabled ") + "/>");
        out.println("                                <option value=\"0\">Elige un dia</option>"); // Opción
                                                                                                  // predeterminada

        for (int i = 1; i <= 31; i++) {
            out.print("                <option value=\"" + i + "\"");
            if (dia != null && Integer.parseInt(dia) == i) {
                out.print(" selected");
            }
            out.println(" >" + i + "</option>");
        }
        out.println("                            </select>");

        out.println("                            <select name=\"mes\" id=\"mes\"" 
                + (tipoError.length() != 0 ? " " : " disabled ") + "/>");
        for (int i = 0; i <= 12; i++) {
            out.print("                <option value=\"" + i + "\"");
            if (mes != null && Integer.parseInt(mes) == i) {
                out.print(" selected");
            }
            out.println(" >" + (i == 0 ? "Elige un mes" : meses[i - 1]) + "</option>");
        }
        out.println("                            </select>");

        out.println("                            <img class=\"imagen\" src=\"./IMG/"
                + (tipoError.indexOf("fecha") != -1 ? "cancelar.png" : "cheque.png") + "\" width=\"30px\">");
        out.println("                      <br><br>");
        out.println("                        </div>");

        out.println("                        <div class=\"tipohab\">");
        out.println("                            <label for=\"radest1\">Tipo de Habitación*: </label>");
        out.println(
                "                            <input id=\"radest1\" type=\"radio\" name=\"tipohab\" value=\"simple\" "
                        + (tipoHabitacion.equals("simple") ? "checked" : "")                 
                        + (tipoError.length() != 0 ? " " : " disabled ")
                        + "/>");
        out.println("                            <label for=\"radest1\">Habitación Simple</label>");
        out.println("                            <input id=\"radest2\" type=\"radio\" name=\"tipohab\" value=\"doble\" "
                + (tipoHabitacion.equals("doble") ? "checked" : "  ") 
                + (tipoError.length() != 0 ? " " : " disabled ")
                + "/>");
        out.println("                            <label for=\"radest2\">Habitación Doble</label>");
        out.println(
                "                            <input id=\"radest3\" type=\"radio\" name=\"tipohab\" value=\"matrimonio\" "
                        + (tipoHabitacion.equals("matrimonio") ? "checked" : " ") 
                        + (tipoError.length() != 0 ? "" : " disabled ") + "/>");
        out.println("                            <label for=\"radest3\">Matrimonio</label>");
        out.println("                        <br><br><br>");
        out.println("                        <div class=\"chb-box\">");
        out.println("                            <p>Servicios Extras: </p>");
        out.println("                            <div class=\"chb\">");
        String[] extras = request.getParameterValues("extra");
        String[] opcionesExtras = { "Desayuno", "Comida", "Cena", "Cama_Supletoria" };
        
        if (extras != null && extras.length > 0) {

            for (String opcionextra : opcionesExtras) {
                out.print("                <input type=\"checkbox\" id=\"" + opcionextra.toLowerCase()
                        + "\" name=\"extra\" value=\"" + opcionextra + "\" "
                        + (Arrays.asList(extras).contains(opcionextra) ? "checked " : "")
                        + (tipoError.length() != 0 ? " " : " disabled ")
                        + "/>");
        
                out.println("                <label for=\"" + opcionextra.toLowerCase() + "\">" + opcionextra
                        + "</label><br><br>\n");
            }
        } else {
        
            for (String opcionextra : opcionesExtras) {
                out.print("                <input type=\"checkbox\" id=\"" + opcionextra.toLowerCase()
                        + "\" name=\"extra\" value=\"" + opcionextra + "\" />");
        
                out.println("                <label for=\"" + opcionextra.toLowerCase() + "\">" + opcionextra
                        + "</label><br><br>\n");
            }
        }
        out.println("                        </div>");
        out.println("                        </div>");
        out.println("                        </div>");
        out.println("                        <br>");
        out.println("                        <p>Comentarios: </p>");
        out.println(
                "                        <input id=\"comentarios\""
                        + (tipoError.length() != 0 ? " " : " disabled ")  + "name=\"comentarios\" type=\"text\" size=\"50px\" value=\""
                        + comentarios + "\" />");
        out.println("                    </div>");
        out.println("                    <div class=\"aviso\">");
        out.println("                        <p>Los campos que contienen * son obligatorios</p>");
        out.println("                    </div>");
        out.println("                    <div class=\"btns\">");
        if (tipoError.length() > 0) {
            out.println(
                    "                        <input type=\"submit\" name=\"btnEnviar\" class=\"boton\" value=\"Enviar\" />");
        } else {
            out.println(
                    "                        <input type=\"reset\" name=\"btnLimpiar\" class=\"boton\" value=\"Limpiar\" />");
            out.println(
                    "                        <input type=\"button\"  onClick=\"location.href='"
                            + request.getContextPath()
                            + "/index.html';\" name=\"brnVolver\" class=\"boton\" value=\"Volver\" />");
        }
        out.println("                    </div>");
        out.println("                </form>");
        out.println("            </div>");
        out.println("            <!-- FIN FORMULARIO -->");
        out.println("        </div>");
        out.println("    </body>");
        out.println("</html>");

    }
}