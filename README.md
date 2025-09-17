# Inventario-Miscelanea
Proyecto de Conmutación

# Planteamiento

En las misceláneas se presenta un problema recurrente al momento de buscar productos almacenados, ya sea en la bodega o en un almacén. La falta de organización y orden genera complicaciones, sobre todo con los productos perecederos que requieren venderse en un tiempo determinado para evitar pérdidas económicas.

Actualmente, el negocio lleva el control de inventario de manera manual o mediante herramientas básicas como hojas de cálculo. Este enfoque presenta varias limitaciones que afectan la eficiencia operativa. El registro, seguimiento y análisis del inventario consumen demasiado tiempo del personal, lo que resta atención a tareas clave como la atención al cliente o la planificación de compras. Además, el control manual es propenso a errores como registros duplicados, omisiones o discrepancias en las cantidades de productos, lo que provoca faltantes o sobrantes de mercancía.

Otro problema importante es la falta de un sistema que alerte sobre niveles bajos de stock, lo que ocasiona quiebres de inventario, afectando la satisfacción del cliente y, en algunos casos, la pérdida de los mismos. También resulta difícil conocer el estado real del inventario en tiempo oportuno, lo que limita la toma de decisiones rápidas, como el reabastecimiento de productos o la identificación de tendencias de venta que ayuden al crecimiento del negocio.

A esto se suma la falta de control de proveedores. Sin un registro adecuado de los proveedores y de sus condiciones de abastecimiento, se dificulta la gestión de compras, los tiempos de entrega y la negociación de precios. Esto puede derivar en retrasos en el reabastecimiento, sobrecostos o dependencia de un solo proveedor.

```mermaid
---
title: UML Miscelanea
---

classDiagram
	
	class Almacen{
		-nombre:String
		-ubicacion:String
		+setNombre(nombre:String):void
		+getNombre():String
		+setUbicacion(Ubicacion:String):void
		+getUbicacion():String
	}
	class Lote {
		- productos: Producto[]
		+Lote(nombre: int,productos: Producto[], ubicacion: String)
		+ getProductos(): Producto[]
		+ setProductos(productos: Producto[]):void
	}
	
	class Producto{
		#nombre:String
		#Producto(nombre:String)
		+setNombre(nombre:String):void
		+getNombre():String
	}
	
	class Perecedero{
		-fechaCaducidad:date
		+Perecedero(nombre:String,fechaCaducidad:date)
		+seFecha(fecha:date):void
		+getFecha():date
	}
	
	class NoPerecedero{
		-fechaLlegada:date		
		+NoPerecedero(nombre:String,fechaLlegade:date)
		+seFecha(fecha:date):void
		+getFecha():date
	}
	
	class Usuario{
		<<abstract>>
		#nombre:String
		#telefono:String
		#correo:String
		+setNombre(nombre:String):void
		+getNombre():String
		+setTelefono(numero:String):void
		+getTelefono():String
		+setCorreo(correo:String):void
		+getCorreo():String
		+consultarStock();
	}
	
	class Vendedor{
		+registrarVenta(venta:Venta):void
		+consultarStock(idProducto:String):int
	}
	
	class Administrador{
    +consultarStock(idProducto:String):int
	}
	
	class Venta{
		-fecha:date:int
		-vendedor:Vendedor
		-producto:Productos[]
		+Venta(fecha:date,vendedor:Vendedor,producto:Productos[])
		+setFecha(fecha:date):void
		+getFecha():date
		
		+actualizarStock(cantidad:int):void
	}
	
	class Reporte_Compras{
		+getCompras():Compra
		+getCompras(fechaCompra:date):Compra
	}
	
	class Reporte_Venta{
		+getVentas(fecha_inicio:date,fecha_fin:date):Venta
		+getVentas():Venta
	}
	class Proovedor{
		- empresa: String 
		- nombre: String
		- telefono: String
		+ Proovedor(empresa: String, nombre: String,telefono: String)
		+setNombre(nombre:String):void
		+getNombre():String
		+getTelefono():String
		+setCorreo(correo:String):void
		+getEmpresa():String
		+setEmpresa(Empresa:String):void
	}
	class Compra{
		- producto:Producto
		-cantidad:int
		- fecha : date
		+ Compra(fecha:date,cantidad:int,producto:Producto)
		+ setCantidad(cantidad:int):void
		+ getCantidad():int
		+ setFecha(fecha : date): void
		+ getFecha(): date

		+actualizarStock(cantidad:int):void
		+ setProducto(producto:Producto): void
		+ getProducto():Producto
	}

Producto <|-- Perecedero
Producto <|-- NoPerecedero
Usuario <|-- Vendedor
Usuario <|-- Administrador
Vendedor -- Venta : Realiza
Almacen <|-- Lote

Venta  -- Reporte_Venta
Venta -- Producto: Incluye

Producto --Compra
Compra -- Proovedor
Compra -- Reporte_Compras

Proovedor -- Lote

```

