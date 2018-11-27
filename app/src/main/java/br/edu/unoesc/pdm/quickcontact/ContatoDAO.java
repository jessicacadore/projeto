package br.edu.unoesc.pdm.quickcontact;

import android.content.ContentValues;
import android.content.Context;
//Um identificador associado a um grupo de linhas
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

//Objeto de acesso a dados (Data Access Object)
public class ContatoDAO extends SQLiteOpenHelper {

    //Declarar a verção, nome da tabela, nome do banco
    private static final int VERSAO = 1;
    private static final String TABELA = "Contatos";
    private static final String DATABASE =  "DadosAgenda";

    //A partir disso é possivel criar o banco, caso ele não exista
    public ContatoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    //Enserir um contato na tabela
    public void inserirContato (Contato contato){

        ContentValues values = new ContentValues();

        //Saber aonde esse novo contato será enserido

        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("site", contato.getSite());
        values.put("telefone", contato.getTelefone());
        values.put("endereco", contato.getEndereco());
        values.put("caminhoFoto", contato.getFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public void apagarContato (Contato contato){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {contato.getId().toString()};
        db.delete("contatos", "id=?", args);
    }
    //Altera o contato da tabela
    public void alteraContato(Contato contato){

        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("site", contato.getSite());
        values.put("telefone", contato.getTelefone());
        values.put("endereco", contato.getEndereco());
        values.put("caminhoFoto", contato.getFoto());

        String[] idParaSerAlterado = {contato.getId().toString()};

        getWritableDatabase().update(TABELA, values, "id=?", idParaSerAlterado);
    }

    //Cria o banco com todos os parametros, colunas...Intens
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " email TEXT, "
                + " site TEXT, "
                + " caminhoFoto TEXT);";
        db.execSQL(ddl);
    }

    //Se houve alguma alteração  na tabela se utiliza este comando, para atualizar
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1) {
            String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
            db.execSQL(sql);
        }
    }

    public List<Contato> getLista(){

        //Transforma em uma Lista (array)
        List<Contato> contatos = new ArrayList<Contato>();
        //Seleciona todos os dados da tabela, emquanto o cursor tiver para onde ir
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);

        while ((c.moveToNext())) {
            Contato contato = new Contato();
            contato.setId(c.getLong(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setEndereco(c.getString(c.getColumnIndex("endereco")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setSite(c.getString(c.getColumnIndex("site")));
            contato.setFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            contatos.add(contato);
        }
        //Fecha o cursor e retorna a lista de contatos
        c.close();
        return contatos;
    }

    public boolean isContato(String telefone){
        String[] parametros = {telefone};
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone FROM " + TABELA + " WHERE telefone = ?", parametros);
        int total = rawQuery.getCount();
        return total > 0;
    }
}