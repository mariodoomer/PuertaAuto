//Hola
//lbrerias para shildethernet
#include <SPI.h>
#include <Ethernet.h>

//MAC de la tarjeta Shild
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};

//direccion y puerto del servidor http
IPAddress ip(192, 168, 0, 105);
EthernetServer server(80);

//variables de control
String peticiones;
boolean estado_LED=0;

void setup() {
  //inicializacion de la targeta shild
  Ethernet.begin(mac, ip);
  server.begin();
  //Configuracion del puerto serial
  Serial.begin(9600);
  Serial.print("El Serrvidor es: ");
  //obtiene la IP de la shild
  Serial.println(Ethernet.localIP());
  //inicializacion del pin 2 en arduino
  pinMode(2, OUTPUT);
}

void loop() {
  //Espera de clientes
  EthernetClient client = server.available();
  //Si hay un cliente
  if (client) {
    //Notificacion del nuevo cliente, mediante serial
    Serial.println("Nuevo cliente");
    // Solicitud http termina con una linea en blanco
    boolean currentLineIsBlank = true;
    //mientras el cliente este conectado
    while (client.connected()){
    //si existe una nueva peticion del cliente
      if (client.available()) {
        //asignacion de lectura a una variable.
        char c = client.read();
        //se imprime la lectura en la consola serial
        Serial.write(c);
        peticiones += c;          // Los añadimos al String
        // Si se ha llegado al final de la linea
        // y se recibe un caracter de nueva linea y esta esta en blanco,
        // la solicitud a terminado, por lo que se envia una respuesta
        if (c == '\n' && currentLineIsBlank) {
          // se enevia un encapezado inicial
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // La coneccion sera cerrada despues de completar la respuesta
          //client.println("Refresh: 5");  // La pagina es refrescada cada 5 segundos
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          client.println("<head>");
          client.println("<title>Control de Puerta</title>");
          client.println("</head>");
          client.println("<body>");
          client.println("<h1>Puerta principal</h1>");
          client.println("<p>controles.</p>");
          client.println("<form method=\"get\" action=\"/\"");
          client.println("<table border=1 >");
          client.println("<tr><td>Abrir</td><td><input type=\"radio\" name=\"puerta\" value=\"1\" \\ onclick=\"submit();\"></td></tr>");
          client.println("<tr><td>Cerrar</td><td><input type=\"radio\" name=\"puerta\" value=\"0\" \\  onclick=\"submit();\"></td></tr>");
          if (peticiones.indexOf("puerta=1") > -1 && peticiones.indexOf("puerta=1") < 8){
            digitalWrite(2,LOW);
          }else{
            digitalWrite(2,HIGH);
          }
          client.println("</table>");
          client.println("</form>");
          client.println("</body>");
          client.println("</html>");
          peticiones="";
          break;
        }
        if (c == '\n') {
          // si se a comenzado un nueva line
          currentLineIsBlank = true;
        } else if (c != '\r') {
          currentLineIsBlank = false;
        }
      }
    }
    // esperar a que el navegador reciba los datos
    delay(10);
    // cerrar la conexion:
    client.stop();
    //se imprime un mensaje que el cliente a sido desconectado
    Serial.println("client disconnected");
  }
}
