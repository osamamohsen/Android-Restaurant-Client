package osama.restaurant.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import osama.restaurant.Model.Order;

/**
 * Created by osama on 10/09/17.
 */

public class Database extends SQLiteAssetHelper{

    private static final String DB_NAME = "restaurantDB.db"; // database name
    private static final int DB_VER = 1; // version of database

    public Database(Context context) {
        super(context, DB_NAME, null , DB_VER);
    }

    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sqlTable = "orderDetails";
        String [] sqlSelect = {"ProductId","ProductName","Quantity","Price","Discount"};
        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(new Order(
                        cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                ));
            }while (cursor.moveToNext());
        }
        return  result;
    }

    public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO orderDetails (ProductId , ProductName , Quantity , Price , Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount()
                );
        db.execSQL(query);
    }

    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM orderDetails");
        db.execSQL(query);
    }
}
