package br.edu.unoesc.pdm.quickcontact;

import android.app.Activity;
//Responáveis pelas imagens da câmera e da galeria
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//A interface tem o objetivo de definir coleções
import java.util.List;

//Tem acesso a todos os dados da Base adapter
//Responsavel por pegar todos os dados e vai montar a lista/célula/tabela
public class ContatoAdaptador extends BaseAdapter {

    private final List<Contato> contatos;
    private final Activity activity;

    public ContatoAdaptador(Activity activity, List<Contato> contatos) {
        this.contatos = contatos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View linha = convertView;
        Contato contato = contatos.get(position);
        Bitmap bm;

        if(linha == null){
            linha = this.activity.getLayoutInflater().inflate(R.layout.celula_layout, parent, false);
        }

        TextView nome = (TextView) linha.findViewById(R.id.nomeCelula);
        TextView email = (TextView) linha.findViewById(R.id.emailCelula);
        TextView telefone = (TextView) linha.findViewById(R.id.telefoneCelula);
        TextView endereco = (TextView) linha.findViewById(R.id.enderecoCelula);
        TextView site = (TextView) linha.findViewById(R.id.siteForm);

        nome.setText(contato.getNome());

        if(contato.getFoto() != null){
            bm = BitmapFactory.decodeFile(contato.getFoto());
        }else{
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        bm = Bitmap.createScaledBitmap(bm, 180, 120, true);
        ImageView foto = (ImageView) linha.findViewById(R.id.imagemCelula);
        foto.setImageBitmap(bm);

        if(site != null){site.setText(contato.getSite());}
        if(email != null){email.setText(contato.getEmail());}
        if(telefone != null){telefone.setText(contato.getTelefone());}
        if(endereco != null){endereco.setText(contato.getEndereco());}

        return linha;
    }
}