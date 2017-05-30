package jp.ac.dendai.c.jtp.shootingsample.mono;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public interface Shootable extends Mono {
    Shootable getInstance();
    void init(Vect p, Vect dp);
}