┌──────────────────────┐
│       Usuario        │
├──────────────────────┤
│ - nombre: String     │
│ - telefono: String   │
│ - correo: String     │
├──────────────────────┤
│ + setNombre()        │
│ + getNombre()        │
│ + setTelefono()      │
│ + getTelefono()      │
│ + setCorreo()        │
│ + getCorreo()        │
│ + consultarStock()   │
└─────────▲────────────┘
│
│ hereda
│
┌─────────┴────────────┐    ┌──────────────────────┐
│      Vendedor        │    │    Administrador     │
├──────────────────────┤    ├──────────────────────┤
│ - idVendedor: int    │    │ - idAdmin: int       │
├──────────────────────┤    ├──────────────────────┤
│ + registrarVenta()   │    │ + consultarStock()   │
│ + consultarStock()   │    └──────────────────────┘
└──────────────────────┘

┌──────────────────────┐
│      Producto        │
├──────────────────────┤
│ - idProducto: int    │
│ - nombre: String     │
│ - precio: double     │
├──────────────────────┤
│ + setPrecio()        │
│ + getPrecio()        │
│ + getNombre()        │
└─────────▲────────────┘
│
│ hereda
│
┌─────────┴────────────┐    ┌──────────────────────┐
│     Perecedero       │    │   NoPerecedero       │
├──────────────────────┤    ├──────────────────────┤
│ - fechaCaducidad: Date│   │ - fechaCompra: Date  │
├──────────────────────┤    ├──────────────────────┤
│ + getFechaCaducidad()│   │ + getFechaCompra()   │
│ + setFechaCaducidad()│   │ + setFechaCompra()   │
└──────────────────────┘    └──────────────────────┘

┌──────────────────────┐
│      Proovedor       │  ← (Corregir a "Proveedor" en código)
├──────────────────────┤
│ - empresa: String    │
│ - nombre: String     │
│ - telefono: String   │
├──────────────────────┤
│ + getEmpresa()       │
│ + setEmpresa()       │
│ + getNombre()        │
│ + setNombre()        │
│ + getTelefono()      │
│ + setTelefono()      │
└─────────┬────────────┘
│
│ realiza
│
┌─────────▼────────────┐
│       Compra         │
├──────────────────────┤
│ - idCompra: int      │
│ - fecha: Date        │
│ - proveedor: Proovedor│
├──────────────────────┤
│ + setFecha()         │
│ + getFecha()         │
│ + setProveedor()     │
│ + getProveedor()     │
│ + agregarProducto()  │
└─────────┬────────────┘
│
│ incluye → usa Compra_Producto
│
┌─────────▼────────────┐    ┌──────────────────────┐
│   Compra_Producto    │    │      Inventario      │
├──────────────────────┤    ├──────────────────────┤
│ - compra: Compra     │    │ - producto: Producto │
│ - producto: Producto │    │ - lote: Lote         │
│ - cantidad: int      │    │ - cantidadActual: int│
├──────────────────────┤    ├──────────────────────┤
│ + setCantidad()      │    │ + aumentarStock()    │
│ + getCantidad()      │    │ + reducirStock()     │
└──────────────────────┘    │ + obtenerStock()     │
└─────────┬────────────┘
│
│
│
┌──────────────────────┐              ▼
│      Almacen         │    ┌──────────────────────┐
├──────────────────────┤    │        Lote          │◄────────────────┐
│ - nombre: String     │    ├──────────────────────┤                 │
│ - ubicacion: String  │    │ - nombre: String     │                 │
├──────────────────────┤    │ - capacidad: int     │                 │
│ + getNombre()        │    │ - almacen: Almacen   │                 │
│ + setNombre()        │    ├──────────────────────┤                 │
│ + getUbicacion()     │    │ + setAlmacen()       │                 │
│ + setUbicacion()     │    │ + getAlmacen()       │                 │
└─────────┬────────────┘    │ + agregarProducto()  │                 │
│                 └──────────────────────┘                 │
│ contiene                                                 │
│                                                          │ contiene
▼                                                          │
┌──────────────────────────────────────────────────────────┘
│
│
┌─────────▼────────────┐
│       Venta          │
├──────────────────────┤
│ - idVenta: int       │
│ - fecha: Date        │
│ - vendedor: Vendedor │
├──────────────────────┤
│ + setFecha()         │
│ + getFecha()         │
│ + setVendedor()      │
│ + getVendedor()      │
│ + agregarProducto()  │
└─────────┬────────────┘
│
│ incluye → usa Venta_Producto
│
┌─────────▼────────────┐
│   Venta_Producto     │
├──────────────────────┤
│ - venta: Venta       │
│ - producto: Producto │
│ - cantidad: int      │
├──────────────────────┤
│ + setCantidad()      │
│ + getCantidad()      │
└──────────────────────┘

