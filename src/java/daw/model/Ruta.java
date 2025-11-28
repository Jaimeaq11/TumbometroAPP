package daw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r"),
    //esta es para seleccionar las rutas cuyo dueño sea null
    @NamedQuery(name = "Ruta.findByUserNull", query = "SELECT r FROM Ruta r WHERE r.usuarioid IS NULL"),
    //esta es para seleccionar solo las rutas publicas
    @NamedQuery(name = "Ruta.findPublicas", query = "SELECT r FROM Ruta r WHERE r.publica = true"),
})

@Entity
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String nombre;
    private String descripcion;
    private double distancia;
    private double tiempo;
    private String dificultad;
    private String tipoRuta; //offroad, costa, montaña...    
    private String rutaFoto;
    private boolean publica;

    //@ManyToOne(fetch = FetchType.LAZY)
    
    //esto es para que el id usuario de una ruta pueda ser null
    @ManyToOne(optional = true) // <-- 'true' permite que sea null
    @JoinColumn(name = "usuarioid", nullable = true) // <-- Le dice a la BBDD que acepte NULL
    private Usuario usuarioid;

    //SIN EL CONSTRUCTOR VACIO NO LANZA LA APLICACION (no se por que)
    public Ruta() {
    }

    public Ruta(String nombre, String descripcion, double distancia, double tiempo, String dificultad, String tipoRuta, String rutaFoto, boolean publica, Usuario usuarioid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
        this.tipoRuta = tipoRuta;
        this.rutaFoto = rutaFoto;
        this.publica = publica;
        this.usuarioid = usuarioid;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(String tipoRuta) {
        this.tipoRuta = tipoRuta;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
    
    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
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