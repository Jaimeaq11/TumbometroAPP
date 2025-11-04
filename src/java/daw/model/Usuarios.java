package daw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="usuarios")
@NamedQueries({
 @NamedQuery(name="Usuarios.findAll", query="SELECT u FROM Usuarios u"),
 @NamedQuery(name="Usuarios.findByName", query="SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
 
 @NamedQuery(name="Usuarios.findByEmail", query="SELECT u FROM Usuarios u WHERE u.correo = :correo")
})

@Entity
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private Moto moto;
    //private List<Ruta> rutas = new ArrayList<>();

    public Usuarios() {
    }

    public Usuarios(String nombre, String correo, String descripcion, String contrasena, LocalDateTime fechaRegistro, Moto moto) {
        this.nombre = nombre;
        this.correo = correo;
        this.descripcion = descripcion;
        this.contrasena =  contrasena;
        this.moto = moto;
        this.fechaRegistro = fechaRegistro;
    }

    /*public Usuarios(String nombre, String correo, String descripcion, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.descripcion = descripcion;
        this.contrasena = contraseña;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.model.Usuarios[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /*public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }*/

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
