package ergo.leticia.ergomobile.activity;

/**
 * Created by Letícia Silva Souza on 25/11/2016.
 */

public class ItemListView {

    public static final int CAMERA = 1;
    public static final int AUDIO = 2;
    public static final int TESTE = 3;



    private String descricao;
    private String titulo;
    private int iconeRid;
    private int id;

    public ItemListView() {
    }

    public ItemListView(String descricao, String titulo, int iconeRid, int id) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.iconeRid = iconeRid;
        this.id = id;
    }

    public int getIconeRid() {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid) {
        this.iconeRid = iconeRid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}