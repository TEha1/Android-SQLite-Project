package com.abdu.teha.mycompanytesting03.Company;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdu.teha.mycompanytesting03.R;
import com.abdu.teha.mycompanytesting03.User.CompanyInfo;
import com.abdu.teha.mycompanytesting03.User.ProductInfo;
import com.abdu.teha.mycompanytesting03.User.RecentProductData;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.DBConnection;
import com.abdu.teha.mycompanytesting03.sqlDBclasses.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TEha on 4/27/2018.
 */

public class DeleteProductAdapter extends ArrayAdapter {
    private Context context;

    public DeleteProductAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    private static class DataHandler{
        TextView productName;
        Button deleteProduct;
    }

    private List list = new ArrayList();

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount() {
        return this.list.size();
    }
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    public void removeItem(int position){
        list.remove(list.get(position));
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        final DeleteProductAdapter.DataHandler handler;
        if (convertView == null) {
            /* yb2a na 3mlt Inflater 3l4an L Views elli h3mlha gdeda tm4i 3la nfs L Style bta3 L parent elli h3rf L Style bta3o mn L Inflater*/
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_delete_product_layout, parent, false);
            handler = new DeleteProductAdapter.DataHandler();
            handler.productName = (TextView) row.findViewById(R.id.product_name01);
            handler.deleteProduct = (Button) row.findViewById(R.id.delete_product01);
            row.setTag(handler);
        }
        else {
            handler = (DeleteProductAdapter.DataHandler) row.getTag();
        }
        /*
        w b3d ma b2a m3aya L Tag elli hwa L View y3ni --> h7ot feh L Data ell na 3ayzha 2w 23ml feh elli na 3ayzo
         */
        final Product product;
        product = (Product) this.getItem(position);
        assert product != null;
        handler.productName.setText(product.getPname());

        handler.deleteProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int product_id = product.getPro_ID();
                if (delete_product(product_id)) {
                    removeItem(position);
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
        return row;
        /*
        w 5li balk --> kol L Functions elli fi l Class da bttnfz Automatic kol elli btb3to hwa L 7aga bta3t L Constructor bs
         */
    }

    public boolean delete_product(int id) {
        Product product = new Product();
        product.setPro_ID(id);
        DBConnection dbConnection = new DBConnection(context);
        boolean b = dbConnection.DeleteProduct(product);
        if (b)
            return true;
        return false;
    }
}
