package daw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    
    private String nombre;
    private double distanciaRecorrida;      
    private double tiempo;                   
    private double inclinacionMaxima;        
    private Date fecha;                 
    private double velocidadMaxima;          
    private double velocidadMedia;
    
    public Ruta() {
    }

    public Ruta(Long id, String nombre, double distanciaRecorrida, double tiempo, double inclinacionMaxima, Date fecha, double velocidadMaxima, double velocidadMedia) {
        this.id = id;
        this.nombre = nombre;
        this.distanciaRecorrida = distanciaRecorrida;
        this.tiempo = tiempo;
        this.inclinacionMaxima = inclinacionMaxima;
        this.fecha = fecha;
        this.velocidadMaxima = velocidadMaxima;
        this.velocidadMedia = velocidadMedia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(double distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getInclinacionMaxima() {
        return inclinacionMaxima;
    }

    public void setInclinacionMaxima(double inclinacionMaxima) {
        this.inclinacionMaxima = inclinacionMaxima;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(double velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public double getVelocidadMedia() {
        return velocidadMedia;
    }

    public void setVelocidadMedia(double velocidadMedia) {
        this.velocidadMedia = velocidadMedia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.model.Ruta[ id=" + id + " ]";
    }
    
}
