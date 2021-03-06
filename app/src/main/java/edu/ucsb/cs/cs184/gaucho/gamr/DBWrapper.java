package edu.ucsb.cs.cs184.gaucho.gamr;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    public interface SwipeListener {
        public void onMatch(User other);
    }

    public interface SaleListListener {
        public void onComplete(List<Sale> sales);
    }

    public interface UserListListener {
        public void onComplete(List<User> users);
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
        db.child("users").addListenerForSingleValueEvent(new LoginUserDatabaseListener(uid, listener));
    }

    public void updateUser(User user) {
        db.child("users").child(user.getId()).setValue(user);
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
        if (sale.id == null) {
            sale.id = db.child("sales").push().getKey();
        }
        Log.d("DBWrapper", String.format("Writing item %s with id %s\n", sale.name, sale.id));
        db.child("sales").child(sale.id).setValue(sale);
        addSaletoUser(sale);
    }

    private void addSaletoUser(final Sale sale) {
        getUser(sale.ownerId, new UserTransactionListener() {
            @Override
            public void onComplete(User user) {
                if (!user.saleIds.contains(sale.id)) {
                    user.saleIds.add(sale.id);
                    updateUser(user);
                }
            }
        });
    }
    public void swipeSaleItem(final User swiper, Sale sale, boolean positive, final SwipeListener listener) {
        swiper.seenSaleIds.add(sale.id);
        if (positive && !swiper.swipedUserIds.contains(sale.ownerId))
            swiper.getSwipedUserIds().add(sale.ownerId);
        updateUser(swiper);
        getUser(sale.ownerId, new UserTransactionListener() {
            @Override
            public void onComplete(User user) {
                if (user.getSwipedUserIds().contains(swiper.id)) {
                    // It's a match!
                    if (!user.getMatchIds().contains(swiper.id))
                        user.matchIds.add(swiper.id);
                    if (!swiper.getMatchIds().contains(user.id))
                        swiper.matchIds.add(user.id);
                        updateUser(user);
                    updateUser(swiper);
                    listener.onMatch(user);
                }
            }
        });
    }

    public void getUserMatches(User user, UserListListener listener) {

    }

    public void getNewSales(User swiper, SaleListListener listener) {
        db.child("sales").addListenerForSingleValueEvent(new GetNewSalesDatabaseListener(swiper, listener));
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
    class GetNewSalesDatabaseListener implements ValueEventListener {
        SaleListListener listener;
        User owner;
        public GetNewSalesDatabaseListener(User owner, SaleListListener listener) {
            this.listener = listener;
            this.owner = owner;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<Sale> sales = new ArrayList<>();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Sale s = ds.getValue(Sale.class);
                if (!s.ownerId.equals(owner.id) && !owner.getSeenSaleIds().contains(s.id))
                    sales.add(s);
            }
            listener.onComplete(sales);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
