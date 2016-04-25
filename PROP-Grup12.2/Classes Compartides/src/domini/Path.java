package domini;

import java.io.Serializable;

public class Path implements Serializable {

	private static final long serialVersionUID = -6731277843309657995L;
	
	private String path;
    private String definicio;

    public Path(String path) {
        this.path = path;
        this.definicio = "";
    }

    public Path(String path, String definicio) {
        this.path = path;
        this.definicio = definicio;
    }

    public String getPath() {
        return this.path;
    }

    public String getDefinicio() {
        return this.definicio;
    }

    public void setDefinicio(String definicio) {
        this.definicio = definicio;
    }

    public String toString() {
        return this.path + ": " + this.definicio;
    }
}
