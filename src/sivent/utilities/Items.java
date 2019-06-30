/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sivent.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author espinoza
 */
public final class Items {
    private Items(){}
    
    
    public static ObservableList<String> usuario(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Apellido"
        );

        return data;
    }

    public static ObservableList<String> cliente(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Email",
            "Domicilio",
            "Teléfono"
        );

        return data;
    }

    public static ObservableList<String> proveedor(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Email",
            "Domicilio",
            "Teléfono"
        );

        return data;
    }

    public static ObservableList<String> empleado(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Email",
            "Domicilio",
            "Teléfono"
        );

        return data;
    }

    public static ObservableList<String> departamento(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Nombre grupo"
        );

        return data;
    }

    public static ObservableList<String> bodega(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "Almacenaje"
        );

        return data;
    }

    public static ObservableList<String> compra(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "ID",
            "ProveedorID",
            "Fecha compra",
            "Total"
        );

        return data;
    }

    public static ObservableList<String> venta(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "ID",
            "ClienteID",
            "Fecha venta",
            "Total"
        );

        return data;
    }

    public static ObservableList<String> producto(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID",
            "CategoríaID",
            "Precio venta",
            "Stock"
        );

        return data;
    }

    public static ObservableList<String> categoria(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Nombre/ID"
        );

        return data;
    }

    public static ObservableList<String> oferta(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "ID/Tipo oferta",
            "Descuento(%)",
            "Fecha inicio",
            "Fecha final"
        );

        return data;
    }

    public static ObservableList<String> ciudad(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Boaco", "Carazo", "Chinandega", "Chontales", "Estelí", "Granada", "Jinotega", "León", 
            "Madriz", "Managua", "Masaya", "Matagalpa", "Nueva Segovia", "RAAN", "RAAS", "Río San Juan", "Rivas"
        );

        return data;
    }

    public static ObservableList<String> tipoPagoVenta(){
        ObservableList<String> data = FXCollections.observableArrayList(
            "Efectivo", "Cheque", "Trajeta"
        );

        return data;
    }
}
