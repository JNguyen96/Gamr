package edu.ucsb.cs.cs184.gaucho.gamr;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by william on 12/14/17.
 */

public class DBWrapper {
    public interface UserTransactionListener {
        public void onComplete(User user);
    }

    public interface SaleTransactionListener {
        enum FailureReason {
            KeyNotFound,
        };
        public void onComplete(Sale sale);
        public void onFailure(FailureReason reason);
    }

    private static DBWrapper instance;
    public static DBWrapper getInstance() {
        if (instance == null)
            instance = new DBWrapper();
        return instance;
    }

    private DatabaseReference db;

    private DBWrapper() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    public void getUser(String uid, UserTransactionListener listener) {
        User user = new User();
        user.id = uid;
        db.child("users").addListenerForSingleValueEvent(new LoginUserDatabaseListener(uid, listener));
    }

    class LoginUserDatabaseListener implements  ValueEventListener {
        String uid;
        UserTransactionListener outerListener;
        public LoginUserDatabaseListener(String uid, UserTransactionListener outerListener) {
            this.uid =  uid;
            this.outerListener = outerListener;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User user;
            if (dataSnapshot.hasChild(uid)) {
                user = dataSnapshot.child(uid).getValue(User.class);
            }
            else {
                user = new User();
                user.id = uid;
                db.child("users").child(uid).setValue(user);
            }
            outerListener.onComplete(user);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d("DBWrapper", String.format("Firebase failed with reason %s\n", databaseError.getMessage()));
        }
    }

    public void getSale(String saleId, SaleTransactionListener listener) {
        db.child("sales").addListenerForSingleValueEvent(new GetSaleDatabaseListener(saleId, listener));
    }

    public void updateSale(Sale sale) {
        final Sale closure = sale;
        getSale(sale.id, new SaleTransactionListener() {
            @Override
            public void onComplete(Sale sale) {
                db.child("sales").child(sale.id).setValue(sale);
            }

            @Override
            public void onFailure(FailureReason reason) {
                if (reason == FailureReason.KeyNotFound) {
                    String key = db.child("sales").push().getKey();
                    closure.id = key;
                    db.child("sales").child(key).setValue(closure);
                }
            }
        });
    }


    class GetSaleDatabaseListener implements ValueEventListener {
        String id;
        SaleTransactionListener outerListener;
        public GetSaleDatabaseListener(String id, SaleTransactionListener outerListener) {
            this.id = id;
            this.outerListener = outerListener;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Sale sale;
            if (dataSnapshot.hasChild(id)) {
                sale = dataSnapshot.child(id).getValue(Sale.class);
                outerListener.onComplete(sale);
            }
            else {
                outerListener.onFailure(SaleTransactionListener.FailureReason.KeyNotFound);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