┌──────────────────────┐    ┌──────────────────────┐
│   Reporte_Venta      │    │  Reporte_Compras     │
├──────────────────────┤    ├──────────────────────┤
│ + getVentas()        │    │ + getCompras()       │
│ + getVentas(fechas)  │    │ + getCompras(fecha)  │
└──────────────────────┘    └──────────────────────┘

![Imagen de WhatsApp 2025-09-10 a las 14.01.27_306a8449.jpg](attachment:6bd063cc-32c1-4f74-a53e-7cd0882e4f02:Imagen_de_WhatsApp_2025-09-10_a_las_14.01.27_306a8449.jpg)

# Código de la bdd

```sql
--Creación de la bdd
CREATE DATABASE MISCELANEA;

--tabla usuario, vendedor, administrador
CREATE TABLE Usuario(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    numero_telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    PRIMARY KEY (id)
);
    --Las tablas vendedor y administrador heredan de usuario
    CREATE TABLE Vendedor(
        id INT PRIMARY KEY AUTO_INCREMENT,
        FOREIGN KEY (numero_telefono) REFERENCES Usuario(numero_telefono)
    );
    CREATE TABLE Administrador(
        id INT PRIMARY KEY AUTO_INCREMENT,
        FOREIGN KEY (numero_telefono) REFERENCES Usuario(numero_telefono)
    );

-- tabla producto, perecedero, no perecedero
CREATE TABLE Producto(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (id)
);
    --Las tablas perecedero y no perecedero heredan de producto
    CREATE TABLE Perecedero(
        id INT PRIMARY KEY AUTO_INCREMENT,
        fecha_caducidad DATE,
        FOREIGN KEY (id) REFERENCES Producto(id)
    );

    CREATE TABLE NoPerecedero(
        id INT PRIMARY KEY AUTO_INCREMENT,
        fecha_compra DATE,
        FOREIGN KEY (id) REFERENCES Producto(id)
    );

-- Tabla almacen y lote
CREATE TABLE Almacen(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    ubicacion VARCHAR(100),
    PRIMARY KEY (id)
);
    --La tabla lote hereda de almacen
    CREATE TABLE Lote(
        id INT PRIMARY KEY AUTO_INCREMENT,
        PRIMARY KEY (id),
        FOREIGN KEY (id_almacen) REFERENCES Almacen(id)
    );
    
-- Tabla venta
-- Tiene la llave foranea vendedor porque solo un vendedor realiza esa venta
CREATE TABLE Venta(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE,
    id_vendedor INT,
    FOREIGN KEY (id_vendedor) REFERENCES Vendedor(id)
);
    --Relacion entre venta y producto
    CREATE TABLE VentaProducto(
        id_venta INT,
        id_producto INT,
        cantidad INT,
        PRIMARY KEY (id_venta),
        FOREIGN KEY (id_venta) REFERENCES Venta(id),
        FOREIGN KEY (id_producto) REFERENCES Producto(id)
    );

```

```mermaid
---
title: Entidad Relacion
---
erDiagram
	Almacen{
		int id
		String nombre
		String ubicacion
	}
	
	Producto{
		String nombre 
	}
	
	Perecedero{
		Date fechaCaducidad
	}
	NoPerecedero{
		Date fechaLlegada		
	}
	Producto o|--|| Perecedero: Deriva
	Producto o|--|| NoPerecedero: Deriva
	
	Usuario{
		String nombre 
		String telefono
		String correo
	}
	
	
	
```
